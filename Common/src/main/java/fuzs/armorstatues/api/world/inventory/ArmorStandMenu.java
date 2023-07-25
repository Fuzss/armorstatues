package fuzs.armorstatues.api.world.inventory;

import com.mojang.datafixers.util.Pair;
import fuzs.armorstatues.api.ArmorStatuesApi;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOption;
import fuzs.armorstatues.core.ModServices;
import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import fuzs.armorstatues.mixin.accessor.SimpleContainerAccessor;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ArmorStandMenu extends AbstractContainerMenu implements ArmorStandHolder {
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SWORD = ArmorStatuesApi.id("item/empty_armor_slot_sword");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD, EMPTY_ARMOR_SLOT_SWORD};
    public static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    private final Container armorStandInventory;
    private final ArmorStand armorStand;

    public static ArmorStandMenu create(MenuType<?> menuType, int containerId, Inventory inventory, FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        ArmorStand entity = (ArmorStand) inventory.player.level.getEntity(entityId);
        if (entity != null) {
            // vanilla doesn't sync these automatically, we need them for the menu
            entity.setInvulnerable(buf.readBoolean());
            ((ArmorStandAccessor) entity).setDisabledSlots(buf.readInt());
            // also create the armor stand container client side, so that visual update instantly instead of having to wait for the server to resync data
            return create(menuType, containerId, inventory, entity);
        }
        // exception is caught, so nothing will crash, only the screen will not open
        // not sure how this is even possible, but there was a report about it
        // report was concerning just placed statues, so maybe entity data arrived at remote after menu was opened
        throw new IllegalStateException("trying to open invalid armor stand menu, entity for id %s was not found on client".formatted(entityId));
    }

    public static ArmorStandMenu create(MenuType<?> menuType, int containerId, Inventory inventory, ArmorStand armorStand) {
        // we could also copy all items from the armor stand to a SimpleContainer, then update the armor stand using a listener using LivingEntity::setItemSlot
        // problem is that way we miss out on anything changing with the armor stand entity itself, therefore this approach
        NonNullList<ItemStack> armorItems = ((ArmorStandAccessor) armorStand).getArmorItems();
        NonNullList<ItemStack> handItems = ((ArmorStandAccessor) armorStand).getHandItems();
        SimpleContainer handItemsContainer = simpleContainer(handItems);
        handItemsContainer.addListener(container -> {
            if (container.hasAnyMatching(stack -> !stack.isEmpty())) {
                ArmorStandStyleOption.setArmorStandData(armorStand, true, ArmorStand.CLIENT_FLAG_SHOW_ARMS);
            }
        });
        CompoundContainer container = new CompoundContainer(simpleContainer(armorItems), handItemsContainer);
        return new ArmorStandMenu(menuType, containerId, inventory, container, armorStand);
    }

    private static SimpleContainer simpleContainer(NonNullList<ItemStack> items) {
        SimpleContainer container = new SimpleContainer(items.size());
        ((SimpleContainerAccessor) container).setItems(items);
        return container;
    }

    private ArmorStandMenu(MenuType<?> menuType, int containerId, Inventory inventory, Container container, ArmorStand armorStand) {
        super(menuType, containerId);
        this.armorStandInventory = container;
        this.armorStand = armorStand;
        for (int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentslot = SLOT_IDS[k];
            this.addSlot(new Slot(this.armorStandInventory, 3 - k, 58, 20 + k * 18) {

                @Override
                public void set(ItemStack stack) {
                    ItemStack oldStack = this.getItem();
                    super.set(stack);
                    armorStand.onEquipItem(equipmentslot, oldStack, stack);
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    if (!inventory.player.isCreative()) {
                        if (isSlotDisabled(armorStand, equipmentslot, 0)) {
                            return false;
                        }
                        if (isSlotDisabled(armorStand, equipmentslot, ArmorStand.DISABLE_PUTTING_OFFSET) && !this.hasItem()) {
                            return false;
                        }
                    }
                    if (equipmentslot == EquipmentSlot.HEAD) {
                        return true;
                    }
                    return ModServices.ABSTRACTIONS.canEquip(stack, equipmentslot, inventory.player);
                }

                @Override
                public boolean mayPickup(Player player) {
                    if (!inventory.player.isCreative()) {
                        if (isSlotDisabled(armorStand, equipmentslot, ArmorStand.DISABLE_TAKING_OFFSET)) {
                            return false;
                        }
                    }
                    return super.mayPickup(player);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            final EquipmentSlot equipmentslot = SLOT_IDS[4 + i];
            final ResourceLocation slotTexture = TEXTURE_EMPTY_SLOTS[5 - i];
            this.addSlot(new Slot(this.armorStandInventory, 4 + i, 136, 56 + i * 18) {

                @Override
                public boolean mayPlace(ItemStack stack) {
                    if (!inventory.player.isCreative()) {
                        if (isSlotDisabled(armorStand, equipmentslot, 0)) {
                            return false;
                        }
                        if (isSlotDisabled(armorStand, equipmentslot, ArmorStand.DISABLE_PUTTING_OFFSET) && !this.hasItem()) {
                            return false;
                        }
                    }
                    return super.mayPlace(stack);
                }

                @Override
                public boolean mayPickup(Player player) {
                    if (!inventory.player.isCreative()) {
                        if (isSlotDisabled(armorStand, equipmentslot, ArmorStand.DISABLE_TAKING_OFFSET)) {
                            return false;
                        }
                    }
                    return super.mayPickup(player);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, slotTexture);
                }
            });
        }

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + (l + 1) * 9, 25 + j1 * 18, 96 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 25 + i1 * 18, 154));
        }
    }

    public static boolean isSlotDisabled(ArmorStand armorStand, EquipmentSlot slot, int offset) {
        return (((ArmorStandAccessor) armorStand).getDisabledSlots() & 1 << slot.getFilterFlag() + offset) != 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            if (index >= 0 && index < 6) {
                if (!this.moveItemStackTo(itemStack2, 6, 42, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(3 - equipmentSlot.getIndex()).hasItem()) {
                int i = 3 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemStack2, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !this.slots.get(5).hasItem()) {
                if (!this.moveItemStackTo(itemStack2, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 6 && index < 33) {
                if (!this.moveItemStackTo(itemStack2, 33, 42, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 33 && index < 42) {
                if (!this.moveItemStackTo(itemStack2, 6, 33, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 6, 42, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        // no distance check to avoid annoying issues when editing armor stand
        return this.armorStand != null && !this.armorStand.isRemoved();
    }

    @Override
    public ArmorStand getArmorStand() {
        return this.armorStand;
    }
}

package fuzs.armorstatues.world.inventory;

import com.mojang.datafixers.util.Pair;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Stream;

public class ArmorStandMenu extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SWORD = new ResourceLocation(ArmorStatues.MOD_ID, "item/empty_armor_slot_sword");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private final SimpleContainer armorStandInventory;
    private final ArmorStand armorStand;

    public ArmorStandMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, new SimpleContainer(6), (ArmorStand) inventory.player.level.getEntity(buf.readInt()));
    }

    public static ArmorStandMenu create(int containerId, Inventory inventory, ArmorStand armorStand) {
        final EquipmentSlot[] equipmentSlots = EquipmentSlot.values();
        ItemStack[] slots = Stream.of(equipmentSlots).map(armorStand::getItemBySlot).toArray(ItemStack[]::new);
        SimpleContainer container = new SimpleContainer(slots);
        container.addListener(container1 -> {
            for (int i12 = 0; i12 < equipmentSlots.length; i12++) {
                if (!container1.getItem(i12).sameItem(armorStand.getItemBySlot(equipmentSlots[i12]))) {
                    armorStand.setItemSlot(equipmentSlots[i12], container1.getItem(i12));
                    if (equipmentSlots[i12].getType() == EquipmentSlot.Type.HAND) {
                        armorStand.getEntityData().set(ArmorStand.DATA_CLIENT_FLAGS, (byte) (armorStand.getEntityData().get(ArmorStand.DATA_CLIENT_FLAGS) | 4));
                    }
                }
            }
        });
        return new ArmorStandMenu(containerId, inventory, container, armorStand);
    }

    public ArmorStandMenu(int containerId, Inventory inventory, SimpleContainer container, ArmorStand armorStand) {
        super(ModRegistry.ARMOR_STAND_MENU_TYPE.get(), containerId);
        this.armorStandInventory = container;
        this.armorStand = armorStand;
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentslot = SLOT_IDS[k];
            this.addSlot(new Slot(this.armorStandInventory, 5 - k, 58, 20 + k * 18) {

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return equipmentslot == Mob.getEquipmentSlotForItem(stack);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
                }
            });
        }

        this.addSlot(new Slot(this.armorStandInventory, 1, 136, 74) {

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        this.addSlot(new Slot(this.armorStandInventory, 0, 136, 56) {

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_ARMOR_SLOT_SWORD);
            }
        });

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + (l + 1) * 9, 25 + j1 * 18, 96 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 25 + i1 * 18, 154));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public ArmorStand getArmorStand() {
        return this.armorStand;
    }
}

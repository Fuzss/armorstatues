package fuzs.armorstatues.api.client.gui.screens.armorstand.data;

import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import fuzs.armorstatues.api.network.client.data.CommandDataSyncHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandStyleOption {
    SHOW_NAME("showName", Entity::setCustomNameVisible, Entity::isCustomNameVisible),
    SHOW_ARMS("showArms", (armorStand, setting) -> setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_SHOW_ARMS), armorStand -> getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_SHOW_ARMS)),
    SMALL("small", (armorStand, setting) -> setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_SMALL), armorStand -> getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_SMALL)),
    INVISIBLE("invisible", ArmorStand::setInvisible, Entity::isInvisible),
    NO_BASE_PLATE("noBasePlate", (armorStand, setting) -> setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_NO_BASEPLATE), armorStand -> getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_NO_BASEPLATE)),
    NO_GRAVITY("noGravity", Entity::setNoGravity, Entity::isNoGravity),
    SEALED("sealed", (armorStand, setting) -> {
        armorStand.setInvulnerable(setting);
        ((ArmorStandAccessor) armorStand).setDisabledSlots(setting ? CommandDataSyncHandler.ARMOR_STAND_ALL_SLOTS_DISABLED : 0);
    }, Entity::isInvulnerable);

    private final String name;
    private final BiConsumer<ArmorStand, Boolean> newValue;
    private final Function<ArmorStand, Boolean> currentValue;

    ArmorStandStyleOption(String name, BiConsumer<ArmorStand, Boolean> newValue, Function<ArmorStand, Boolean> currentValue) {
        this.name = name;
        this.newValue = newValue;
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Component getComponent() {
        return Component.translatable("armorstatues.screen.style." + this.name);
    }

    public Component getDescriptionComponent() {
        return Component.translatable("armorstatues.screen.style." + this.name + ".description");
    }

    public void setOption(ArmorStand armorStand, boolean setting) {
        this.newValue.accept(armorStand, setting);
    }

    public boolean getOption(ArmorStand armorStand) {
        return this.currentValue.apply(armorStand);
    }

    public boolean onlyCreative() {
        return this == SEALED;
    }

    public static boolean getArmorStandData(ArmorStand armorStand, int offset) {
        return (armorStand.getEntityData().get(ArmorStand.DATA_CLIENT_FLAGS) & offset) != 0;
    }

    public static void setArmorStandData(ArmorStand armorStand, boolean setting, int offset) {
        armorStand.getEntityData().set(ArmorStand.DATA_CLIENT_FLAGS, setBit(armorStand.getEntityData().get(ArmorStand.DATA_CLIENT_FLAGS), offset, setting));
    }

    private static byte setBit(byte oldBit, int offset, boolean value) {
        if (value) {
            oldBit = (byte)(oldBit | offset);
        } else {
            oldBit = (byte)(oldBit & ~offset);
        }
        return oldBit;
    }
}

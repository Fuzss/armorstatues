package fuzs.armorstatues.api.world.inventory.data;

import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandStyleOptions implements ArmorStandStyleOption {
    SHOW_NAME("showName", Entity::setCustomNameVisible, Entity::isCustomNameVisible),
    SHOW_ARMS("showArms", (armorStand, setting) -> ArmorStandStyleOption.setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_SHOW_ARMS), armorStand -> ArmorStandStyleOption.getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_SHOW_ARMS)),
    SMALL("small", (armorStand, setting) -> ArmorStandStyleOption.setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_SMALL), armorStand -> ArmorStandStyleOption.getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_SMALL)),
    INVISIBLE("invisible", ArmorStand::setInvisible, Entity::isInvisible),
    NO_BASE_PLATE("noBasePlate", (armorStand, setting) -> ArmorStandStyleOption.setArmorStandData(armorStand, setting, ArmorStand.CLIENT_FLAG_NO_BASEPLATE), armorStand -> ArmorStandStyleOption.getArmorStandData(armorStand, ArmorStand.CLIENT_FLAG_NO_BASEPLATE)),
    NO_GRAVITY("noGravity", Entity::setNoGravity, Entity::isNoGravity),
    SEALED("sealed", (armorStand, setting) -> {
        armorStand.setInvulnerable(setting);
        ((ArmorStandAccessor) armorStand).setDisabledSlots(setting ? ArmorStandStyleOption.ARMOR_STAND_ALL_SLOTS_DISABLED : 0);
    }, Entity::isInvulnerable);

    private final String translationId;
    private final BiConsumer<ArmorStand, Boolean> newValue;
    private final Function<ArmorStand, Boolean> currentValue;

    ArmorStandStyleOptions(String translationId, BiConsumer<ArmorStand, Boolean> newValue, Function<ArmorStand, Boolean> currentValue) {
        this.translationId = translationId;
        this.newValue = newValue;
        this.currentValue = currentValue;
    }

    @Override
    public String getTranslationId() {
        return this.translationId;
    }

    @Override
    public void setOption(ArmorStand armorStand, boolean setting) {
        this.newValue.accept(armorStand, setting);
    }

    @Override
    public boolean getOption(ArmorStand armorStand) {
        return this.currentValue.apply(armorStand);
    }

    @Override
    public void toTag(CompoundTag tag, boolean currentValue) {
        String dataKey = switch (this) {
            case SHOW_NAME -> "CustomNameVisible";
            case SHOW_ARMS -> "ShowArms";
            case SMALL -> "Small";
            case INVISIBLE -> "Invisible";
            case NO_BASE_PLATE -> "NoBasePlate";
            case NO_GRAVITY -> "NoGravity";
            case SEALED -> "Invulnerable";
        };
        tag.putBoolean(dataKey, currentValue);
        if (this == ArmorStandStyleOptions.SEALED) {
            tag.putInt("DisabledSlots", currentValue ? ArmorStandStyleOption.ARMOR_STAND_ALL_SLOTS_DISABLED : 0);
        }
    }


    @Override
    public boolean allowChanges(Player player) {
        return this != ArmorStandStyleOptions.SEALED || player.getAbilities().instabuild;
    }
}

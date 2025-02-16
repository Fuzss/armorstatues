package fuzs.armorstatues.api.network.client.data;

import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.*;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface DataSyncHandler {

    ArmorStandHolder getArmorStandHolder();

    default ArmorStand getArmorStand() {
        return this.getArmorStandHolder().getArmorStand();
    }

    void sendName(String name);

    void sendPose(ArmorStandPose pose);

    default void sendPose(ArmorStandPose pose, boolean finalize) {
        this.sendPose(pose);
    }

    @Nullable
    default ArmorStandPose getLastSyncedPose() {
        return null;
    }

    void sendPosition(double posX, double posY, double posZ);

    default void sendPosition(double posX, double posY, double posZ, boolean finalize) {
        this.sendPosition(posX, posY, posZ);
    }

    void sendRotation(float rotation);

    default void sendRotation(float rotation, boolean finalize) {
        this.sendRotation(rotation);
    }

    void sendStyleOption(ArmorStandStyleOption styleOption, boolean value);

    default void sendStyleOption(ArmorStandStyleOption styleOption, boolean value, boolean finalize) {
        this.sendStyleOption(styleOption, value);
    }

    default void sendAlignment(ArmorStandAlignment alignment) {
        if (!this.getArmorStand().isInvisible()) {
            this.sendStyleOption(ArmorStandStyleOptions.INVISIBLE, true, false);
        }
        if (!this.getArmorStand().isNoGravity()) {
            this.sendStyleOption(ArmorStandStyleOptions.NO_GRAVITY, true, false);
        }
        this.sendPose(alignment.getPose(), false);
        Vec3 alignmentOffset = alignment.getAlignmentOffset(this.getArmorStand().isSmall());
        Vec3 newPosition = getLocalPosition(this.getArmorStand(), alignmentOffset);
        this.sendPosition(newPosition.x(), newPosition.y(), newPosition.z(), false);
        this.finalizeCurrentOperation();
    }

    /**
     * Copied from {@link net.minecraft.commands.arguments.coordinates.LocalCoordinates#getPosition(CommandSourceStack)}.
     */
    private static Vec3 getLocalPosition(Entity entity, Vec3 offset) {
        Vec2 vec2 = entity.getRotationVector();
        Vec3 vec3 = entity.position();
        float f = Mth.cos((vec2.y + 90.0F) * 0.017453292F);
        float g = Mth.sin((vec2.y + 90.0F) * 0.017453292F);
        float h = Mth.cos(-vec2.x * 0.017453292F);
        float i = Mth.sin(-vec2.x * 0.017453292F);
        float j = Mth.cos((-vec2.x + 90.0F) * 0.017453292F);
        float k = Mth.sin((-vec2.x + 90.0F) * 0.017453292F);
        Vec3 vec32 = new Vec3(f * h, i, g * h);
        Vec3 vec33 = new Vec3(f * j, k, g * j);
        Vec3 vec34 = vec32.cross(vec33).scale(-1.0);
        double d = vec32.x * offset.z() + vec33.x * offset.y() + vec34.x * offset.x();
        double e = vec32.y * offset.z() + vec33.y * offset.y() + vec34.y * offset.x();
        double l = vec32.z * offset.z() + vec33.z * offset.y() + vec34.z * offset.x();
        return new Vec3(vec3.x + d, vec3.y + e, vec3.z + l);
    }

    default ArmorStandScreenType[] getScreenTypes() {
        return Stream.of(this.getArmorStandHolder().getDataProvider().getScreenTypes()).filter(this::supportsScreenType).toArray(ArmorStandScreenType[]::new);
    }

    default boolean supportsScreenType(ArmorStandScreenType screenType) {
        return true;
    }

    default void tick() {

    }

    default boolean shouldContinueTicking() {
        return false;
    }

    default void finalizeCurrentOperation() {

    }

    static void setCustomArmorStandName(ArmorStand armorStand, String name) {
        name = SharedConstants.filterText(name);
        if (name.length() <= 50) {
            boolean remove = name.isBlank() || name.equals(EntityType.ARMOR_STAND.getDescription().getString());
            armorStand.setCustomName(remove ? null : Component.literal(name));
        }
    }
}

package fuzs.armorstatues.api.world.inventory.data;

import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;

/**
 * values copied from <a href="https://vanillatweaks.net/">Vanilla Tweaks</a> data pack
 */
public enum ArmorStandAlignment {
    BLOCK("block", new Rotations(-15.0f, 135.0f, 0.0f), new Vec3(0.5725, -0.655, 0.352), new Vec3(0.28625, -0.3275, 0.176)),
    FLOATING_ITEM("itemFloating", new Rotations(-90.0f, 0.0f, 0.0f), new Vec3(0.36, -1.41, -0.5625), new Vec3(0.18, -0.705, -0.28125)),
    FLAT_ITEM("itemFlat", new Rotations(0.0f, 0.0f, 0.0f), new Vec3(0.385, -0.78, -0.295), new Vec3(0.1925, -0.39, -0.1475)),
    TOOL("tool", new Rotations(-10.0f, 0.0f, -90.0f), new Vec3(-0.17, -1.285, -0.44), new Vec3(-0.085, -0.6425, -0.22));

    private final String name;
    private final ArmorStandPose pose;
    private final Vec3 position;
    private final Vec3 smallPosition;

    ArmorStandAlignment(String name, Rotations rightArmRotations, Vec3 position, Vec3 smallPosition) {
        this.name = name;
        this.pose = ArmorStandPose.empty().withRightArmPose(rightArmRotations);
        this.position = position;
        this.smallPosition = smallPosition;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Component getComponent() {
        return new TranslatableComponent("armorstatues.screen.alignments." + this.name);
    }

    public ArmorStandPose getPose() {
        return this.pose;
    }

    public Vec3 getPosition(boolean small) {
        return small ? this.smallPosition : this.position;
    }
}

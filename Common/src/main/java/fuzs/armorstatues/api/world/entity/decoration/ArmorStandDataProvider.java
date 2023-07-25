package fuzs.armorstatues.api.world.entity.decoration;

import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.PosePartMutator;
import net.minecraft.core.Rotations;

public interface ArmorStandDataProvider {
    ArmorStandDataProvider INSTANCE = new ArmorStandDataProvider() {};

    default ArmorStandScreenType[] getScreenTypes() {
        return new ArmorStandScreenType[]{ArmorStandScreenType.ROTATIONS, ArmorStandScreenType.POSES, ArmorStandScreenType.STYLE, ArmorStandScreenType.POSITION, ArmorStandScreenType.ALIGNMENTS, ArmorStandScreenType.EQUIPMENT};
    }

    default ArmorStandScreenType getDefaultScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }

    default PosePartMutator[] getPosePartMutators() {
        return new PosePartMutator[]{PosePartMutator.HEAD, PosePartMutator.BODY, PosePartMutator.RIGHT_ARM, PosePartMutator.LEFT_ARM, PosePartMutator.RIGHT_LEG, PosePartMutator.LEFT_LEG};
    }

    default ArmorStandPose getRandomPose(boolean clampRotations) {
        return ArmorStandPose.random(this.getPosePartMutators(), clampRotations).withBodyPose(new Rotations(0.0F, 0.0F, 0.0F));
    }
}

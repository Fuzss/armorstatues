package fuzs.armorstatues.api.world.entity.decoration;

import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;

public interface ArmorStandDataProvider {
    ArmorStandDataProvider INSTANCE = new ArmorStandDataProvider() {};

    default ArmorStandScreenType[] getScreenTypes() {
        return new ArmorStandScreenType[]{ArmorStandScreenType.ROTATIONS, ArmorStandScreenType.POSES, ArmorStandScreenType.STYLE, ArmorStandScreenType.POSITION, ArmorStandScreenType.ALIGNMENTS, ArmorStandScreenType.EQUIPMENT};
    }

    default ArmorStandScreenType getDefaultScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }

    default Component getBodyComponent() {
        return Component.translatable("armorstatues.screen.rotations.pose.body");
    }

    default ArmorStandPose getRandomPose(boolean clampRotations) {
        return ArmorStandPose.random(clampRotations).setBodyPose(new Rotations(0.0F, 0.0F, 0.0F));
    }
}

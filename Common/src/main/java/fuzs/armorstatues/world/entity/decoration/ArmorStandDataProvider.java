package fuzs.armorstatues.world.entity.decoration;

import fuzs.armorstatues.world.inventory.ArmorStandScreenType;

public interface ArmorStandDataProvider {
    ArmorStandDataProvider INSTANCE = new ArmorStandDataProvider() {};

    default ArmorStandScreenType[] getScreenTypes() {
        return new ArmorStandScreenType[]{ArmorStandScreenType.ROTATIONS, ArmorStandScreenType.POSES, ArmorStandScreenType.STYLE, ArmorStandScreenType.POSITION, ArmorStandScreenType.ALIGNMENTS, ArmorStandScreenType.EQUIPMENT};
    }

    default ArmorStandScreenType getDefaultScreenType() {
        return ArmorStandScreenType.ROTATIONS;
    }
}

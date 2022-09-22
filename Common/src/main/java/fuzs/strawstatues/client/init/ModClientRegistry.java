package fuzs.strawstatues.client.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry REGISTRY = ClientCoreServices.FACTORIES.modelLayerRegistration(ArmorStatues.MOD_ID);
    public static final ModelLayerLocation STRAW_STATUE = REGISTRY.register("straw_statue");
    public static final ModelLayerLocation STRAW_STATUE_INNER_ARMOR = REGISTRY.registerInnerArmor("straw_statue");
    public static final ModelLayerLocation STRAW_STATUE_OUTER_ARMOR = REGISTRY.registerOuterArmor("straw_statue");
    public static final ModelLayerLocation STRAW_STATUE_SLIM = REGISTRY.register("straw_statue_slim");
    public static final ModelLayerLocation STRAW_STATUE_SLIM_INNER_ARMOR = REGISTRY.registerInnerArmor("straw_statue_slim");
    public static final ModelLayerLocation STRAW_STATUE_SLIM_OUTER_ARMOR = REGISTRY.registerOuterArmor("straw_statue_slim");
}

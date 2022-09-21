package fuzs.armorstatues.client.init;

import fuzs.armorstatues.ArmorStatues;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry REGISTRY = ClientCoreServices.FACTORIES.modelLayerRegistration(ArmorStatues.MOD_ID);
    public static final ModelLayerLocation PLAYER_STATUE = REGISTRY.register("player_statue");
    public static final ModelLayerLocation PLAYER_STATUE_INNER_ARMOR = REGISTRY.registerInnerArmor("player_statue");
    public static final ModelLayerLocation PLAYER_STATUE_OUTER_ARMOR = REGISTRY.registerOuterArmor("player_statue");
}

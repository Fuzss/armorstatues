package fuzs.armorstatues.init;

import com.mojang.authlib.GameProfile;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.world.entity.decoration.PlayerStatue;
import fuzs.armorstatues.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(ArmorStatues.MOD_ID);
    public static final RegistryReference<EntityType<PlayerStatue>> PLAYER_STATUE_ENTITY_TYPE = REGISTRY.registerEntityTypeBuilder("player_statue", () -> {
        return EntityType.Builder.of((EntityType<PlayerStatue> entityType, Level level) -> new PlayerStatue(entityType, level), MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10);
    });
    public static final RegistryReference<MenuType<ArmorStandMenu>> ARMOR_STAND_MENU_TYPE = REGISTRY.registerExtendedMenuTypeSupplier("armor_stand", () -> ArmorStandMenu::create);

    public static final EntityDataSerializer<Block> BLOCK_ENTITY_DATA_SERIALIZER = EntityDataSerializer.simpleId(Registry.BLOCK);
    public static final EntityDataSerializer<Optional<GameProfile>> GAME_PROFILE_ENTITY_DATA_SERIALIZER = EntityDataSerializer.optional(FriendlyByteBuf::writeGameProfile, FriendlyByteBuf::readGameProfile);

    public static void touch() {
        EntityDataSerializers.registerSerializer(BLOCK_ENTITY_DATA_SERIALIZER);
        EntityDataSerializers.registerSerializer(GAME_PROFILE_ENTITY_DATA_SERIALIZER);
    }
}

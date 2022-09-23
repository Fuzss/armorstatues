package fuzs.strawstatues.init;

import com.mojang.authlib.GameProfile;
import fuzs.armorstatues.api.world.inventory.ArmorStandMenu;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.puzzleslib.init.builder.ExtendedModMenuSupplier;
import fuzs.strawstatues.StrawStatues;
import fuzs.strawstatues.world.entity.decoration.StrawStatue;
import fuzs.strawstatues.world.item.StrawStatueItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(StrawStatues.MOD_ID);
    public static final RegistryReference<Item> STRAW_STATUE_ITEM = REGISTRY.registerItem("straw_statue", () -> new StrawStatueItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryReference<EntityType<StrawStatue>> STRAW_STATUE_ENTITY_TYPE = REGISTRY.registerEntityTypeBuilder("straw_statue", () -> {
        return EntityType.Builder.of((EntityType<StrawStatue> entityType, Level level) -> new StrawStatue(entityType, level), MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10);
    });
    public static final RegistryReference<MenuType<ArmorStandMenu>> STRAW_STATUE_MENU_TYPE = REGISTRY.registerExtendedMenuTypeSupplier("straw_statue", () -> getStrawStatueMenuTypeSupplier());

    public static final EntityDataSerializer<Optional<GameProfile>> GAME_PROFILE_ENTITY_DATA_SERIALIZER = EntityDataSerializer.optional(FriendlyByteBuf::writeGameProfile, FriendlyByteBuf::readGameProfile);

    public static void touch() {
        EntityDataSerializers.registerSerializer(GAME_PROFILE_ENTITY_DATA_SERIALIZER);
    }

    private static ExtendedModMenuSupplier<ArmorStandMenu> getStrawStatueMenuTypeSupplier() {
        return (containerId, inventory, data) -> ArmorStandMenu.create(STRAW_STATUE_MENU_TYPE.get(), containerId, inventory, data);
    }
}

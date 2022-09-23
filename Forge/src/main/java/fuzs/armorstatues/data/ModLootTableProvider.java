package fuzs.armorstatues.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fuzs.strawstatues.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(Pair.of(ModEntityLoot::new, LootContextParamSets.ENTITY));
    private final String modId;

    public ModLootTableProvider(DataGenerator p_124437_, String modId) {
        super(p_124437_);
        this.modId = modId;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return this.subProviders;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {

    }

    private class ModEntityLoot extends EntityLoot {

        @Override
        protected void addTables() {
            this.add(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), LootTable.lootTable());
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return ForgeRegistries.ENTITY_TYPES.getEntries().stream()
                    .filter(e -> e.getKey().location().getNamespace().equals(ModLootTableProvider.this.modId))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet());
        }

        @Override
        protected boolean isNonLiving(EntityType<?> entitytype) {
            return entitytype != ModRegistry.STRAW_STATUE_ENTITY_TYPE.get() && super.isNonLiving(entitytype);
        }
    }
}

package fuzs.armorstatues.data;

import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.STRAW_STATUE_ITEM.get())
                .define('#', Blocks.HAY_BLOCK)
                .define('X', Items.STICK)
                .pattern("XXX")
                .pattern(" X ")
                .pattern("X#X")
                .unlockedBy("has_hay_block", has(Blocks.HAY_BLOCK))
                .save(p_176532_);
    }
}

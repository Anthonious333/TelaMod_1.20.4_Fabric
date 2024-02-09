package net.anthony.telamod.datagen;

import net.anthony.telamod.block.ModBlocks;
import net.anthony.telamod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.TELARAIL, 1)
                .input(ItemTags.RAILS)
                .input(Items.ENDER_PEARL)
                .input(Items.REDSTONE)
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL)).
                offerTo(exporter, new Identifier("railtotelarail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.TELARAIL, 1)
                .pattern("IRI")
                .pattern("ISI")
                .pattern("IEI")
                .input('S', Items.STICK)
                .input('I', Items.IRON_INGOT)
                .input('R', Items.REDSTONE)
                .input('E', Items.ENDER_PEARL)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier("telarail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.DESTARAIL, 1)
                .pattern("ODO")
                .pattern("OSO")
                .pattern("OEO")
                .input('S', Items.STICK)
                .input('O', Items.OBSIDIAN)
                .input('D', Items.DIAMOND)
                .input('E', Items.ENDER_PEARL)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier("destarail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.TELA_WRENCH, 1)
                .pattern("ESE")
                .pattern("OOO")
                .pattern(" O ")
                .input('S', Items.STICK)
                .input('O', Items.OBSIDIAN)
                .input('E', Items.ENDER_PEARL)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter, new Identifier("telawrench"));
    }


}

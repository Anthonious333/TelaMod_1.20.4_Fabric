package net.anthony.telamod.block;

import net.anthony.telamod.TelaMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block TELARAIL = registerBlock("telarail",
            new TelaRailBlock(FabricBlockSettings.copyOf(Blocks.DETECTOR_RAIL)));

    public static final Block DESTARAIL = registerBlock("destarail",
            new DestaRailBlock(FabricBlockSettings.copyOf(Blocks.DETECTOR_RAIL)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TelaMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(TelaMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        TelaMod.LOGGER.info("Registering ModBlocks for " + TelaMod.MOD_ID);
    }
}
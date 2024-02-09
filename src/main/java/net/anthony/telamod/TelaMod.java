package net.anthony.telamod;

import net.anthony.telamod.block.ModBlocks;
import net.anthony.telamod.block.entity.ModBlockEntities;
import net.anthony.telamod.item.ModItems;
import net.anthony.telamod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelaMod implements ModInitializer {
	public static final String MOD_ID = "telamod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModScreenHandlers.registerScreenHandlers();
		ModBlockEntities.registerBlockEntities();
	}
}
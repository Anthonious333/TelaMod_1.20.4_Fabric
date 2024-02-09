package net.anthony.telamod;

import net.anthony.telamod.block.ModBlocks;
import net.anthony.telamod.screen.ModScreenHandlers;
import net.anthony.telamod.screen.TelaRailScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class TelaModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TELARAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DESTARAIL, RenderLayer.getCutout());
        HandledScreens.register(ModScreenHandlers.TELA_RAIL_SCREEN_HANDLER_SCREEN_HANDLER, TelaRailScreen::new);
    }
}

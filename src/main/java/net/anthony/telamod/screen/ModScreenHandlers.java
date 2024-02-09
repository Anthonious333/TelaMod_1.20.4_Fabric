package net.anthony.telamod.screen;

import net.anthony.telamod.TelaMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<TelaRailScreenHandler> TELA_RAIL_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TelaMod.MOD_ID, "telarail_screen"),
                    new ExtendedScreenHandlerType<>(TelaRailScreenHandler::new));

    public static void registerScreenHandlers() {
        TelaMod.LOGGER.info("Registering Screen Handlers for " + TelaMod.MOD_ID);
    }
}

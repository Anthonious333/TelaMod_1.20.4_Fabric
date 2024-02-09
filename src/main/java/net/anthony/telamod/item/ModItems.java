package net.anthony.telamod.item;

import net.anthony.telamod.TelaMod;
import net.anthony.telamod.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TELA_WRENCH = registerItem("tela_wrench", new TelaWrenchItem(new FabricItemSettings().maxCount(1)));

    private static void addItemsToRedStoneItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.TELARAIL);
        entries.add(ModBlocks.DESTARAIL);
        entries.add(TELA_WRENCH);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TelaMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TelaMod.LOGGER.info("Registering Mod Items for " + TelaMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(ModItems::addItemsToRedStoneItemGroup);
    }
}
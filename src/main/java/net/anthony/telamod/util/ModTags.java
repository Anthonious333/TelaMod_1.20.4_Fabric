package net.anthony.telamod.util;

import net.anthony.telamod.TelaMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {

    public static TagKey<Block> createTag(String name) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(TelaMod.MOD_ID, name));
    }
}

    public static class Items {


        public static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(TelaMod.MOD_ID, name));
        }
    }
}

package net.anthony.telamod.block.entity;

import net.anthony.telamod.TelaMod;
import net.anthony.telamod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
        public static final BlockEntityType<TelaRailBlockEntity> TELA_RAIL =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TelaMod.MOD_ID, "telarail_be"),
                    FabricBlockEntityTypeBuilder.create(TelaRailBlockEntity::new,
                            ModBlocks.TELARAIL).build());

        public static void registerBlockEntities() {
            TelaMod.LOGGER.info("Registering Block Entities for " + TelaMod.MOD_ID);
        }
}

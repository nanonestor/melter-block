package com.nanonestor.melter.registrate;

import com.nanonestor.melter.content.melter.MelterBlockEntity;
import com.nanonestor.melter.content.melter.MelterBlockRenderer;
import com.nanonestor.melter.Melter;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {
    public static final BlockEntityEntry<MelterBlockEntity> MELTER_BLOCK_ENTITY = Melter.registrate()
            //.blockEntity(TradingStationBlockEntity::new)
            .blockEntity("melter_block_entity", MelterBlockEntity::new)
            .validBlocks(ModBlocks.MELTER)
            .renderer(() -> MelterBlockRenderer::new)
            .register();
    public static void register() {

    }
}

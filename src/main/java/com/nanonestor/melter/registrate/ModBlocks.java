package com.nanonestor.melter.registrate;

import com.nanonestor.melter.content.melter.MelterBlock;
import com.nanonestor.melter.content.melter.MelterBlockEntity;
import com.nanonestor.melter.Melter;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class ModBlocks {
    public static final BlockEntry<MelterBlock> MELTER = Melter.registrate()
            .block("melter", MelterBlock::new)
            .lang("Melter")
            .blockstate((ctx, prov) ->
                    prov.getVariantBuilder(ctx.getEntry()).forAllStates(state -> {
                        String value = state.getValue(MelterBlock.HEAT_SOURCE).getSerializedName();
                        return ConfiguredModel.builder().modelFile(prov.models().getExistingFile(ResourceLocation.tryParse("melter:block/melter_" + value))).build();
                    })
           )
            .simpleItem()
            .blockEntity(MelterBlockEntity::new)
            .build()
            .register();
    public static void register() {

    }
}

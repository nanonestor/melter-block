package com.nanonestor.melter.registrate;

import com.nanonestor.melter.Melter;
import com.nanonestor.melter.content.melter.MelterBlock;
import com.nanonestor.melter.content.melter.MelterBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static com.nanonestor.melter.Melter.registrate;

public class ModBlocks {
    public static final BlockEntry<MelterBlock> MELTER = registrate.object("melter")
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

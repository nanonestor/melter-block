package com.oierbravo.melter.registrate;

import com.oierbravo.melter.Melter;
import com.oierbravo.melter.content.melter.MelterBlock;
import com.oierbravo.melter.content.melter.MelterBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static com.oierbravo.melter.Melter.registrate;

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

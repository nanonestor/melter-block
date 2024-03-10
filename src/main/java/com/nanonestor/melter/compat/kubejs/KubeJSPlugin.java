
package com.nanonestor.melter.compat.kubejs;

import com.nanonestor.melter.content.melter.MeltingRecipe;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;

public class KubeJSPlugin extends dev.latvian.mods.kubejs.KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.register(MeltingRecipe.Serializer.MELTING_TYPE, MeltingRecipeSchema.SCHEMA);
    }

}

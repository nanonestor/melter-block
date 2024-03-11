package com.oierbravo.melter.compat.kubejs;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.ListJS;

public class MeltingRecipeProcessing extends RecipeJS {
    public FluidStackJS outputFluid;
    public IngredientJS inputIngredient;
    @Override
    public void create(ListJS listJS) {
        outputFluid = FluidStackJS.of(listJS.get(0));
        inputIngredient = parseIngredientItem(listJS.get(1));
        json.addProperty("processingTime", 200);
        json.addProperty("minimumHeat", 1);
    }

    @Override
    public void deserialize() {
        inputIngredient = parseIngredientItem(json.get("ingredient"));
        outputFluid = FluidStackJS.fromJson(json.get("result"));
    }

    @Override
    public void serialize() {

        json.add("ingredient", inputIngredient.toJson());
        json.add("result",outputFluid.toJson());
    }
    public MeltingRecipeProcessing processingTime(int time) {
        json.addProperty("processingTime", time);
        json.addProperty("minimumHeat", 0);
        save();
        return this;
    }
    public MeltingRecipeProcessing minimumHeat(int minimum) {
        json.addProperty("minimumHeat", minimum);
        save();
        return this;
    }

}
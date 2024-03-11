package com.oierbravo.melter.content.melter;

import com.google.gson.JsonObject;
import com.oierbravo.melter.Melter;
import com.oierbravo.melter.foundation.fluid.FluidHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


public class MeltingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    //private final NonNullList<FluidStack> output;
    //private final NonNullList<Ingredient> input;

    private final FluidStack result;
    private final Ingredient ingredient;

    private final int processingTime;
    private final int heatlevel;

    private final int minimumHeat;

    public MeltingRecipe(ResourceLocation id, FluidStack result,
                         Ingredient ingredient, int processingTime, int heatlevel, int minimumHeat) {
        this.id = id;
        this.result = result;
        this.ingredient = ingredient;
        this.processingTime = processingTime;
        this.heatlevel = heatlevel;
        this.minimumHeat = minimumHeat;
        validate(id);
    }
    @Override
    public boolean matches(SimpleContainer pContainer, @NotNull Level pLevel) {
        return ingredient.test(pContainer.getItem(0));
    }
    public boolean matches(SimpleContainer pContainer, @NotNull Level pLevel, int heatLevel) {
        if(heatLevel < this.minimumHeat)
            return false;
        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
     public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
    public int  getProcessingTime() {
        return processingTime;
    }

    public int getHeatLevel() {return heatlevel;}
    public FluidStack getOutputFluidStack() {
        return result;
    }
    public FluidStack getOutput() {
        return result;
    }
    private void validate(ResourceLocation recipeTypeId) {
        String messageHeader = "Your custom " + recipeTypeId + " recipe (" + id.toString() + ")";
        Logger logger = Melter.LOGGER;
        if (processingTime > 0 && !canSpecifyDuration())
            logger.warn(messageHeader + " specified a duration. Durations have no impact on this type of recipe.");
    }

    private boolean canSpecifyDuration() {
        return true;
    }

    public int getOutputFluidAmount() {
        return getOutputFluidStack().getAmount();
    }

    public int getMinimumHeat() {
        return this.minimumHeat;
    }

    public static class Type implements RecipeType<MeltingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "melting";
    }
    public static class Serializer implements RecipeSerializer<MeltingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Melter.MODID,"melting");

        @Override
        public MeltingRecipe fromJson(ResourceLocation id, JsonObject json) {

            FluidStack output = FluidHelper.deserializeFluidStack(GsonHelper.getAsJsonObject(json,"result"));

            Ingredient input =  Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            int processingTime = 200;
            if (GsonHelper.isValidNode(json, "processingTime")) {
                processingTime = GsonHelper.getAsInt(json, "processingTime");
            }
            int minimumHeat = 0;
            if (GsonHelper.isValidNode(json, "minimumHeat")) {
                minimumHeat = GsonHelper.getAsInt(json, "minimumHeat");
            }
            return new MeltingRecipe(id, output, input, processingTime, minimumHeat, minimumHeat);
        }

        @Override
        public MeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            FluidStack output = FluidStack.readFromPacket(buf);


            int processingTime = buf.readInt();
            int minimumHeat = buf.readInt();
            return new MeltingRecipe(id, output, input, processingTime, minimumHeat, minimumHeat);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MeltingRecipe recipe) {
            recipe.ingredient.toNetwork(buf);
            recipe.result.writeToPacket(buf);
            buf.writeInt(recipe.getProcessingTime());
            buf.writeInt(recipe.getHeatLevel());
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }

    }
}

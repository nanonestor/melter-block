package com.nanonestor.melter.compat.jei;

import com.nanonestor.melter.Melter;
import com.nanonestor.melter.content.melter.MeltingRecipe;
import com.nanonestor.melter.registrate.ModBlocks;
import com.nanonestor.melter.registrate.ModGUITextures;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class MeltingRecipeCategory implements IRecipeCategory<MeltingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Melter.MODID, "melting");

    private final IDrawable background;
    private final IDrawable icon;

    public MeltingRecipeCategory(IGuiHelper helper) {
        //this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 59);
        this.background = new IDrawable() {
            @Override
            public int getWidth() {
                return 176;
            }

            @Override
            public int getHeight() {
                return 45;
            }

            @Override
            public void draw(@NotNull GuiGraphics guiGraphics, int xOffset, int yOffset) {

            }
        };
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MELTER.get()));

    }


    @Override
    public @NotNull RecipeType<MeltingRecipe> getRecipeType() {
        return RecipeType.create("melter","melting", MeltingRecipe.class);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("melting.recipe");
    }


    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MeltingRecipe recipe, @NotNull IFocusGroup focuses) {
        Ingredient input = recipe.getIngredient();
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 11).addIngredients(recipe.getIngredient());

        NonNullList<FluidStack> fluidList = NonNullList.create();
        fluidList.add(recipe.getOutput());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 11)
                .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(1, Component.literal(recipe.getOutput().getAmount() + "mB")) )
                .addIngredients(ForgeTypes.FLUID_STACK, fluidList);
    }

    @Override
    public void draw(@NotNull MeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        ModGUITextures.JEI_SHORT_ARROW.render(graphics, 75, 12);

        drawProcessingTime(recipe, graphics, 81,35);
    }
    protected void drawProcessingTime(MeltingRecipe recipe, GuiGraphics graphics, int x, int y) {
        int processingTime = recipe.getProcessingTime();
        if (processingTime > 0) {
            int cookTimeSeconds = processingTime / 20;
            MutableComponent timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            graphics.drawString(fontRenderer, timeString, x, y, 0xFF808080);

        }
    }
}

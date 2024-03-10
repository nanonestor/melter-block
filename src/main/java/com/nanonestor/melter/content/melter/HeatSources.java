package com.nanonestor.melter.content.melter;

import com.nanonestor.melter.Melter;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Arrays;

public enum HeatSources implements StringRepresentable {
    NONE(0,"", "None"),
    TORCH(1,"Block{minecraft:torch}", "Torch"),
    SOUL_TORCH(1,"Block{minecraft:soul_torch}", "Soul Torch"),
    WALL_TORCH(1,"Block{minecraft:torch}", "Torch"),
    CAMPFIRE(2,"Block{minecraft:campfire}", "Campfire"),
    SOUL_CAMPFIRE(2,"Block{minecraft:soul_campfire}", "Soul Campfire"),
    LAVA(3,"Block{minecraft:lava}", "Lava"),
    BLAZE_BURNER_INACTIVE(0,"create:blocks/blaze_burner:smouldering", "Blaze Inactive"),
    BLAZE_BURNER_FADING(1,"create:blocks/blaze_burner:fading","Blaze Active"),
    BLAZE_BURNER_ACTIVE(5,"create:blocks/blaze_burner:kindled","Blaze Active"),
    BLAZE_BURNER_SUPERHEATED(8,"create:blocks/blaze_burner:seething","Blaze SUPERHEATED!");



    // CREATE HeatLevel: NONE, SMOULDERING, FADING, KINDLED, SEETHING,;
    private int multiplier;
    private String resourceName;
    private String displayName;
    HeatSources (int multiplier, String resourceName, String displayName){
        this.multiplier = multiplier;
        this.resourceName = resourceName;
        this.displayName = displayName;
    }

    public static HeatSources fromLevel(Level level, BlockPos below) {
        BlockState belowBlockState = level.getBlockState(below);
        return HeatSources.get(belowBlockState);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String getResourceName() {
        return resourceName;
    }

    public static HeatSources get(String resourceName){
        return Arrays.stream(HeatSources.values())
                .filter(e -> e.resourceName.equals(resourceName))
                .findFirst()
                .orElse(HeatSources.NONE);
    }
    public static HeatSources get(BlockState blockState){
        String nameString = blockState.getBlock().getLootTable().toString();
        String blockString = blockState.getBlock().toString();
        if(blockString.equals(HeatSources.CAMPFIRE.getResourceName())){
            if(!blockState.getValue(BlockStateProperties.LIT)){
                return HeatSources.NONE;
            }
        }
        if(Melter.withCreate && (blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL))){
            BlazeBurnerBlock.HeatLevel heatLevel = blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL);
            nameString += ":" +heatLevel.getSerializedName();
            return get(nameString);
        }

        return get(blockString);
    }
    public static boolean isHeatSource(BlockState blockState){
        HeatSources source = HeatSources.get(blockState);
        if(source != HeatSources.NONE){
            return true;
        }
        return false;
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getDisplayName() {
        return displayName;
    }
}

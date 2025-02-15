package com.nanonestor.melter.registrate;

import com.nanonestor.melter.content.melter.MelterConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

//From https://github.com/McJty/TutorialV3/blob/1.19/src/main/java/com/example/tutorialv3/setup/Config.java
public class Config {
    public static void register() {
       // registerServerConfigs();
        registerCommonConfigs();
        //registerClientConfigs();
    }
  //  private static void registerClientConfigs() {
   //     ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
  //      ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
  //  }

    private static void registerCommonConfigs() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        MelterConfig.registerCommonConfig(COMMON_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

  //  private static void registerServerConfigs() {
   //     ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        //MelterConfig.registerServerConfig(SERVER_BUILDER);
    //    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
 //   }
}

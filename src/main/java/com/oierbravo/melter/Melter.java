package com.oierbravo.melter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.oierbravo.melter.content.melter.MelterBlock;
import com.oierbravo.melter.registrate.*;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;



// The value here should match an entry in the META-INF/mods.toml file
@Mod("melter")
public class Melter {
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "melter";
    public static final String DISPLAY_NAME = "Melter";

    // public static IEventBus modEventBus;
    public static Registrate registrate() {
        return registrate.get();
    }

    public static final NonNullSupplier<Registrate> registrate = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public static final boolean withCreate = ModList.get().isLoaded("create");


    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

   // public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

   // public static ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, MODID));
   // public static RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register(MODID, () -> {
    //    return CreativeModeTab.builder()
    //            .icon(() -> new ItemStack(Items.HOPPER))
    //            .title(Component.literal("Melter"))
    //            .build();
    //});


    public Melter() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);


        ModBlocks.register();
        ModRecipes.register(modEventBus);
        ModMessages.register();
        ModBlockEntities.register();

        // CREATIVE_MODE_TABS.register(modEventBus);

        registrate().addRawLang("melter.block.display", "Melter");
        registrate().addRawLang("melting.recipe", "Melting");
        registrate().addRawLang("melter.tooltip.progress", "Progress: %d%%");
        registrate().addRawLang("melter.tooltip.multiplier", "Heat multiplier: %d");
        registrate().addRawLang("melter.tooltip.multiplier_none", "§cNot heated!");
        registrate().addRawLang("config.jade.plugin_melter.melter_data", "Melter data");


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::afterServerStart);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        Config.register();

    }

    private void afterServerStart(ServerStartedEvent event) {

    }
}





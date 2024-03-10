package com.nanonestor.melter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.nanonestor.melter.registrate.*;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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

    public static final Registrate registrate = Registrate
            .create("melter");

    //public static final NonNullSupplier<Registrate> registrate = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    private final RegistryEntry<CreativeModeTab> meltercreativetab = registrate.object("melter")
            .defaultCreativeTab(tab -> tab.withLabelColor(0xFF00AA00))
            .register();
    public static final boolean withCreate = ModList.get().isLoaded("create");


    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public  IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public Melter() {
        
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.register();
        ModBlockEntities.register();
        ModRecipes.register(modEventBus);
        ModMessages.register();
        Config.register();


        registrate.addRawLang("melter.itemGroup.melter", "Test");
        registrate.addRawLang("melter.block.display", "Melter");
        registrate.addRawLang("melting.recipe", "Melting");
        registrate.addRawLang("melter.tooltip.progress", "Progress: %d%%");
        registrate.addRawLang("melter.tooltip.multiplier", "Heat multiplier: %d");
        registrate.addRawLang("melter.tooltip.multiplier_none", "§cNot heated!");
        registrate.addRawLang("config.jade.plugin_melter.melter_data", "Melter data");


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::afterServerStart);
    }


    private void onCommonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    //    CREATIVE_MODE_TABS.register(modEventBus);

    }

    private void afterServerStart(ServerStartedEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

    }


}





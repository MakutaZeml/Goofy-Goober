package com.zeml.rotp_zthm;

import com.zeml.rotp_zthm.capabilities.CapabilityHandler;
import com.zeml.rotp_zthm.init.*;
import com.zeml.rotp_zthm.network.AddonPackets;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtraHamonStandsAddon.MOD_ID)
public class ExtraHamonStandsAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_zthm";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExtraHamonStandsAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExtraHamonConfig.commonSpec);

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitParticles.PARTICLES.register(modEventBus);
        modEventBus.addListener(this::preInit);

    }

    private void preInit(FMLCommonSetupEvent event){
        CapabilityHandler.commonSetupRegister();
        AddonPackets.init();
        IntTags.iniTags();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

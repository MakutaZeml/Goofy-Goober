package com.zeml.rotp_zpb;

import com.zeml.rotp_zpb.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ItemStandAddon.MOD_ID)
public class ItemStandAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_zpb";
    public static final Logger LOGGER = LogManager.getLogger();

    public ItemStandAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitParticles.PARTICLES.register(modEventBus);

        IntTags.iniTags();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

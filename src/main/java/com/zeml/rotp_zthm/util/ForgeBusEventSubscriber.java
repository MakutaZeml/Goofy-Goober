package com.zeml.rotp_zthm.util;

import com.zeml.rotp_zthm.ExtraHamonConfig;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtraHamonStandsAddon.MOD_ID)
public class ForgeBusEventSubscriber {

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event){
        ExtraHamonConfig.Common.SyncedValues.onPlayerLogout((ServerPlayerEntity) event.getPlayer());
    }
}

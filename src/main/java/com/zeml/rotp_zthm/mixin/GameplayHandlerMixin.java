package com.zeml.rotp_zthm.mixin;

import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zhp.HermitConfig;
import com.zeml.rotp_zhp.capability.LivingDataProvider;
import com.zeml.rotp_zhp.util.GameplayHandler;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(value = GameplayHandler.class, remap = false)
public class GameplayHandlerMixin {

    @Inject(method = "onPlayerTick", cancellable = true, at = @At("HEAD"))
    private static void onPlayerLambdaTick(TickEvent.PlayerTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

}

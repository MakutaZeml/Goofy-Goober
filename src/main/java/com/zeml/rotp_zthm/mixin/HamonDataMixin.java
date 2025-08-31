package com.zeml.rotp_zthm.mixin;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.power.impl.nonstand.TypeSpecificData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = HamonData.class, remap = false)
public abstract class HamonDataMixin extends TypeSpecificData {

    public HamonDataMixin() {
    }


    @Inject(method = "tickEnergy", at = @At("RETURN"), cancellable = true)
    private void onTickEnergy(CallbackInfoReturnable<Float> cir){
        LivingEntity user = power.getUser();
        IStandPower.getStandPowerOptional(user).ifPresent(standPower->{
            if(standPower.getHeldAction() == InitStands.STAND_BREATH.get() && user.getAirSupply() >= user.getMaxAirSupply()){
                cir.cancel();
                cir.setReturnValue(power.getEnergy()+tickHamonBreath(InitStands.STAND_BREATH.get()));
            }
        });
    }

    @Shadow
    public abstract CompoundNBT writeNBT();

    @Shadow
    public abstract void readNBT(CompoundNBT var1);

    @Shadow
    public abstract void syncWithUserOnly(ServerPlayerEntity var1);

    @Shadow
    public abstract void syncWithTrackingOrUser(LivingEntity var1, ServerPlayerEntity var2);

    @Shadow
    public abstract float tickHamonBreath(Action<?> var1);




}

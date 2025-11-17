package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.playeranim.anim.ModPlayerAnimations;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zgfgb.client.sound.ModClientTickingSoundsHelper;
import com.zeml.rotp_zthm.init.AddonStands;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class HamonBreath extends StandEntityAction {

    public HamonBreath(StandEntityAction.Builder builder){
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        AtomicBoolean ham = new AtomicBoolean(false);
        INonStandPower.getNonStandPowerOptional(user).ifPresent(ipower -> {
            if (ipower.getType() == ModPowers.HAMON.get()) {
                ham.set(true);
            }
        });

        if (ham.get()){
            if (user.getAirSupply() < user.getMaxAirSupply()) {
                return conditionMessage("no_air");
            }
            return ActionConditionResult.POSITIVE;
        }
        return conditionMessage("no_hamon");
    }

    @Override
    public boolean canStaminaRegen(IStandPower standPower, StandEntity standEntity) {
        return true;
    }

    @Override
    public boolean enabledInHudDefault() {
        return false;
    }

    @Override
    public boolean clHeldStartAnim(PlayerEntity user) {
        return ModPlayerAnimations.hamonBreath.setAnimEnabled(user, true);
    }

    @Override
    public void clHeldStopAnim(PlayerEntity user) {
        ModPlayerAnimations.hamonBreath.setAnimEnabled(user, false);
    }


    @Override
    protected void playSoundAtStand(World world, StandEntity standEntity, SoundEvent sound, IStandPower standPower, Phase phase) {
        if (world.isClientSide()) {
            SoundEvent event = ModSounds.BREATH_DEFAULT.get();
            if(standPower.getType() == InitStands.GOOFY_GOOBER.getStandType()){
                event = ModSounds.BREATH_CAESAR.get();
            }else if(standPower.getType() == InitStands.HOUSE_EARTH.getStandType()){
                event = ModSounds.BREATH_LISA_LISA.get();
            }

            if (canBeCanceled(standPower, standEntity, phase, null)) {
                ModClientTickingSoundsHelper.playStandEntityCancelableActionSoundAtUser(standEntity, sound, this, phase, 1.0F, 1.0F, false);
                ModClientTickingSoundsHelper.playStandEntityCancelableActionSoundAtUser(standEntity, event,this,phase,1F,1F, false);
            }
            else {
                standEntity.playSound(sound, 1.0F, 1.0F, ClientUtil.getClientPlayer());
            }
        }
    }
}

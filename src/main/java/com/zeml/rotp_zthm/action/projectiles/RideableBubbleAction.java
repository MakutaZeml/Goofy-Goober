package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.client.playeranim.anim.HamonPlayerAnimations;
import com.zeml.rotp_zthm.entity.projectile.RideableBubble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class RideableBubbleAction extends StandEntityAction {
    private static final Map<LivingEntity, RideableBubble> BUBBLE_MAP = new HashMap<>();

    public RideableBubbleAction(StandEntityAction.Builder builder){
        super(builder);
    }

    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        return HamonBubbleLauncher.checkSoap(user);
    }

    @Override
    public void startedHolding(World world, LivingEntity user, IStandPower power, ActionTarget target, boolean requirementsFulfilled) {
        if (!world.isClientSide() && requirementsFulfilled) {
            RideableBubble rideableBubble = new RideableBubble(world, user, power);
            if(power.getStandInstance().isPresent()){
                rideableBubble.withStandSkin(power.getStandInstance().get().getSelectedSkin());
            }
            world.addFreshEntity(rideableBubble);
            BUBBLE_MAP.put(user,rideableBubble);
        }
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            RideableBubble bubble = BUBBLE_MAP.get(userPower.getUser());
            if(bubble != null){
                bubble.shootFromRotation(userPower.getUser(),.75F,1);
                userPower.getUser().startRiding(bubble);
                HamonBubbleLauncher.consumeSoap(userPower.getUser(), 50);
            }
        }
    }

    public boolean clHeldStartAnim(PlayerEntity user) {
        return HamonPlayerAnimations.rideBubble.setWindupAnim(user);
    }

    @Override
    public void clHeldStopAnim(PlayerEntity user) {
        HamonPlayerAnimations.rideBubble.stopAnim(user);
    }
}

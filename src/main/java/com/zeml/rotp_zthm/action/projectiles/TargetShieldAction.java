package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.entity.projectile.ShieldBubble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class TargetShieldAction extends StandEntityAction {

    public TargetShieldAction(StandEntityAction.Builder builder){
        super(builder);
    }

    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        return HamonBubbleLauncher.checkSoap(user);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            if(task.getTarget().getEntity() instanceof LivingEntity && userPower.getUser() != null){
                LivingEntity user = userPower.getUser();
                LivingEntity target = (LivingEntity) task.getTarget().getEntity();
                ShieldBubble shieldBubble = new ShieldBubble(world, user, false, userPower);
                shieldBubble.copyPosition(target);
                shieldBubble.withStandSkin(standEntity.getStandSkin());
                target.startRiding(shieldBubble);
                world.addFreshEntity(shieldBubble);
                HamonBubbleLauncher.consumeSoap(user, 50);
            }
        }
    }




    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }


}

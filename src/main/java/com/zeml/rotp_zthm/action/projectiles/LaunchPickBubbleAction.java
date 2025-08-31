package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.entity.projectile.PickUpBubbleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class LaunchPickBubbleAction extends StandAction {
    public LaunchPickBubbleAction(StandAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        return HamonBubbleLauncher.checkSoap(user);
    }


    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (requirementsFulfilled && !world.isClientSide()) {
            int bubblesCount = 4;
            HamonBubbleLauncher.TookSoapFrom soapSource = HamonBubbleLauncher.consumeSoap(user, 1);
            if (soapSource == HamonBubbleLauncher.TookSoapFrom.BOTTLE) {
                bubblesCount = 36;
            }
            for (int i = 0; i < bubblesCount; i++) {
                PickUpBubbleEntity bubbleEntity = new PickUpBubbleEntity(user, world);
                float velocity = 0.1F + user.getRandom().nextFloat() * 0.5F;
                bubbleEntity.shootFromRotation(user, velocity, 16.0F);
                if(power.getStandInstance().isPresent()){
                    bubbleEntity.withStandSkin(power.getStandInstance().get().getSelectedSkin());
                }
                world.addFreshEntity(bubbleEntity);
            }
        }
    }


}

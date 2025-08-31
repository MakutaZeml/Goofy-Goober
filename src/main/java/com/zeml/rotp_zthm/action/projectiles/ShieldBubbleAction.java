package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.entity.projectile.ShieldBubble;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShieldBubbleAction extends StandEntityAction {

    public ShieldBubbleAction(StandEntityAction.Builder builder){
        super(builder);
    }


    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        if(user.isShiftKeyDown() && target.getEntity() instanceof LivingEntity){
            return InitStands.SHIELD_BUBBLE_TARGET.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        return HamonBubbleLauncher.checkSoap(user);
    }


    @Override
    public void startedHolding(World world, LivingEntity user, IStandPower power, ActionTarget target, boolean requirementsFulfilled) {
        if(!world.isClientSide){
            ShieldBubble shieldBubble = new ShieldBubble(world, user, true, power);
            if(power.getStandManifestation() instanceof StandEntity){
                StandEntity stand = (StandEntity) power.getStandManifestation();
                shieldBubble.withStandSkin(stand.getStandSkin());
                shieldBubble.startRiding(stand);
            }
            world.addFreshEntity(shieldBubble);
            HamonBubbleLauncher.consumeSoap(user, 50);
        }
    }


    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if(!world.isClientSide){
            ItemStack itemStack = BubbleLauncher.getSoapItem(user);
            if(!itemStack.isEmpty()){
                if(TommyGunItem.getAmmo(itemStack) <= 0){
                    power.stopHeldAction(false);
                    power.setCooldownTimer(this,60);
                }
            }
        }
    }


}

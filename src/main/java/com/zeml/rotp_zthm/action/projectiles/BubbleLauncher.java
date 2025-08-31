package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonActions;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zthm.entity.projectile.HamonStandBubbleEntity;
import com.zeml.rotp_zthm.init.InitStands;
import com.zeml.rotp_zthm.item.StandGlovesItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class BubbleLauncher extends StandEntityAction {

    public BubbleLauncher(StandEntityAction.Builder builder){
        super(builder);
    }



    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        ItemStack item = getSoapItem(user);
        if(item.getItem() instanceof StandGlovesItem){
            if(TommyGunItem.getAmmo(item) <= 0){
                return conditionMessage("gloves_no_soap");
            }
            return ActionConditionResult.POSITIVE;
        }
        return conditionMessage("stand_gloves");
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
                HamonStandBubbleEntity bubbleEntity = new HamonStandBubbleEntity(user, world);
                float velocity = 0.1F + user.getRandom().nextFloat() * 0.5F;
                bubbleEntity.shootFromRotation(user, velocity, 16.0F);
                if(power.getStandInstance().isPresent()) bubbleEntity.withStandSkin(power.getStandInstance().get().getSelectedSkin());
                world.addFreshEntity(bubbleEntity);
            }
            INonStandPower.getNonStandPowerOptional(user).ifPresent(iNonStandPower -> iNonStandPower.consumeEnergy(38));
        }
    }

    public static ItemStack getSoapItem(LivingEntity entity) {
        ItemStack soapItem = entity.getMainHandItem();
        if (!soapItem.isEmpty() &&
                (soapItem.getItem() instanceof StandGlovesItem )) {
            return soapItem;
        }

        soapItem = entity.getOffhandItem();
        if (!soapItem.isEmpty() &&
                (soapItem.getItem() instanceof StandGlovesItem )) {
            return soapItem;
        }

        return ItemStack.EMPTY;
    }




    @Override
    public boolean isUnlocked(IStandPower power) {
        return INonStandPower.getNonStandPowerOptional(power.getUser()).map(iNonStandPower -> {
            Optional<HamonData> hamonDataOptional = iNonStandPower.getTypeSpecificData(ModPowers.HAMON.get());
            return hamonDataOptional.map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.BUBBLE_LAUNCHER.get())).orElse(false);
        }).orElse(false) && power.getType() == InitStands.GOOFY_GOOBER.getStandType();
    }
}

package com.zeml.rotp_zthm.action.projectiles;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonUtil;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zthm.entity.projectile.HamonStandBubbleCutterEntity;
import com.zeml.rotp_zthm.init.InitStands;
import com.zeml.rotp_zthm.item.StandGlovesItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

import static com.zeml.rotp_zthm.action.projectiles.BubbleLauncher.getSoapItem;

public class HamonBubbleCutter extends StandEntityAction {

    public HamonBubbleCutter(StandEntityAction.Builder builder){
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
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity user = userPower.getUser();
            HamonBubbleLauncher.consumeSoap(user, 20);

            boolean shift = isShiftVariation();
            int bubbles = shift ? 4 : 8;
            Vector3d shootingPos = null;
            for (int i = 0; i < bubbles; i++) {
                HamonStandBubbleCutterEntity bubbleCutterEntity = new HamonStandBubbleCutterEntity(user, world);
                float velocity = 1.35F + user.getRandom().nextFloat() * 0.3F;
                bubbleCutterEntity.setGliding(shift);
                bubbleCutterEntity.setHamonStatPoints(shift? 600:500/ 10F);
                bubbleCutterEntity.shootFromRotation(user, velocity, shift ? 2.0F : 8.0F);
                if (i == 0) shootingPos = bubbleCutterEntity.position();
                bubbleCutterEntity.withStandSkin(standEntity.getStandSkin());
                world.addFreshEntity(bubbleCutterEntity);
            }
            INonStandPower.getNonStandPowerOptional(user).ifPresent(iNonStandPower -> iNonStandPower.consumeEnergy(shift? 450:375));

            HamonUtil.emitHamonSparkParticles(world, null, shootingPos.x, shootingPos.y, shootingPos.z, 0.75F);
        }
    }


    @Override
    public boolean isUnlocked(IStandPower power) {
        return INonStandPower.getNonStandPowerOptional(power.getUser()).map(iNonStandPower -> {
            Optional<HamonData> hamonDataOptional = iNonStandPower.getTypeSpecificData(ModPowers.HAMON.get());
            return hamonDataOptional.map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.BUBBLE_CUTTER.get())).orElse(false);
        }).orElse(false) && power.getType() == InitStands.GOOFY_GOOBER.getStandType();
    }

}

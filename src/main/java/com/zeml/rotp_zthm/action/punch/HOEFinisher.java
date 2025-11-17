package com.zeml.rotp_zthm.action.punch;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandBlockPunch;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.HamonSendoOverdriveEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.damage.KnockbackCollisionImpact;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Optional;
import java.util.function.Supplier;

public class HOEFinisher extends StandEntityHeavyAttack {
    private final Supplier<SoundEvent> punchSound;
    public HOEFinisher(StandEntityHeavyAttack.Builder builder,  Supplier<SoundEvent> punchSound){
        super(builder);
        this.punchSound = punchSound;
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        double strength = stand.getAttackDamage();
        if(target instanceof LivingEntity && stand.getUser() != null){
            LivingEntity livingEntity = (LivingEntity) target;
            float damage = 10;
            LivingEntity user = stand.getUser();
            if (DamageUtil.dealHamonDamage(target, damage, user, null, attack -> attack.hamonParticle(ModParticles.HAMON_SPARK_YELLOW.get()))) {
                target.level.playSound(null, target.getX(), target.getEyeY(), target.getZ(), ModSounds.HAMON_SYO_PUNCH.get(), target.getSoundSource(), 1, 1.0F);
                livingEntity.knockback(2.5F, user.getX() - target.getX(), user.getZ() - target.getZ());
                INonStandPower.getNonStandPowerOptional(user).ifPresent(ipower->{
                    ipower.getTypeSpecificData(ModPowers.HAMON.get()).ifPresent(userHamon->{
                        boolean hamonSpread = userHamon.isSkillLearned(ModHamonSkills.HAMON_SPREAD.get());
                        float punchDamage = damage;
                        KnockbackCollisionImpact.getHandler(target).ifPresent(cap -> {
                            cap.onPunchSetKnockbackImpact(target.getDeltaMovement(), user);
                            if (hamonSpread) {
                                cap.hamonDamage(punchDamage, 0, ModParticles.HAMON_SPARK_YELLOW.get());
                            }
                        });
                    });
                });
            }
        }
        return new HeavyPunchInstance(stand, target, dmgSource)
                .damage(0)
                .addKnockback(0.5F + (float) strength / (8 - stand.getLastHeavyFinisherValue() * 4))
                .setStandInvulTime(10)
                .impactSound(punchSound);
    }


    @Override
    public StandBlockPunch punchBlock(StandEntity stand, BlockPos pos, BlockState state, Direction face) {
        if(stand.getUser() != null){
            if(INonStandPower.getNonStandPowerOptional(stand.getUser()).map(ipower->
                    ipower.getTypeSpecificData(ModPowers.HAMON.get()).map(hamonData ->
                            hamonData.isSkillLearned(ModHamonSkills.SENDO_OVERDRIVE.get()))
                    .orElse(false) && ipower.getEnergy()>=900).orElse(false)){
                return new HeavyPunchSendoBlockInstance(stand,pos,state,face);
            }
        }
        return super.punchBlock(stand, pos, state, face);
    }


    public static class HeavyPunchSendoBlockInstance extends StandBlockPunch{
        private final Direction sendoFace;

        public HeavyPunchSendoBlockInstance(StandEntity stand, BlockPos targetPos, BlockState blockState, Direction face) {
            super(stand, targetPos, blockState);
            this.sendoFace = face;
        }


        @Override
        public boolean doHit(StandEntityTask task) {
            if(stand.getUser() != null && !stand.level.isClientSide){
                Optional<HamonData> optional = INonStandPower.getNonStandPowerOptional(stand.getUser()).map(ipower->ipower.getTypeSpecificData(ModPowers.HAMON.get())).orElse(Optional.empty());
                if(optional.isPresent() && this.sendoFace != null){
                    HamonData hamonData = optional.get();
                    float energyCost = 900;
                    float hamonEfficiency = hamonData.getActionEfficiency(energyCost, true, ModHamonSkills.SENDO_OVERDRIVE.get());
                    BlockPos blockPos = this.blockPos;
                    HamonSendoOverdriveEntity sendoOverdrive = new HamonSendoOverdriveEntity(this.stand.level,
                            this.stand.getUser(), this.sendoFace.getAxis())
                            .setRadius((hamonData.getHamonControlLevelRatio() * 2) * hamonEfficiency)
                            .setWaveDamage(0.85F * hamonEfficiency)
                            .setWavesCount(2 + (int) ((2 + Math.min(hamonData.getHamonControlLevelRatio() * 3, 2)) * hamonEfficiency))
                            .setStatPoints(Math.min(energyCost, energyCost) * hamonEfficiency);
                    sendoOverdrive.moveTo(Vector3d.atCenterOf(blockPos).subtract(0, sendoOverdrive.getDimensions(null).height * 0.5, 0));
                    sendoOverdrive.setBlockTarget(blockPos, sendoFace);
                    stand.level.addFreshEntity(sendoOverdrive);
                    return true;
                }
            }

            return false;
        }
    }


}

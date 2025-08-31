package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonActions;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.skill.BaseHamonSkill;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.zeml.rotp_zthm.init.InitEntities;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;


public class RideableBubble extends BubblesEntity {
    private boolean shot;
    private IStandPower power;

    public RideableBubble(World world, LivingEntity user, IStandPower power){
        super(InitEntities.RIDE_BUBBLE.get(),user,world);
        this.power = power;
    }


    public RideableBubble(EntityType<? extends RideableBubble> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide()) {
            if (!this.shot) {
                if (power != null) {
                    if (power.getHeldAction() != InitStands.RIDE_BUBBLE.get()) {
                        remove();
                    } else if (power.getHeldActionTicks() >= InitStands.RIDE_BUBBLE.get().getHoldDurationToFire(power) - 1) {
                        shot = true;
                    }
                }
                this.tickCount = 0;
            } else {
                if (tickCount > ticksLifespan()) {
                    this.remove();
                }
                if(this.getOwner() != null && this.getPassengers().contains(this.getOwner())){
                    double f = this.getDeltaMovement().length();
                    float xRot = this.getOwner().getViewXRot(1);
                    float yRot = this.getOwner().getViewYRot(1);
                    f = Math.max(f,.75);
                    double dX = MathHelper.cos(-xRot*(float) Math.PI/180 ) *(MathHelper.sin(-yRot * ((float)Math.PI / 180F)) * f);
                    double dY = f* MathHelper.sin(-xRot*(float) Math.PI/180 );
                    double dZ = MathHelper.cos(-xRot*(float) Math.PI/180 ) *(MathHelper.cos(-yRot * ((float)Math.PI / 180F)) * f);
                    this.setDeltaMovement(dX,dY,dZ);

                }

            }
        }
    }


    @Override
    protected boolean hurtTarget(Entity target, @Nullable LivingEntity owner) {
        if(owner != null){
            INonStandPower.getNonStandPowerOptional(owner).map(ipower ->{
                Optional<HamonData> hamonDataOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
                if(hamonDataOp.isPresent()){
                    return DamageUtil.dealHamonDamage(target, 2.5F, this, owner) && hamonDataOp.map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.ARROW_INFUSION.get())).orElse(false);
                }
                return false;
            });

        }
        return super.hurtTarget(target, owner);
    }

    @Override
    protected void afterEntityHit(EntityRayTraceResult entityRayTraceResult, boolean entityHurt) {
        if (entityHurt) {
            LivingEntity owner = getOwner();
            if (owner != null) {
                INonStandPower.getNonStandPowerOptional(owner).ifPresent(power -> {
                    power.getTypeSpecificData(ModPowers.HAMON.get()).ifPresent(hamon -> {
                        hamon.hamonPointsFromAction(BaseHamonSkill.HamonStat.STRENGTH, ModHamonActions.CAESAR_BUBBLE_BARRIER.get().getHeldTickEnergyCost(power) / 4F);
                    });
                });
            }
        }
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }


    @Override
    public int getTicksToHoming() {
        return 0;
    }

    @Override
    public int ticksLifespan() {
        return 150;
    }

    @Override
    protected float getBaseDamage() {
        return 0;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0;
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    public float getSize(float partialTick) {
        return Math.min((tickCount + partialTick) / (float) InitStands.RIDE_BUBBLE.get().getHoldDurationToFire(null), 1);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("shot", this.shot);

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        this.shot = nbt.getBoolean("shot");
    }



    private boolean prevTickInput = false;
    public void controlBubble(){

    }


}

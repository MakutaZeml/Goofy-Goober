package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.action.non_stand.HamonOrganismInfusion;
import com.github.standobyte.jojo.client.particle.custom.CustomParticlesHelper;
import com.github.standobyte.jojo.client.sound.HamonSparksLoopSound;
import com.github.standobyte.jojo.entity.HamonBlockChargeEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.HamonBubbleEntity;
import com.github.standobyte.jojo.init.ModEntityTypes;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonActions;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.skill.BaseHamonSkill;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.zeml.rotp_zthm.init.InitEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

public class HamonStandBubbleEntity extends BubblesEntity{
    public HamonStandBubbleEntity(LivingEntity shooter, World world) {
        super(InitEntities.HAMON_BUBBLE.get(), shooter, world);
    }



    public HamonStandBubbleEntity(EntityType<HamonStandBubbleEntity> hamonStandBubbleEntityEntityType, World world) {
        super(hamonStandBubbleEntityEntityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide() && tickCount % 10 == getId() % 10) {
            HamonSparksLoopSound.playSparkSound(this, position(), 0.25F, true);
            CustomParticlesHelper.createHamonSparkParticles(this, position(), 1);
        }
    }

    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner) {
        return DamageUtil.dealHamonDamage(target, 0.3F, this, owner);
    }

    @Override
    protected void afterEntityHit(EntityRayTraceResult entityRayTraceResult, boolean entityHurt) {
        if (entityHurt) {
            LivingEntity owner = getOwner();
            if (owner != null) {
                INonStandPower.getNonStandPowerOptional(owner).ifPresent(power -> {
                    power.getTypeSpecificData(ModPowers.HAMON.get()).ifPresent(hamon -> {
                        hamon.hamonPointsFromAction(BaseHamonSkill.HamonStat.STRENGTH, ModHamonActions.CAESAR_BUBBLE_LAUNCHER.get().getHeldTickEnergyCost(power) / 4F);
                    });
                });
            }
        }
    }

    @Override
    protected void afterBlockHit(BlockRayTraceResult blockRayTraceResult, boolean blockDestroyed) {
        super.afterBlockHit(blockRayTraceResult, blockDestroyed);
        if(this.getOwner() != null){
            INonStandPower.getNonStandPowerOptional(this.getOwner()).ifPresent(ipower->{
                Optional<HamonData> hamonOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
                if(hamonOp.isPresent()){
                    BlockPos blockPos = blockRayTraceResult.getBlockPos();
                    HamonData hamon = hamonOp.get();
                    if(hamon.isSkillLearned(ModHamonSkills.PLANT_BLOCK_INFUSION.get())&&
                            this.getOwner().hasEffect(ModStatusEffects.RESOLVE.get()) &&
                            HamonOrganismInfusion.isBlockLiving(level.getBlockState(blockRayTraceResult.getBlockPos()))
                            && Math.random() < .1
                    ){
                        {
                            float hamonEfficiency = hamon.getActionEfficiency(200, true);
                            int chargeTicks = 100 + MathHelper.floor((float) (1100 * hamon.getHamonStrengthLevel())
                                    / (float) HamonData.MAX_STAT_LEVEL * hamonEfficiency * hamonEfficiency);
                            level.getEntitiesOfClass(HamonBlockChargeEntity.class,
                                    new AxisAlignedBB(Vector3d.atCenterOf(blockPos), Vector3d.atCenterOf(blockPos))).forEach(Entity::remove);
                            HamonBlockChargeEntity charge = new HamonBlockChargeEntity(level, blockPos);
                            charge.setCharge(0.02F * hamon.getHamonDamageMultiplier() * hamonEfficiency, chargeTicks, this.getOwner(), 200);
                            level.addFreshEntity(charge);
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    public float getBaseDamage() {
        return 0;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0.0F;
    }

    @Override
    public int ticksLifespan() {
        return 75;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
    }
}

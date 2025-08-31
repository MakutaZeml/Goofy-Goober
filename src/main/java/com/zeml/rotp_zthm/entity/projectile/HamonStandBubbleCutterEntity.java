package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.action.non_stand.HamonOrganismInfusion;
import com.github.standobyte.jojo.client.particle.custom.CustomParticlesHelper;
import com.github.standobyte.jojo.client.sound.HamonSparksLoopSound;
import com.github.standobyte.jojo.entity.HamonBlockChargeEntity;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
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
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

public class HamonStandBubbleCutterEntity extends BubblesEntity{
    private boolean gliding;
    private float hamonStatPoints;

    public HamonStandBubbleCutterEntity(LivingEntity shooter, World world) {
        super(InitEntities.HAMON_BUBBLE_CUTTER.get(), shooter, world);
    }

    public HamonStandBubbleCutterEntity(EntityType<? extends HamonStandBubbleCutterEntity> type, World world) {
        super(type, world);
    }

    public void setGliding(boolean gliding) {
        this.gliding = gliding;
    }

    public void setHamonStatPoints(float points) {
        this.hamonStatPoints = points;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            HamonSparksLoopSound.playSparkSound(this, position(), 0.25F);
            CustomParticlesHelper.createHamonSparkParticles(this, position(), 1);
        }
    }

    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner) {
        boolean projectileAttack = super.hurtTarget(target, owner);
        boolean hamonAttack = DamageUtil.dealHamonDamage(target, 0.3F, this, owner);
        return projectileAttack || hamonAttack;
    }

    @Override
    protected void afterEntityHit(EntityRayTraceResult entityRayTraceResult, boolean entityHurt) {
        if (entityHurt) {
            LivingEntity owner = getOwner();
            if (owner != null) {
                INonStandPower.getNonStandPowerOptional(owner).ifPresent(power -> {
                    power.getTypeSpecificData(ModPowers.HAMON.get()).ifPresent(hamon -> {
                        hamon.hamonPointsFromAction(BaseHamonSkill.HamonStat.STRENGTH, hamonStatPoints);
                    });
                });
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        if (gliding && result.getDirection().getAxis() == Direction.Axis.Y) {
            Vector3d movementVec = getDeltaMovement();
            Vector3d newVec = new Vector3d(movementVec.x, 0, movementVec.z);
            this.setDeltaMovement(newVec.scale(Math.sqrt(movementVec.lengthSqr() / newVec.lengthSqr())));
        }
        else {
            super.onHitBlock(result);
        }
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    public float getBaseDamage() {
        return 1.0F;
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
                            && Math.random() < .25
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
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Gliding", gliding);
        nbt.putFloat("Points", hamonStatPoints);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        gliding = nbt.getBoolean("Gliding");
        hamonStatPoints = nbt.getFloat("Points");
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        buffer.writeBoolean(gliding);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        this.gliding = additionalData.readBoolean();
    }

}

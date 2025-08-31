package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.util.mc.EntityOwnerResolver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class BubblesEntity extends ModdedProjectileEntity {
    private EntityOwnerResolver homingTarget = new EntityOwnerResolver();
    private boolean isHoming = false;

    public BubblesEntity(EntityType<? extends ModdedProjectileEntity> type, LivingEntity shooter, World world) {
        super(type, shooter, world);
    }

    public BubblesEntity(EntityType<? extends ModdedProjectileEntity> type, World world){
        super(type,world);
    }



    @Override
    protected void moveProjectile(){
        super.moveProjectile();
        if(this.tickCount > this.getTicksToHoming()){
            Entity target = this.homingTarget.getEntity(level);
            if(target != null){
                Vector3d targetPos = target.getBoundingBox().getCenter();
                Vector3d vecToTarget = targetPos.subtract(this.position());
                setDeltaMovement(vecToTarget.normalize().scale(this.getDeltaMovement().length()));
            }
        }
    }



    public boolean isHoming(){
        return this.isHoming;
    }

    public void setHoming(boolean homing){
        this.isHoming = homing;
    }

    public Entity getTarget(){
        return homingTarget.getEntity(this.level);
    }

    public void setTarget(LivingEntity target) {
        homingTarget.setOwner(target);
    }


    public int getTicksToHoming(){
        return 2;
    }


    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        homingTarget.saveNbt(nbt, "HomingTarget");
        nbt.putBoolean("isHoming", this.isHoming);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        homingTarget.loadNbt(nbt, "HomingTarget");
        this.isHoming = nbt.getBoolean("isHoming");
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        homingTarget.writeNetwork(buffer);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        homingTarget.readNetwork(additionalData);
    }

}

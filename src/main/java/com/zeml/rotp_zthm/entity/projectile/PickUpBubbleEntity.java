package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.init.InitEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class PickUpBubbleEntity extends BubblesEntity{


    public PickUpBubbleEntity(LivingEntity shooter, World world) {
        super(InitEntities.PICK_ITEM_BUBBLE.get(), shooter, world);
    }

    public PickUpBubbleEntity(EntityType<? extends PickUpBubbleEntity> type, World world) {
        super(type, world);
    }


    @Override
    public void tick() {
        super.tick();
        if(this.getPassengers().isEmpty()){
            if(!this.level.isClientSide){
                List<ItemEntity> list = MCUtil.entitiesAround(ItemEntity.class,this,this.getBbHeight()*1.25,false, itemEntity -> itemEntity.isAlive() && itemEntity.getVehicle() == null);
                if(!list.isEmpty()){
                    ItemEntity itemEntity = list.get(0);
                    itemEntity.startRiding(this);
                    if(this.getOwner() != null){
                        this.setTarget(this.getOwner());
                        this.setHoming(true);
                    }
                }

            }
            if(this.isHoming() && this.getPassengers().isEmpty() && !level.isClientSide){
                this.remove();
            }
        }
    }



    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        if(this.isHoming() && this.getOwner() != null && this.getOwner() == entityRayTraceResult.getEntity()) {
            this.remove();
        }
        super.onHitEntity(entityRayTraceResult);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        if(!this.isHoming()){
            if(this.getPassengers().isEmpty()){
                List<ItemEntity> list = MCUtil.entitiesAround(ItemEntity.class,this,this.getBbHeight()*1.25,false, itemEntity -> itemEntity.isAlive() && itemEntity.getVehicle() == null);
                if(!list.isEmpty()){
                    ItemEntity itemEntity = list.get(0);
                    itemEntity.startRiding(this);
                    if(this.getOwner() != null){
                        this.setTarget(this.getOwner());
                        this.setHoming(true);
                        ExtraHamonStandsAddon.LOGGER.debug("This is actually weird {} {}", this.getPassengers(), blockRayTraceResult.getBlockPos());
                        if(!ricochet(blockRayTraceResult.getDirection())){
                            super.onHitBlock(blockRayTraceResult);
                        }
                    }else {
                        super.onHitBlock(blockRayTraceResult);
                    }
                }
            }else {
                ricochet(blockRayTraceResult.getDirection());
            }
        }else {
            ricochet(blockRayTraceResult.getDirection());
        }
    }


    private boolean ricochet(Direction hitSurfaceDirection) {
        if (hitSurfaceDirection != null) {
            Vector3d motion = getDeltaMovement();
            Vector3d motionNew;
            switch (hitSurfaceDirection.getAxis()) {
                case X:
                    motionNew = new Vector3d(-motion.x, motion.y, motion.z);
                    break;
                case Y:
                    motionNew = new Vector3d(motion.x, -motion.y, motion.z);
                    break;
                case Z:
                    motionNew = new Vector3d(motion.x, motion.y, -motion.z);
                    break;
                default:
                    return false;
            }
            if (JojoModUtil.rayTrace(position(), motionNew, 16, level, this,
                    EntityPredicates.NO_SPECTATORS.and(EntityPredicates.ENTITY_STILL_ALIVE), 1.0, 0).getType() == RayTraceResult.Type.MISS) {
                return false;
            }
            setDeltaMovement(motionNew);
            rotateTowardsMovement(1.0F);
            return true;
        }
        return false;
    }

    @Override
    public int ticksLifespan() {
        return this.isHoming()?Integer.MAX_VALUE: 200;
    }

    public int getTicksToHoming(){
        return 0;
    }

    @Override
    public double getPassengersRidingOffset() {
        return -this.getBbHeight();
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
}

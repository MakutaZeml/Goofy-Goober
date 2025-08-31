package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.action.projectiles.BubbleLauncher;
import com.zeml.rotp_zthm.init.InitEntities;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ShieldBubble extends BubblesEntity{
    private static final DataParameter<Float> HEALTH = EntityDataManager.defineId(ShieldBubble.class, DataSerializers.FLOAT);
    protected static final Vector3d DEFAULT_POS_OFFSET = new Vector3d(0.0D, -1.6D, 0.0D);
    private boolean isOwnerTarget;
    private IStandPower power;

    @Override
    protected Vector3d getOwnerRelativeOffset() {
        return DEFAULT_POS_OFFSET;
    }

    public ShieldBubble(World world, LivingEntity shooter, boolean isOwnerTarget, IStandPower power){
        super(InitEntities.DEFENSE_BUBBLE.get(),shooter, world);
        this.isOwnerTarget = isOwnerTarget;
        this.power = power;
    }

    public ShieldBubble(EntityType<ShieldBubble> shieldBubbleEntityType, World world) {
        super(shieldBubbleEntityType, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEALTH, 50F);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            pushEntities();
            if(this.getHealth() <= 0){
                this.remove();
            }
        }

    }

    @Override
    public int ticksLifespan() {
        if(this.isOwnerTarget && power !=null){
            return power.getHeldAction() == InitStands.SHIELD_BUBBLE.get() ? Integer.MAX_VALUE:0;
        }
        return this.getPassengers().isEmpty()?0: 200;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(this.getOwner() != null){
            if(INonStandPower.getNonStandPowerOptional(this.getOwner()).map(iPower ->{
                Optional<HamonData> hamonDataOptional = iPower.getTypeSpecificData(ModPowers.HAMON.get());
                return hamonDataOptional.map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.PROJECTILE_SHIELD.get())).orElse(false);
            }).orElse(false)){
                source.getDirectEntity().setDeltaMovement(source.getDirectEntity().getDeltaMovement().reverse());
                if(source.getDirectEntity() instanceof ModdedProjectileEntity){
                    ((ModdedProjectileEntity) source.getDirectEntity()).setIsDeflected();
                }
                return false;
            }
            if(this.isOwnerTarget){
                ItemStack stack = BubbleLauncher.getSoapItem(this.getOwner());
                if(!stack.isEmpty()){
                    int soap = 3*Math.round(amount);
                    if(soap < TommyGunItem.getAmmo(stack)){
                        HamonBubbleLauncher.consumeSoap(this.getOwner(),soap);
                        return false;
                    }else {
                        HamonBubbleLauncher.consumeSoap(this.getOwner(),soap);
                        if(power != null){
                            power.setCooldownTimer(InitStands.SHIELD_BUBBLE.get(), 60);
                        }
                        return true;
                    }
                }
                return true;
            }else {
                this.setHealth(this.getHealth()-amount);
            }
        }
        return true;
    }

    protected void pushEntities(){
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate(.65), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()){
            ExtraHamonStandsAddon.LOGGER.debug("{}", list);
            list.forEach(entity -> {
                if((this.getOwner() == entity &&  this.isOwnerTarget) || entity instanceof ProjectileEntity){
                    return;
                }
                this.push(entity);
            });
        }
    }

    public float getHealth(){
        return this.entityData.get(HEALTH);
    }

    public boolean isOwnerTarget() {
        return this.isOwnerTarget;
    }

    public void setOwnerTarget(boolean isOwnerTarget){
        this.isOwnerTarget = isOwnerTarget;
    }

    public void setHealth(float health){
        this.entityData.set(HEALTH, health);
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
        return false;
    }


    @Override
    public double getPassengersRidingOffset() {
        if(!this.getPassengers().isEmpty() && !this.getPassengers().get(0).getPassengers().isEmpty()){
            return -this.getPassengers().get(0).getPassengersRidingOffset();
        }
        return 0;
    }


    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isOwnerStuff", this.isOwnerTarget);
        nbt.putFloat("health", this.entityData.get(HEALTH));
    }

    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity livingEntity) {
        return livingEntity.position();
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.isOwnerTarget = nbt.getBoolean("isOwnerStuff");
        this.entityData.set(HEALTH, nbt.getFloat("health"));
    }
}

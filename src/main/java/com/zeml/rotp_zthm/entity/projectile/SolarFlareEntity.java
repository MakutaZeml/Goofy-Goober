package com.zeml.rotp_zthm.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.HamonBlockChargeEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonUtil;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.init.InitEntities;
import com.zeml.rotp_zthm.init.InitParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

import static com.github.standobyte.jojo.action.non_stand.HamonOrganismInfusion.isBlockLiving;

public class SolarFlareEntity extends ModdedProjectileEntity {

    public SolarFlareEntity(EntityType<? extends SolarFlareEntity> type, World world) {
        super(type, world);
    }

    public SolarFlareEntity(LivingEntity shooter, World world, boolean right){
        super(InitEntities.SOLAR_FLARE.get(),shooter,world);
        this.right = right;
    }
    private boolean right;
    private static final Vector3d LEFT = new Vector3d(0.75, -0.3D, 0.0D);
    private static final Vector3d RIGHT = new Vector3d(-LEFT.x, LEFT.y, LEFT.z);
    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            if(ClientUtil.canSeeStands()){
                this.level.addParticle(InitParticles.SOLAR.get(), this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0, 0, 0);
            }
        }
    }

    @Override
    protected boolean hurtTarget(Entity target, DamageSource dmgSource, float dmgAmount) {
        if(this.getOwner() != null && target instanceof LivingEntity){
            LivingEntity user = StandUtil.getStandUser(this.getOwner());
            ExtraHamonStandsAddon.LOGGER.debug("user {}", user.getName().getString());
            return  DamageUtil.dealHamonDamage(target,.3F,user,this);
        }
        return super.hurtTarget(target, dmgSource, dmgAmount);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        if(this.getOwner() != null){
            LivingEntity user = StandUtil.getStandUser(this.getOwner());
            BlockPos blockPos = blockRayTraceResult.getBlockPos();
            BlockState blockState =this.level.getBlockState(blockPos);
            Block block = blockState.getBlock();
            boolean isLivingBlock;
            if (isBlockLiving(blockState)) {
                isLivingBlock = true;
            }
            else if (block instanceof FlowerPotBlock && blockState.getBlock() != Blocks.FLOWER_POT) {
                FlowerPotBlock flowerPot = (FlowerPotBlock) block;
                ItemStack flowerPotContents = flowerPot.getCloneItemStack(level, blockPos, blockState);
                isLivingBlock = HamonUtil.isItemLivingMatter(flowerPotContents);
            }
            else {
                isLivingBlock = false;
            }
            if (isLivingBlock){
                Optional<HamonData> optional = INonStandPower.getNonStandPowerOptional(user).map(iNonStandPower -> iNonStandPower.getTypeSpecificData(ModPowers.HAMON.get())).orElse(Optional.empty());
                if(optional.isPresent()){
                    HamonData data = optional.get();
                    boolean skill = data.isSkillLearned(ModHamonSkills.PLANT_BLOCK_INFUSION.get());
                    if(skill && !level.isClientSide){
                        float hamonEfficiency = data.getActionEfficiency(1000, true, ModHamonSkills.PLANT_BLOCK_INFUSION.get());
                        int chargeTicks = 100 + MathHelper.floor((float) (1100 * data.getHamonStrengthLevel())
                                / (float) HamonData.MAX_STAT_LEVEL * hamonEfficiency * hamonEfficiency);
                        addBlockCharge(user.level, blockPos, user, data, hamonEfficiency, chargeTicks);
                        if (data.isSkillLearned(ModHamonSkills.HAMON_SPREAD.get())) {
                            addBlockCharge(user.level, blockPos.offset(-1,  0,  0), user, data, hamonEfficiency, chargeTicks);
                            addBlockCharge(user.level, blockPos.offset( 1,  0,  0), user, data, hamonEfficiency, chargeTicks);
                            addBlockCharge(user.level, blockPos.offset( 0, -1,  0), user, data, hamonEfficiency, chargeTicks);
                            addBlockCharge(user.level, blockPos.offset( 0,  1,  0), user, data, hamonEfficiency, chargeTicks);
                            addBlockCharge(user.level, blockPos.offset( 0,  0, -1), user, data, hamonEfficiency, chargeTicks);
                            addBlockCharge(user.level, blockPos.offset( 0,  0,  1), user, data, hamonEfficiency, chargeTicks);
                        }

                    }
                }


            }

        }
        super.onHitBlock(blockRayTraceResult);
    }

    @Override
    protected Vector3d getOwnerRelativeOffset() {
        return right ? RIGHT : LEFT;
    }

    @Override
    public int ticksLifespan() {
        return 30;
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

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt){
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsRight", right);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        right = nbt.getBoolean("IsRight");
    }


    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        buffer.writeBoolean(right);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        right = additionalData.readBoolean();
    }

    private void addBlockCharge(World world, BlockPos blockPos, LivingEntity user, HamonData hamon, float hamonEfficiency, int chargeTicks) {
        world.getEntitiesOfClass(HamonBlockChargeEntity.class,
                new AxisAlignedBB(Vector3d.atCenterOf(blockPos), Vector3d.atCenterOf(blockPos))).forEach(Entity::remove);
        HamonBlockChargeEntity charge = new HamonBlockChargeEntity(world, blockPos);
        charge.setCharge(hamon.getHamonDamageMultiplier() * hamonEfficiency, chargeTicks, user, 1000);
        world.addFreshEntity(charge);
    }
}
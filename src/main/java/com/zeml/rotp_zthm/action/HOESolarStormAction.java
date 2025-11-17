package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.init.InitParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HOESolarStormAction extends StandEntityAction {
    public static final StandPose BLOW =new StandPose("BLOW");

    public HOESolarStormAction(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(userPower.getUser() != null){
            boolean reflect = INonStandPower.getNonStandPowerOptional(userPower.getUser()).map(ipower->ipower.getTypeSpecificData(ModPowers.HAMON.get()).map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.PROJECTILE_SHIELD.get())).orElse(false)).orElse(false);
            List<ProjectileEntity> projectileEntities = MCUtil.entitiesAround(ProjectileEntity.class,standEntity,5,false,projectile -> standEntity.canSee(projectile) && areVectorsColliding(standEntity,projectile) && standEntity.isAlive());
            if(!projectileEntities.isEmpty() && reflect){
                projectileEntities.forEach(projectile -> {
                    ExtraHamonStandsAddon.LOGGER.debug("WTF {} {}",reflect, projectile.getType());
                    projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
                    if(projectile instanceof ModdedProjectileEntity &&  ((ModdedProjectileEntity) projectile).canBeDeflected(projectile)){
                        ((ModdedProjectileEntity) projectile).setIsDeflected(projectile.getDeltaMovement().reverse(),projectile.position());
                    }
                });
            }
        }
        if(!world.isClientSide){
            Vector3d lookAngle = standEntity.getLookAngle();
            RayTraceResult[] rayTraceResults = JojoModUtil.rayTraceMultipleEntities(standEntity,standEntity.getMaxRange(),
                    entity -> entity.isAlive() && standEntity.canSee(entity) && !standEntity.isAlliedTo(entity) ||
                            (entity instanceof ProjectileEntity && standEntity.canSee(entity))
                    , this.isShiftVariation()? 3:10,standEntity.getPrecision());
            if(rayTraceResults.length > 0){
                Arrays.stream(rayTraceResults).forEach(rayTraceResult ->{
                    if(rayTraceResult.getType() == RayTraceResult.Type.ENTITY){
                        Entity entity = ((EntityRayTraceResult) rayTraceResult).getEntity();
                        if(entity.canUpdate()){
                            if (entity.canUpdate()) {
                                double distance = entity.distanceTo(standEntity);
                                Vector3d pushVec = lookAngle.normalize().scale(0.25 * standEntity.getStandEfficiency());
                                entity.setDeltaMovement(distance > standEntity.getMaxRange()/2 ?
                                        entity.getDeltaMovement().add(pushVec.scale(1/distance*2))
                                        : pushVec.scale(Math.max(distance - 1, 0)));
                            }
                            if(entity instanceof LivingEntity){
                                DamageUtil.dealHamonDamage(entity,this.isShiftVariation()?.4F:.25F,userPower.getUser(),null);
                            }
                        }
                    }
                });
            }
        }else {
            Vector3d wrLookVec = standEntity.getLookAngle();
            GeneralUtil.doFractionTimes(() -> {
                LivingEntity user = userPower.getUser();
                Vector3d userPos = standEntity.position().add(
                        (Math.random() - 0.5) * (user.getBbWidth() + 1.0),
                        Math.random() * (user.getBbHeight() + 1.0),
                        (Math.random() - 0.5) * (user.getBbWidth() + 1.0));
                Vector3d particlePos = userPos.add(wrLookVec.scale(2)
                        .xRot((float) ((Math.random() * 2 - 1) * Math.PI / 6))
                        .yRot((float) ((Math.random() * 2 - 1) * Math.PI / 3)));
                Vector3d vecToStand = userPos.subtract(particlePos).normalize().scale(-1);
                world.addParticle(InitParticles.SOLAR.get(), particlePos.x, particlePos.y, particlePos.z, vecToStand.x,  vecToStand.y, vecToStand.z);
            }, 5);

        }
    }


    private boolean areVectorsColliding(LivingEntity user, Entity target) {
        Vector3d sourcePos = user.position();
        Vector3d targetView = target.getDeltaMovement();
        Vector3d vectorTo = sourcePos.vectorTo(target.position()).normalize();
        vectorTo = new Vector3d(vectorTo.x,0,vectorTo.z);
        return vectorTo.dot(targetView) < 0;
    }
}
package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zthm.entity.projectile.BubblesEntity;
import com.zeml.rotp_zthm.entity.projectile.PickUpBubbleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RedirectBubbleTargetAction extends StandEntityAction {

    public RedirectBubbleTargetAction(StandEntityAction.Builder builder){
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide) {
            LivingEntity user = userPower.getUser();
            if(task.getTarget().getEntity() != null){
                Entity entity = task.getTarget().getEntity();
                List<ProjectileEntity> projectiles = MCUtil.entitiesAround(ProjectileEntity.class, user, standEntity.getMaxRange(), false, projectileEntity -> projectileEntity.getType().getRegistryName().toString().contains("bubble") && projectileEntity.getOwner() == user);
                if (!projectiles.isEmpty()){
                    projectiles.forEach(projectileEntity -> {

                        if(projectileEntity instanceof BubblesEntity && !(projectileEntity instanceof PickUpBubbleEntity) && entity instanceof LivingEntity){
                            BubblesEntity bubblesEntity = (BubblesEntity) projectileEntity;
                            LivingEntity target = (LivingEntity)entity;
                            bubblesEntity.setTarget(target);
                            bubblesEntity.setHoming(true);

                        }else {
                            Vector3d direction = projectileEntity.position().vectorTo(entity.position()).normalize();
                            double f = projectileEntity.getDeltaMovement().length();
                            if(projectileEntity.getDeltaMovement().length() == 0){
                                f = .5F;
                            }
                            projectileEntity.setDeltaMovement(direction.multiply(f,f,f));
                            userPower.consumeStamina(projectileEntity.getBbHeight()*25);
                        }

                    });
                }
            }
        }

    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }




    @Override
    public double getMaxRangeSqEntityTarget() {
        return 625;
    }
}

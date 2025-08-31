package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RedirectBubblesAction extends StandEntityAction {

    public RedirectBubblesAction(StandEntityAction.Builder builder){
        super(builder);
    }


    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(target.getEntity() != null && !isOnOurSide(target.getEntity(), power.getUser()) && !(target.getEntity() instanceof ProjectileEntity)
                && !this.isShiftVariation()
        ){
            return InitStands.REDIRECT_TARGET.get();
        }
        return super.replaceAction(power, target);
    }




    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            LivingEntity user = userPower.getUser();
            List<ProjectileEntity> projectiles = MCUtil.entitiesAround(ProjectileEntity.class, user,standEntity.getMaxRange(),false, projectileEntity -> projectileEntity.getType().getRegistryName().toString().contains("bubble") && projectileEntity.getOwner() == user);
            if(!projectiles.isEmpty()){
                projectiles.forEach(projectileEntity -> {
                    double f = projectileEntity.getDeltaMovement().length();
                    if(projectileEntity.getDeltaMovement().length() == 0){
                        f = .5F;
                    }
                    float xRot = user.getViewXRot(1);
                    float yRot = user.getViewYRot(1);
                    double dX = MathHelper.cos(-xRot*(float) Math.PI/180 ) *(MathHelper.sin(-yRot * ((float)Math.PI / 180F)) * f);
                    double dY = f* MathHelper.sin(-xRot*(float) Math.PI/180 );
                    double dZ = MathHelper.cos(-xRot*(float) Math.PI/180 ) *(MathHelper.cos(-yRot * ((float)Math.PI / 180F)) * f);
                    if(!this.isShiftVariation()){
                        projectileEntity.setDeltaMovement(dX,dY,dZ);
                        userPower.consumeStamina(projectileEntity.getBbHeight()*25);
                    }else {
                        projectileEntity.setDeltaMovement(Vector3d.ZERO);
                        userPower.consumeStamina(projectileEntity.getBbHeight()*15);
                    }


                });
            }
        }
    }


    private boolean isOnOurSide(Entity entity, LivingEntity user){
        if(entity instanceof ProjectileEntity){
            return ((ProjectileEntity) entity).getOwner() == user;
        }
        return entity.isAlliedTo(user);
    }

    @Override
    public double getMaxRangeSqEntityTarget() {
        return 625;
    }


}

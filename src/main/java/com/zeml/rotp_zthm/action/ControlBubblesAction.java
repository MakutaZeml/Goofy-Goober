package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class ControlBubblesAction extends StandEntityAction {

    public ControlBubblesAction(StandEntityAction.Builder builder){
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            List<ProjectileEntity> projectiles = MCUtil.entitiesAround(ProjectileEntity.class, userPower.getUser(),standEntity.getMaxRange(),false, projectileEntity -> projectileEntity.getType().getRegistryName().toString().contains("bubble") && (projectileEntity.getOwner() != userPower.getUser()));
            if(!projectiles.isEmpty()){
                projectiles.forEach(projectileEntity -> {
                    ProjectileEntity projectile = (ProjectileEntity) projectileEntity.getType().create(world);
                    Vector3d vector3d = projectileEntity.getDeltaMovement();
                    Vector3d pos = projectileEntity.position();
                    projectileEntity.remove();
                    if(projectile != null){
                        projectile.setOwner(userPower.getUser());
                        projectile.moveTo(pos);
                        projectile.setDeltaMovement(vector3d);
                        world.addFreshEntity(projectile);
                    }
                });
            }
        }
    }
}

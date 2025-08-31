package com.zeml.rotp_zthm.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ExplodeBubblesAction extends StandEntityAction {

    public ExplodeBubblesAction(StandEntityAction.Builder builder){
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            LivingEntity user = userPower.getUser();
            List<ProjectileEntity> projectiles = MCUtil.entitiesAround(ProjectileEntity.class, user,standEntity.getMaxRange(),false, projectileEntity -> projectileEntity.getType().getRegistryName().toString().contains("bubble") && projectileEntity.getOwner() == user);
            if(!projectiles.isEmpty()){
                projectiles.forEach(projectile -> {
                    if(projectile.getType().getRegistryName().toString().contains("hamon")){
                        List<LivingEntity> targets = MCUtil.entitiesAround(LivingEntity.class, projectile,1,false,entity->entity.isAlive() && !entity.isAlliedTo(user));
                        if(!targets.isEmpty()){
                            targets.forEach(target -> DamageUtil.dealHamonDamage(target, 0.5F*projectile.getBbWidth(), projectile, user));
                        }
                    }
                    projectile.remove();
                });
            }
        }
    }
}

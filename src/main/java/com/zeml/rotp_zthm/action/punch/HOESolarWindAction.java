package com.zeml.rotp_zthm.action.punch;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zthm.entity.projectile.SolarFlareEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class HOESolarWindAction extends StandEntityMeleeBarrage {
    public HOESolarWindAction(StandEntityMeleeBarrage.Builder builder) {
        super(builder);
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        int hitsThisTick = 0;
        int hitsPerSecond = StandStatFormulas.getBarrageHitsPerSecond(standEntity.getAttackSpeed());
        int extraTickSwings = hitsPerSecond / 20;
        for (int i = 0; i < extraTickSwings; i++) {
            hitsThisTick++;
        }
        hitsPerSecond -= extraTickSwings * 20;

        if (standEntity.barrageHandler.popDelayedHit()) {
            hitsThisTick++;
        }
        else if (hitsPerSecond > 0) {
            double ticksInterval = 20D / hitsPerSecond;
            int intTicksInterval = (int) ticksInterval;
            if ((getStandActionTicks(userPower, standEntity) - task.getTick() + standEntity.barrageHandler.getHitsDelayed()) % intTicksInterval == 0) {
                if (!world.isClientSide()) {
                    double delayProb = ticksInterval - intTicksInterval;
                    if (standEntity.getRandom().nextDouble() < delayProb) {
                        standEntity.barrageHandler.delayHit();
                    }
                    else {
                        hitsThisTick++;
                    }
                }
            }
        }
        int barrageHits = hitsThisTick;
        standEntity.setBarrageHitsThisTick(barrageHits);
        if (barrageHits > 0) {
            standEntity.punch(task, this, task.getTarget());
            if(!world.isClientSide && userPower.getUser() != null){
                SolarFlareEntity solarFlare = new SolarFlareEntity(standEntity,world, Math.random() <.5);
                solarFlare.shootFromRotation(standEntity,1,.7F);
                solarFlare.setOwner(userPower.getUser());
                world.addFreshEntity(solarFlare);
            }
            if (world.isClientSide()) {
                clTtickSwingSound(task.getTick(), standEntity);
            }
        }

    }

    @Override
    public BarrageEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource){
        BarrageEntityPunch punch = new BarrageEntityPunch(stand,target,dmgSource);
        punch.impactSound(hitSound);
        punch.damage(StandStatFormulas.getBarrageHitDamage(stand.getAttackDamage()/5, stand.getPrecision()));
        return punch;
    }



}

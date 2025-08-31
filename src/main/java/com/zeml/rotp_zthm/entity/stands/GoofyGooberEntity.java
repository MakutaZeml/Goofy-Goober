package com.zeml.rotp_zthm.entity.stands;


import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.zeml.rotp_zthm.item.StandGlovesItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class GoofyGooberEntity extends StandEntity {

    public GoofyGooberEntity(StandEntityType<GoofyGooberEntity> type, World world){
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }
    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 0, 0);




    @Override
    public boolean isPickable(){ return false;}

	public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}

    @Override
    public float getLeapStrength() {
        AtomicReference<Float> strength = new AtomicReference<>((float) 0);
        if(this.getUser() != null){
            INonStandPower.getNonStandPowerOptional(this.getUser()).ifPresent(ipower->{
                Optional<HamonData> hamonOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
                if(hamonOp.isPresent()){
                    HamonData hamon = hamonOp.get();
                    strength.set(hamon.isSkillLearned(ModHamonSkills.AFTERIMAGES.get()) ? 2F : 1.25F);
                    ModifiableAttributeInstance speedAttribute = this.getUser().getAttribute(Attributes.MOVEMENT_SPEED);
                    if (speedAttribute != null) {
                        strength.updateAndGet(v -> new Float((v * (float) (speedAttribute.getValue() / speedAttribute.getBaseValue()))));
                    }
                }
            });
        }
        return strength.get();
    }


}

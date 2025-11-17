package com.zeml.rotp_zthm.power.impl.stand.type;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.client.controls.ControlScheme;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.init.InitStands;
import com.zeml.rotp_zthm.network.AddonPackets;
import com.zeml.rotp_zthm.network.server.CanLeapPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HamonStandType<T extends StandStats> extends EntityStandType<T> {
    private boolean leapUnlocked = true;

    @Deprecated
    public HamonStandType(int color, ITextComponent partName, StandAction[] attacks, StandAction[] abilities, Class<T> statsClass, T defaultStats, @Nullable StandType.StandTypeOptionals additions) {
        super(color, partName, attacks, abilities, statsClass, defaultStats, additions);
    }

    protected HamonStandType(EntityStandType.AbstractBuilder<?, T> builder) {
        super(builder);
    }


    @Override
    public void tickUser(LivingEntity user, IStandPower power) {
        super.tickUser(user, power);
        if(!user.level.isClientSide){
            INonStandPower.getNonStandPowerOptional(user).ifPresent(ipower ->{
                Optional<HamonData> hamonOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
                if(hamonOp.isPresent()) {
                    HamonData hamon = hamonOp.get();

                    //------------------GOOFY GOOBER--------------------
                    if(power.getType() == InitStands.GOOFY_GOOBER.getStandType()){
                        this.setLeapUnlocked(hamon.isSkillLearned(ModHamonSkills.JUMP.get()));
                        if(user instanceof ServerPlayerEntity){
                            AddonPackets.sendToClient(new CanLeapPacket(user.getId(), hamon.isSkillLearned(ModHamonSkills.JUMP.get())),(ServerPlayerEntity) user);
                        }
                        if(power.getResolveLevel() > 1){
                            power.unlockAction(InitStands.SHIELD_BUBBLE_TARGET.get());
                        }
                        if(power.getResolveLevel()>2){
                            power.unlockAction(InitStands.REDIRECT_TARGET.get());
                        }
                        if(hamon.isSkillLearned(ModHamonSkills.BUBBLE_CUTTER.get())){
                            power.unlockAction(InitStands.BUBBLE_CUTTER.get());
                            power.unlockAction(InitStands.BUBBLE_CUTTER_GLIDING.get());
                        }
                        if(hamon.isSkillLearned(ModHamonSkills.BUBBLE_LAUNCHER.get())) power.unlockAction(InitStands.BUBBLE_LAUNCHER.get());
                    }
                    //------------------HOUSE OF EARTH--------------------


                }
            });
        }
    }

    @Override
    public boolean canLeap() {
        return leapUnlocked;
    }

    public void setLeapUnlocked(boolean leapUnlocked) {
        this.leapUnlocked = leapUnlocked;
    }

    @Override
    public boolean isActionLegalInHud(Action<IStandPower> action, IStandPower power) {
        ExtraHamonStandsAddon.LOGGER.debug("Stand Action {}, {}", action.getRegistryName(), super.isActionLegalInHud(action, power));
        if(super.isActionLegalInHud(action, power)){
            return true;
        }

        return INonStandPower.getNonStandPowerOptional(power.getUser()).map(iNonStandPower -> {
            Optional<HamonData> hamonDataOptional =  iNonStandPower.getTypeSpecificData(ModPowers.HAMON.get());
            if(hamonDataOptional.isPresent()) {
                HamonData hamonData = hamonDataOptional.get();
                //------------------GOOFY GOOBER--------------------
                if(power.getType() == InitStands.GOOFY_GOOBER.getStandType()){
                    if(action == InitStands.BUBBLE_LAUNCHER.get()){
                        return hamonData.isSkillLearned(ModHamonSkills.BUBBLE_LAUNCHER.get());
                    }
                    if(action == InitStands.BUBBLE_CUTTER.get() || action == InitStands.BUBBLE_CUTTER_GLIDING.get()){
                        return hamonData.isSkillLearned(ModHamonSkills.BUBBLE_CUTTER.get());
                    }
                }


            }
            return false;
        }).orElse(false);

    }

    @Override
    public void clAddMissingActions(ControlScheme controlScheme, IStandPower power) {
        super.clAddMissingActions(controlScheme, power);
        INonStandPower.getNonStandPowerOptional(power.getUser()).ifPresent(iNonStandPower -> {
            Optional<HamonData> hamonDataOptional =  iNonStandPower.getTypeSpecificData(ModPowers.HAMON.get());
            if(hamonDataOptional.isPresent()){
                HamonData hamonData = hamonDataOptional.get();
                if(power.getType() == InitStands.GOOFY_GOOBER.getStandType()){
                    if(hamonData.isSkillLearned(ModHamonSkills.BUBBLE_LAUNCHER.get())){
                        controlScheme.addIfMissing(ControlScheme.Hotbar.LEFT_CLICK, InitStands.BUBBLE_LAUNCHER.get());
                    }
                    if(hamonData.isSkillLearned(ModHamonSkills.BUBBLE_CUTTER.get())){
                        controlScheme.addIfMissing(ControlScheme.Hotbar.LEFT_CLICK, InitStands.BUBBLE_CUTTER.get());
                    }
                }
            }

        });


    }

    public static class Builder<T extends StandStats> extends EntityStandType.AbstractBuilder<Builder<T>, T> {

        @Override
        protected Builder<T> getThis() {
            return this;
        }

        @Override
        public HamonStandType<T> build() {
            return new HamonStandType<>(this);
        }

    }
}

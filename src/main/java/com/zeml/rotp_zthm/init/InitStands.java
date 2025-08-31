package com.zeml.rotp_zthm.init;

import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModSounds;
import com.zeml.rotp_zthm.action.HamonBreath;
import com.zeml.rotp_zthm.action.ControlBubblesAction;
import com.zeml.rotp_zthm.action.ExplodeBubblesAction;
import com.zeml.rotp_zthm.action.RedirectBubbleTargetAction;
import com.zeml.rotp_zthm.action.RedirectBubblesAction;
import com.zeml.rotp_zthm.action.projectiles.*;
import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import com.zeml.rotp_zthm.power.impl.stand.type.HamonStandType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {

    public static final ITextComponent SPECIAL_HAMON = new TranslationTextComponent("zeml.story.hamon").withStyle(TextFormatting.YELLOW);

    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), ExtraHamonStandsAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), ExtraHamonStandsAddon.MOD_ID);

    public static final RegistryObject<StandAction> STAND_BREATH =ACTIONS.register("stand_breath",
            ()-> new HamonBreath(new StandEntityAction.Builder().heldWalkSpeed(0.0F).holdType()
                    .standSound(ModSounds.BREATH_CAESAR).standSound(ModSounds.HAMON_CONCENTRATION)
            ));

    // ======================================== Goofy Goober ========================================

    public static final RegistryObject<StandEntityAction> BUBBLE_LAUNCHER = ACTIONS.register("bubble_launcher",
            ()-> new BubbleLauncher(new StandEntityAction.Builder().heldWalkSpeed(0.3F)
                    .shout(ModSounds.CAESAR_BUBBLE_LAUNCHER).holdType()
            )
    );


    public static final RegistryObject<StandEntityAction> BUBBLE_CUTTER = ACTIONS.register("bubble_cutter",
            ()-> new HamonBubbleCutter(new StandEntityAction.Builder().cooldown(10)
                    .shout(ModSounds.CAESAR_BUBBLE_CUTTER)
            )
    );

    public static final RegistryObject<StandEntityAction> BUBBLE_CUTTER_GLIDING = ACTIONS.register("bubble_cutter_gliding",
            ()-> new HamonBubbleCutter(new StandEntityAction.Builder().cooldown(10)
                    .shout(ModSounds.CAESAR_BUBBLE_CUTTER_GLIDING).shiftVariationOf(BUBBLE_CUTTER)
            )
    );

    public static final RegistryObject<StandEntityAction> REDIRECT = ACTIONS.register("redirect_bubble",
            ()-> new RedirectBubblesAction(new StandEntityAction.Builder().cooldown(60,0,5F))
            );


    public static final RegistryObject<StandEntityAction> REDIRECT_TARGET = ACTIONS.register("redirect_target_bubble",
            ()-> new RedirectBubbleTargetAction(new StandEntityAction.Builder().cooldown(60,0,5F)
            )
    );

    public static final RegistryObject<StandEntityAction> DELETE_BUBBLE = ACTIONS.register("pop_bubble",
            ()-> new ExplodeBubblesAction(new StandEntityAction.Builder().cooldown(10).resolveLevelToUnlock(1))
    );

    public static final RegistryObject<StandEntityAction> LOCK_BUBBLE = ACTIONS.register("lock_bubble",
            ()-> new RedirectBubblesAction(new StandEntityAction.Builder().cooldown(60).shiftVariationOf(REDIRECT)
                    .shiftVariationOf(REDIRECT_TARGET).resolveLevelToUnlock(2)
            )
    );

    public static final RegistryObject<StandEntityAction> RIDE_BUBBLE =ACTIONS.register("ride_bubble",
            ()-> new RideableBubbleAction(new StandEntityAction.Builder().heldWalkSpeed(0.0F).holdToFire(20, false)
                    .staminaCostTick(50F).heldWalkSpeed(0.3F).cooldown(100).staminaCost(100).resolveLevelToUnlock(4)
            ));

    public static final RegistryObject<StandEntityAction> CONTROL_BUBBLES = ACTIONS.register("control_bubble",
            ()-> new ControlBubblesAction(new StandEntityAction.Builder().cooldown(5))
    );

    public static final RegistryObject<StandAction> PICK_BUBBLES = ACTIONS.register("pick_items_bubble",
            ()-> new LaunchPickBubbleAction(new StandAction.Builder().heldWalkSpeed(0.3F).holdType(30)
                    .cooldown(40)
            ));

    public static final RegistryObject<StandEntityAction> SHIELD_BUBBLE = ACTIONS.register("shield",
            ()-> new ShieldBubbleAction(new StandEntityAction.Builder().holdType().heldWalkSpeed(0).standPose(StandPose.BLOCK))
            );

    public static final RegistryObject<StandEntityAction> SHIELD_BUBBLE_TARGET = ACTIONS.register("shield_target",
            ()-> new TargetShieldAction(new StandEntityAction.Builder().heldWalkSpeed(0).holdToFire(30,false)
                    .cooldown(100)
            )
    );

    public static final EntityStandRegistryObject<HamonStandType<StandStats>, StandEntityType<GoofyGooberEntity>> GOOFY_GOOBER =
            new EntityStandRegistryObject<>("goofy_goober",
                    STANDS,
                    () -> new HamonStandType.Builder<StandStats>()
                            .color(0x61AFFF)
                            .storyPartName(SPECIAL_HAMON)
                            .leftClickHotbar(
                                    REDIRECT.get(),
                                    DELETE_BUBBLE.get()
                            )
                            .rightClickHotbar(
                                    STAND_BREATH.get(),
                                    SHIELD_BUBBLE.get(),
                                    CONTROL_BUBBLES.get(),
                                    PICK_BUBBLES.get(),
                                    RIDE_BUBBLE.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(2)
                                    .power(6.0)
                                    .speed(5.0)
                                    .range(25.0)
                                    .durability(12.0)
                                    .precision(7.0)
                                    .randomWeight(2)
                            )
                            .addOst(InitSounds.GOOFY_GOOBER_OST)
                            .disableManualControl().disableStandLeap()
                            .addSummonShout(InitSounds.GOOFY_GOOBER_STAND)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<GoofyGooberEntity>(GoofyGooberEntity::new, 0.0F, 0.0F)
                            .summonSound(InitSounds.GOOFY_GOOBER_SUMMON)
                            .unsummonSound(InitSounds.GOOFY_GOOBER_UNSUMMON))
                    .withDefaultStandAttributes();
}

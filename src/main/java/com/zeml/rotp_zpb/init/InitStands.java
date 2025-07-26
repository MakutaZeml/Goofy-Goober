package com.zeml.rotp_zpb.init;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.your_name.rotp_item_stand.action.*;
import com.zeml.rotp_zpb.entity.stand.stands.ItemStandEntity;
import com.zeml.rotp_zpb.ItemStandAddon;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import com.zeml.rotp_zpb.action.GiveItemStand;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), ItemStandAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), ItemStandAddon.MOD_ID);

    // ======================================== Cream Starter ========================================

    /*It's necessary to have at least one ability, otherwise the game will crash when you open stand stats*/

    public static final RegistryObject<StandEntityAction> GIVE_ITEM = ACTIONS.register("give_item",
            ()->new GiveItemStand(new StandEntityAction.Builder().staminaCost(1)));


    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<ItemStandEntity>> STAND_ITEM =
            new EntityStandRegistryObject<>("item_stand",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                            .color(0xdbb0f7)
                            .storyPartName(ModStandsInit.PART_9_NAME)
                            .leftClickHotbar(

                            )
                            .rightClickHotbar(
                                    GIVE_ITEM.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(2)
                                    .power(6.0)
                                    .speed(10.0)
                                    .range(9.0)
                                    .durability(16.0)
                                    .precision(2.0)
                                    .randomWeight(2)
                            )
                            .addOst(InitSounds.ITEM_STAND_OST)
                            .disableManualControl().disableStandLeap()
                            .addSummonShout(InitSounds.USER_ITEM_STAND)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<ItemStandEntity>(ItemStandEntity::new, 0.1F, 0.1F)
                            .summonSound(InitSounds.ITEM_STAND_SUMMON)
                            .unsummonSound(InitSounds.ITEM_STAND_UNSUMMON))
                    .withDefaultStandAttributes();
}

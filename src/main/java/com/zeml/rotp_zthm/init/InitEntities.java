package com.zeml.rotp_zthm.init;

import com.zeml.rotp_zthm.ExtraHamonStandsAddon;

import com.zeml.rotp_zthm.entity.projectile.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExtraHamonStandsAddon.MOD_ID);


    public static final RegistryObject<EntityType<RideableBubble>> RIDE_BUBBLE = ENTITIES.register("ride_bubble",
            () -> EntityType.Builder.<RideableBubble>of(RideableBubble::new, EntityClassification.MISC).sized(2.0F, 2.0F)
                    .build(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "ride_bubble").toString()));

    public static final RegistryObject<EntityType<PickUpBubbleEntity>> PICK_ITEM_BUBBLE = ENTITIES.register("pick_item_bubble",
            () -> EntityType.Builder.<PickUpBubbleEntity>of(PickUpBubbleEntity::new, EntityClassification.MISC).sized(0.15F, 0.15F)
                    .build(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "pick_item_bubble").toString()));

    public static final RegistryObject<EntityType<HamonStandBubbleEntity>> HAMON_BUBBLE = ENTITIES.register("hamon_bubble",
            () -> EntityType.Builder.<HamonStandBubbleEntity>of(HamonStandBubbleEntity::new, EntityClassification.MISC).sized(0.15F, 0.15F)
                    .build(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "hamon_bubble").toString()));

    public static final RegistryObject<EntityType<HamonStandBubbleCutterEntity>> HAMON_BUBBLE_CUTTER = ENTITIES.register("hamon_bubble_cutter",
            () -> EntityType.Builder.<HamonStandBubbleCutterEntity>of(HamonStandBubbleCutterEntity::new, EntityClassification.MISC).sized(0.325F, 0.0875F).setUpdateInterval(10)
                    .build(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "hamon_bubble_cutter").toString()));

    public static final RegistryObject<EntityType<ShieldBubble>> DEFENSE_BUBBLE = ENTITIES.register("shield_bubble",
            () -> EntityType.Builder.<ShieldBubble>of(ShieldBubble::new, EntityClassification.MISC).sized(2.0F, 2.0F)
                    .build(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "shield_bubble").toString()));


}

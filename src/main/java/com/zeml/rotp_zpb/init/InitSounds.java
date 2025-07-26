package com.zeml.rotp_zpb.init;

import com.github.standobyte.jojo.init.ModSounds;
import com.zeml.rotp_zpb.ItemStandAddon;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ItemStandAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> ITEM_STAND_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;

    public static final RegistryObject<SoundEvent> VOID =SOUNDS.register("void",
            ()->new SoundEvent(new ResourceLocation(ItemStandAddon.MOD_ID,"void")));

    public static final RegistryObject<SoundEvent> ITEM_STAND_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;


    public static final RegistryObject<SoundEvent> USER_ITEM_STAND = SOUNDS.register("user_item_stand",
            ()->new SoundEvent(new ResourceLocation(ItemStandAddon.MOD_ID, "user_item_stand"))
            );



    static final OstSoundList ITEM_STAND_OST = new OstSoundList(new ResourceLocation(ItemStandAddon.MOD_ID, "item_stand_ost"), SOUNDS);
}

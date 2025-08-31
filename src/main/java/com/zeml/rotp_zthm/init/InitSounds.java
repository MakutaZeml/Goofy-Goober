package com.zeml.rotp_zthm.init;

import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExtraHamonStandsAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> GOOFY_GOOBER_SUMMON = SOUNDS.register("summon",
            ()->new SoundEvent(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID,"summon")));

    public static final RegistryObject<SoundEvent> VOID =SOUNDS.register("void",
            ()->new SoundEvent(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID,"void")));

    public static final RegistryObject<SoundEvent> GOOFY_GOOBER_UNSUMMON = SOUNDS.register("unsummon",
            ()->new SoundEvent(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID,"unsummon")));


    public static final RegistryObject<SoundEvent> GOOFY_GOOBER_STAND = SOUNDS.register("user_item_stand",
            ()->new SoundEvent(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "user_item_stand"))
            );



    static final OstSoundList GOOFY_GOOBER_OST = new OstSoundList(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "goofy_goober_ost"), SOUNDS);
}

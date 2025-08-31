package com.zeml.rotp_zthm.init;

import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.item.StandGlovesItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraHamonStandsAddon.MOD_ID);


    public static final RegistryObject<StandGlovesItem> STAND_GLOVES = ITEMS.register("stand_gloves",
            ()->new StandGlovesItem(new Item.Properties().stacksTo(1)));

}

package com.zeml.rotp_zpb.init;

import com.zeml.rotp_zpb.ItemStandAddon;
import com.zeml.rotp_zpb.item.StandItemItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ItemStandAddon.MOD_ID);


    public static final RegistryObject<StandItemItem> STAND_ITEM = ITEMS.register("stand_item",
            ()->new StandItemItem(new Item.Properties().stacksTo(1)));

}

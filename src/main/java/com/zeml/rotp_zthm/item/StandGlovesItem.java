package com.zeml.rotp_zthm.item;

import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.item.BubbleGlovesItem;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.zeml.rotp_zthm.action.projectiles.BubbleLauncher;
import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;


public class StandGlovesItem extends BubbleGlovesItem {

    public StandGlovesItem(Properties properties) {
        super(properties);
    }



    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if(entity instanceof LivingEntity){
            LivingEntity user = (LivingEntity) entity;
            IStandPower.getStandPowerOptional(user).ifPresent(power -> {
                if(power.getStandManifestation() instanceof GoofyGooberEntity){
                    standReload(itemStack,world,power, entity);
                }
            });
            if(!BubbleLauncher.getSoapItem(user).isEmpty()){
                if(!world.isClientSide){
                    user.addEffect(new EffectInstance(ModStatusEffects.INTEGRATED_STAND.get(),10,0,false,false,false));
                }
            }
        }

    }

    public static boolean standReload(ItemStack glovesItem, World world, IStandPower power, Entity entity){
        int ammoToLoad = MAX_AMMO - TommyGunItem.getAmmo(glovesItem);
        if (ammoToLoad > 0 && power.getStamina()>0){
            if(!world.isClientSide && glovesItem.getTag().getString("owner").equals(entity.getName().getString())){
                power.consumeStamina(2);
                glovesItem.getOrCreateTag().putInt("Ammo", glovesItem.getOrCreateTag().getInt("Ammo")+2);
            }
        }
        return false;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putInt("Ammo", MAX_AMMO);
            items.add(stack);
        }
    }




}

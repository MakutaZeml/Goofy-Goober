package com.zeml.rotp_zpb.item;

import com.zeml.rotp_zpb.init.IntTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class StandItemItem extends Item {
    /*You can extend to other Item types depending what typo of Item Stand are you doing*/
    public StandItemItem(Properties properties) {
        super(properties);
    }


    @Override
    public void onUseTick(World world, LivingEntity entity, ItemStack stack, int remainingTicks){
        boolean shot = stack.getTag().getString("mode").equals("attack");
        if(!world.isClientSide){
            /*If it's a action item like Cream Starter or emperor, the code comes here*/

        }
    }




    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return ActionResult.consume(handStack);

    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int remainingTicks){
        if(stack.hasTag() || stack.getTag().contains("ticks")){
            stack.getTag().putInt("ticks",0);
        }
        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;
            player.getCooldowns().addCooldown(this,20);
        }
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    public static final int MAX_AMMO = 600;


    private static int getAmmo(ItemStack gun) {
        return gun.getOrCreateTag().getInt("Ammo");
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity user, LivingEntity target) {
        /*this is so clear, its when you punch the enemy with the item in the main hand*/
        if(!IntTags.NO_MEATABLE.contains(target.getType()) && stack.hasTag() && stack.getTag().getInt("Ammmo")<MAX_AMMO){
            int fill = stack.getTag().getInt("Ammo")+ 2*Math.round(target.getHealth());
            stack.getTag().putInt("Ammo",fill);
        }
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getAmmo(stack) < MAX_AMMO;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - ((double) getAmmo(stack) / (double) MAX_AMMO);
    }


    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }
}

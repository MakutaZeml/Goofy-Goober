package com.zeml.rotp_zthm.util;

import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.OwnerBoundProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import com.zeml.rotp_zthm.ExtraHamonConfig;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.action.projectiles.BubbleLauncher;
import com.zeml.rotp_zthm.capabilities.LivingData;
import com.zeml.rotp_zthm.capabilities.LivingDataProvider;
import com.zeml.rotp_zthm.entity.projectile.ShieldBubble;
import com.zeml.rotp_zthm.init.InitItems;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;


@Mod.EventBusSubscriber(modid = ExtraHamonStandsAddon.MOD_ID)
public class GameplayHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void giveHamonStand(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(!player.level.isClientSide) {
            IStandPower.getStandPowerOptional(player).ifPresent(
                    power -> {
                        if(power.getType() == null && ExtraHamonConfig.getCommonConfigInstance(false).hamonToStand.get()){
                            INonStandPower.getNonStandPowerOptional(player).ifPresent(ipower->{
                                Optional<HamonData> hamonOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
                                if(hamonOp.isPresent()){
                                    HamonData hamon = hamonOp.get();
                                    if(hamon.getHamonStrengthLevel()>=ExtraHamonConfig.getCommonConfigInstance(false).strength.get()&&
                                            hamon.getBreathingLevel() >= ExtraHamonConfig.getCommonConfigInstance(false).breathing.get() &&
                                            hamon.getHamonControlLevel() >= ExtraHamonConfig.getCommonConfigInstance(false).control.get()){
                                        LazyOptional<LivingData> playerDataOptional = player.getCapability(LivingDataProvider.CAPABILITY);
                                        playerDataOptional.ifPresent(playerData ->{
                                            if(!playerData.isTriedHamonStand()){
                                                if(hamon.characterIs(ModHamonSkills.CHARACTER_CAESAR.get())){
                                                    power.givePower(InitStands.GOOFY_GOOBER.getStandType());
                                                    playerData.setTriedHamonStand(true);
                                                }else if(ModList.get().isLoaded("rotp_zhp")){
                                                    power.givePower(com.zeml.rotp_zhp.init.InitStands.STAND_HERMITO_PURPLE.getStandType());
                                                    playerData.setTriedHamonStand(true);
                                                }

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
            );
        }
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(!player.level.isClientSide()){
            IStandPower.getStandPowerOptional(player).ifPresent(standPower -> {
                if(standPower.getType() ==  InitStands.GOOFY_GOOBER.getStandType()){
                    if(standPower.getStandManifestation() instanceof StandEntity) {
                        //This checks if any player has the item stand or if it's in the world as an Item Entity
                        if(!playerHasItem(player) && !checkIfItemEntityIsTheStandObject(player)){
                            ItemStack itemStack = new ItemStack(InitItems.STAND_GLOVES.get(),1);
                            /*NBTags to regulate item functions, this is from Cream Starter*/
                            CompoundNBT nbt = new CompoundNBT();
                            itemStack.setTag(nbt);
                            NBTUtil.writeGameProfile(nbt,player.getGameProfile());
                            nbt.putString("owner",player.getName().getString());
                            nbt.putUUID("UUID",player.getUUID());
                            player.addItem(itemStack);

                        }else {
                            if(playerHasItem(player)){
                                deleteItemStandEntityDropped(player);
                            }
                            deleteDuplicatedItem(player);
                        }


                    }else {
                        deleteItemStandItem(player);
                        deleteItemStandEntityDropped(player);
                    }
                }else{
                    deleteItemStandItem(player);
                    deleteItemStandEntityDropped(player);
                }
            });
        }
    }




    private static void deleteItemStandItem(PlayerEntity players){
        if(players instanceof ServerPlayerEntity){
            ServerPlayerEntity servPlater =  (ServerPlayerEntity) players;
            servPlater.getLevel().players().forEach(player -> {
                for (int i=0; i<player.inventory.getContainerSize();++i){
                    if(itemStandOwner(players,player.inventory.getItem(i))){
                        player.inventory.getItem(i).shrink(1);
                    }
                }
            });
        }else {
            for (int i=0; i<players.inventory.getContainerSize();++i){
                if(itemStandOwner(players,players.inventory.getItem(i))){
                    players.inventory.getItem(i).shrink(1);
                }
            }
        }
    }


    private static void deleteItemStandEntityDropped(PlayerEntity player){
        if(player instanceof ServerPlayerEntity){
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            serverPlayer.getLevel().getEntities().filter(entity -> entity instanceof ItemEntity && ((ItemEntity)entity).getItem().getItem() == InitItems.STAND_GLOVES.get())
                    .forEach(entity -> {
                        ItemStack itemStack = ((ItemEntity) entity).getItem();
                        if(itemStandOwner(player,itemStack)){
                            entity.remove();
                        }
                    });
        }
    }


    private static void deleteDuplicatedItem(PlayerEntity players){
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) players;
        int count = 0;
        for (ServerPlayerEntity player : serverPlayer.getLevel().players()) {
            for (int i=0; i<player.inventory.getContainerSize();++i){
                ItemStack itemStack = player.inventory.getItem(i);
                if(itemStandOwner(players,itemStack)){
                    count++;
                    if(count>1){
                        itemStack.shrink(1);
                    }
                }
            }
        }
    }



    private static boolean playerHasItem(PlayerEntity players){
        if(players instanceof ServerPlayerEntity){
            boolean turn = false;
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) players;
            for (ServerPlayerEntity player : serverPlayer.getLevel().players()) {
                for(int i=0;i<player.inventory.getContainerSize();++i){
                    if(itemStandOwner(players, player.inventory.getItem(i))){
                        turn = true;
                        i= player.inventory.getContainerSize();
                    }
                }
            }
            return turn;
        }else {
            boolean turn = false;
            for(int i=0;i<players.inventory.getContainerSize();++i){
                turn = itemStandOwner(players, players.inventory.getItem(i));
                i= players.inventory.getContainerSize();
            }
            return turn;
        }
    }

    private static boolean checkIfItemEntityIsTheStandObject(PlayerEntity players){
        if(players instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) players;
            boolean turn = player.getLevel().getEntities().filter(entity -> entity instanceof ItemEntity && ((ItemEntity)entity).getItem().getItem() == InitItems.STAND_GLOVES.get())
                    .anyMatch(entity -> {
                        ItemStack itemStack = ((ItemEntity) entity).getItem();
                        return itemStandOwner(player, itemStack);
                    });

            return turn;
        }else {
            return players.level.getEntitiesOfClass(ItemEntity.class,players.getBoundingBox().inflate(1000), EntityPredicates.ENTITY_STILL_ALIVE).stream()
                    .anyMatch(itemEntity -> itemStandOwner(players,itemEntity.getItem()));
        }
    }

    private static boolean itemStandOwner(PlayerEntity player, ItemStack itemStack){
        if(itemStack.getItem() == InitItems.STAND_GLOVES.get()){
            CompoundNBT nbt = itemStack.getTag();
            if(nbt != null && nbt.contains("owner")){
                return nbt.getString("owner").equals(player.getName().getString());
            }
            return false;
        }
        return false;
    }


    @SubscribeEvent(priority = EventPriority.HIGH)

    public static void blockSomeDamage(LivingHurtEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!event.getEntity().level.isClientSide){
            IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
                if(power.getHeldAction() == InitStands.SHIELD_BUBBLE.get()){
                    int soap = 3*Math.round(event.getAmount());
                    ItemStack stack = BubbleLauncher.getSoapItem(entity);
                    if(!stack.isEmpty() && TommyGunItem.getAmmo(stack) > soap){
                        event.setAmount(event.getAmount()- (float) TommyGunItem.getAmmo(stack) /3);
                    }
                    HamonBubbleLauncher.consumeSoap(entity, soap);
                }
            });
            if(entity.getVehicle() instanceof ShieldBubble){
                ShieldBubble shieldBubble = (ShieldBubble) entity.getVehicle();
                if(!shieldBubble.isOwnerTarget()){
                    if(shieldBubble.getHealth() < event.getAmount()) {
                        shieldBubble.setHealth(shieldBubble.getHealth()-event.getAmount());
                        event.setAmount(event.getAmount()-shieldBubble.getHealth());
                    }
                }
            }
        }

    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void blockDamange(LivingAttackEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!event.getEntity().level.isClientSide){
            IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
                if(power.getHeldAction() == InitStands.SHIELD_BUBBLE.get()){
                    int soap = 3*Math.round(event.getAmount());
                    ItemStack stack = BubbleLauncher.getSoapItem(entity);
                    if(!stack.isEmpty() && TommyGunItem.getAmmo(stack) < soap){
                        power.setCooldownTimer(InitStands.SHIELD_BUBBLE.get(), 60);
                    }else {
                        event.setCanceled(true);
                    }
                    HamonBubbleLauncher.consumeSoap(entity, soap);
                }
            });
            if(entity.getVehicle() instanceof ShieldBubble){
                ShieldBubble shieldBubble = (ShieldBubble) entity.getVehicle();
                if(!shieldBubble.isOwnerTarget()){
                    if(shieldBubble.getHealth() > event.getAmount()) {
                        shieldBubble.setHealth(shieldBubble.getHealth()-event.getAmount());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void betterGentlyWeeps(ProjectileImpactEvent event){
        Entity projectile = event.getEntity();
        if(!projectile.level.isClientSide){
            if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY){
                Entity target = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
                if(!(projectile instanceof OwnerBoundProjectileEntity) && target instanceof ShieldBubble){
                    ShieldBubble bubble = (ShieldBubble) target;
                    if(bubble.getOwner() != null){
                        if(INonStandPower.getNonStandPowerOptional(bubble.getOwner()).map(iPower ->{
                            Optional<HamonData> hamonDataOptional = iPower.getTypeSpecificData(ModPowers.HAMON.get());
                            return hamonDataOptional.map(hamonData -> hamonData.isSkillLearned(ModHamonSkills.PROJECTILE_SHIELD.get())).orElse(false);
                        }).orElse(false)){
                            event.setCanceled(true);
                            projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
                            HamonBubbleLauncher.consumeSoap(bubble.getOwner(), 10);
                            if(projectile instanceof ModdedProjectileEntity){
                                ((ModdedProjectileEntity) projectile).setIsDeflected();
                            }
                        }
                    }
                }
            }
        }
    }


}

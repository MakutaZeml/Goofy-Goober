package com.zeml.rotp_zthm.capabilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class LivingData implements INBTSerializable<CompoundNBT> {
    private final LivingEntity entity;
    private boolean triedHamonStand = false;


    public LivingData(LivingEntity entity) {
        this.entity = entity;
    }

    public void setTriedHamonStand(boolean triedHamonStand) {
        this.triedHamonStand = triedHamonStand;
    }

    public boolean isTriedHamonStand() {
        return this.triedHamonStand;
    }




    public void syncWithEntityOnly(ServerPlayerEntity player){

    }

    public void syncWithAnyPlayer(ServerPlayerEntity player) {

        //AddonPackets.sendToClient(new TrPickaxesThrownPacket(entity.getId(), pickaxesThrown), player);
    }



    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("triedHamonStand",this.triedHamonStand);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.triedHamonStand = nbt.getBoolean("triedHamonStand");
    }
}
package com.zeml.rotp_zthm.network.server;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.zeml.rotp_zthm.ExtraHamonConfig;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HamonCommonConfigPacket {
    private final ExtraHamonConfig.Common.SyncedValues values;

    public HamonCommonConfigPacket(ExtraHamonConfig.Common.SyncedValues values){
        this.values= values;
    }

    public static class Handler implements IModPacketHandler<HamonCommonConfigPacket> {

        @Override
        public void encode(HamonCommonConfigPacket msg, PacketBuffer buf) {
            msg.values.writeToBuf(buf);
        }

        @Override
        public HamonCommonConfigPacket decode(PacketBuffer buf) {
            return new HamonCommonConfigPacket(new ExtraHamonConfig.Common.SyncedValues(buf));
        }

        @Override
        public void handle(HamonCommonConfigPacket msg, Supplier<NetworkEvent.Context> ctx) {
            msg.values.changeConfigValues();
        }

        @Override
        public Class<HamonCommonConfigPacket> getPacketClass() {
            return HamonCommonConfigPacket.class;
        }
    }
}

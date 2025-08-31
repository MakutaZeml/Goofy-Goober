package com.zeml.rotp_zthm;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.ResetSyncedCommonConfigPacket;
import com.zeml.rotp_zthm.network.server.HamonCommonConfigPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = ExtraHamonStandsAddon.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtraHamonConfig {
    public static class Common{
        private boolean loaded = false;
        public final ForgeConfigSpec.BooleanValue hamonToStand;
        public final ForgeConfigSpec.IntValue control;
        public final ForgeConfigSpec.IntValue strength;
        public final ForgeConfigSpec.IntValue breathing;

        private Common(ForgeConfigSpec.Builder builder) {
            this(builder, null);
        }

        private Common(ForgeConfigSpec.Builder builder, @Nullable String mainPath){
            if (mainPath != null) {
                builder.push(mainPath);
            }
            builder.push("Can Hamon give Stands (OverWrites Hermit Purple configs)");
            hamonToStand = builder.comment("Does training hamon gives Stand?")
                    .translation("rotp_zthm.config.hamonToStand")
                    .define("hamonToStand",false);
            builder.pop();
            builder.push("Levels Required");
            control = builder.comment("Hamon control level to obtain the Hamon Stand")
                    .translation("rotp_zthm.config.control")
                    .defineInRange("control",60,0,60);
            strength = builder.comment("Hamon strength level to obtain the Hamon Stand")
                    .translation("rotp_zthm.config.strength")
                    .defineInRange("strength",60,0,60);
            breathing = builder.comment("Hamon breathing level to obtain the Hamon Stand")
                    .translation("rotp_zthm.config.breathing")
                    .defineInRange("breathing",100,0,100);
            builder.pop();


        }

        public boolean isConfigLoaded() {
            return loaded;
        }

        private void onLoadOrReload() {
            loaded = true;
        }

        public static class SyncedValues {
            private final boolean hamonToStand;
            private final int control;
            private final int strength;
            private final int breathing;

            public SyncedValues(PacketBuffer buf){
                hamonToStand = buf.readBoolean();
                control = buf.readInt();
                strength = buf.readInt();
                breathing = buf.readInt();

            }
            public void writeToBuf(PacketBuffer buf){
                buf.writeBoolean(hamonToStand);
                buf.writeInt(control);
                buf.writeInt(strength);
                buf.writeInt(breathing);
            }
            private SyncedValues(ExtraHamonConfig.Common config){
                hamonToStand = config.hamonToStand.get();
                control = config.control.get();
                strength = config.strength.get();
                breathing = config.breathing.get();
            }

            public void changeConfigValues(){
                COMMON_SYNCED_TO_CLIENT.hamonToStand.set(hamonToStand);
                COMMON_SYNCED_TO_CLIENT.control.set(control);
                COMMON_SYNCED_TO_CLIENT.strength.set(strength);
                COMMON_SYNCED_TO_CLIENT.breathing.set(breathing);
            }
            public static void resetConfig() {
                COMMON_SYNCED_TO_CLIENT.hamonToStand.clearCache();
                COMMON_SYNCED_TO_CLIENT.control.clearCache();
                COMMON_SYNCED_TO_CLIENT.strength.clearCache();
                COMMON_SYNCED_TO_CLIENT.breathing.clearCache();
            }
            public static void syncWithClient(ServerPlayerEntity player) {
                PacketManager.sendToClient(new HamonCommonConfigPacket(new Common.SyncedValues(COMMON_FROM_FILE)), player);
            }
            public static void onPlayerLogout(ServerPlayerEntity player) {
                PacketManager.sendToClient(new ResetSyncedCommonConfigPacket(), player);
            }
        }
    }
    static final ForgeConfigSpec commonSpec;
    private static final ExtraHamonConfig.Common COMMON_FROM_FILE;
    private static final ExtraHamonConfig.Common COMMON_SYNCED_TO_CLIENT;
    static {
        final Pair<ExtraHamonConfig.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ExtraHamonConfig.Common::new);
        commonSpec = specPair.getRight();
        COMMON_FROM_FILE = specPair.getLeft();

        // how tf do the configs work?
        final Pair<ExtraHamonConfig.Common, ForgeConfigSpec> syncedSpecPair = new ForgeConfigSpec.Builder().configure(builder -> new ExtraHamonConfig.Common(builder, "synced"));
        CommentedConfig config = CommentedConfig.of(InMemoryCommentedFormat.defaultInstance());
        ForgeConfigSpec syncedSpec = syncedSpecPair.getRight();
        syncedSpec.correct(config);
        syncedSpec.setConfig(config);
        COMMON_SYNCED_TO_CLIENT = syncedSpecPair.getLeft();
    }
    public static ExtraHamonConfig.Common getCommonConfigInstance(boolean isClientSide) {
        return isClientSide && !ClientUtil.isLocalServer() ? COMMON_SYNCED_TO_CLIENT : COMMON_FROM_FILE;
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (ExtraHamonStandsAddon.MOD_ID.equals(config.getModId()) && config.getType() == ModConfig.Type.COMMON) {
            COMMON_FROM_FILE.onLoadOrReload();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfig.Reloading event) {
        ModConfig config = event.getConfig();
        if (ExtraHamonStandsAddon.MOD_ID.equals(config.getModId()) && config.getType() == ModConfig.Type.COMMON) {
            // FIXME sync the config to all players on the server
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                server.getPlayerList().getPlayers().forEach(Common.SyncedValues::syncWithClient);
            }
        }
    }
}

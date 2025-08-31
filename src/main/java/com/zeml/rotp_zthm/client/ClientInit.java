package com.zeml.rotp_zthm.client;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.client.render.entity.renderer.projectiles.*;
import com.zeml.rotp_zthm.client.render.entity.renderer.stand.GoofyGooberRenderer;
import com.zeml.rotp_zthm.init.AddonStands;

import com.zeml.rotp_zthm.init.InitEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ExtraHamonStandsAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    private static final IItemPropertyGetter STAND_ITEM_INVISIBLE = (itemStack, clientWorld, livingEntity) -> {
        return !ClientUtil.canSeeStands() ? 1 : 0;
    };

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = event.getMinecraftSupplier().get();;

        RenderingRegistry.registerEntityRenderingHandler(AddonStands.GOOFY_GOOBER.getEntityType(), GoofyGooberRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.RIDE_BUBBLE.get(), RideableBubbleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.PICK_ITEM_BUBBLE.get(), PickItemBubbleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.HAMON_BUBBLE.get(), HamonStandBubbleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.HAMON_BUBBLE_CUTTER.get(), HamonStandBubbleCutterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.DEFENSE_BUBBLE.get(), BubbleShieldRenderer::new);

    }


    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event){
        Minecraft mc = Minecraft.getInstance();


    }

}

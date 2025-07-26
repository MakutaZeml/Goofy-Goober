package com.zeml.rotp_zpb.client;

import com.github.standobyte.jojo.client.ClientUtil;
import com.zeml.rotp_zpb.client.render.entity.renderer.stand.ItemStandRenderer;
import com.zeml.rotp_zpb.ItemStandAddon;
import com.zeml.rotp_zpb.init.AddonStands;

import com.zeml.rotp_zpb.init.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ItemStandAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    private static final IItemPropertyGetter STAND_ITEM_INVISIBLE = (itemStack, clientWorld, livingEntity) -> {
        return !ClientUtil.canSeeStands() ? 1 : 0;
    };

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = event.getMinecraftSupplier().get();;

        RenderingRegistry.registerEntityRenderingHandler(AddonStands.ITEM_STAND.getEntityType(), ItemStandRenderer::new);

        /*Delete all the event.enqueueWork stuff if the Stand it's visible to no-stand Users like Cream Starter*/
        event.enqueueWork(() -> {
            ItemModelsProperties.register(InitItems.STAND_ITEM.get(),
                    new ResourceLocation(ItemStandAddon.MOD_ID, "stand_invisible"),
                    STAND_ITEM_INVISIBLE);
        });

    }


    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event){
        Minecraft mc = Minecraft.getInstance();


    }

}

package com.zeml.rotp_zthm.client.render.entity.renderer.stand;

import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.client.render.entity.model.stand.GoofyGooberModel;
import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class GoofyGooberRenderer extends StandEntityRenderer<GoofyGooberEntity, GoofyGooberModel> {

    public GoofyGooberRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GoofyGooberModel(), new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "textures/entity/stand/goofy_goober.png"), 0);
    }

}

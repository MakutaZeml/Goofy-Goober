package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.zeml.rotp_zthm.client.render.entity.model.projectiles.HamonStandBubbleCutterModel;
import com.zeml.rotp_zthm.entity.projectile.HamonStandBubbleCutterEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HamonStandBubbleCutterRenderer extends SimpleEntityRenderer<HamonStandBubbleCutterEntity, HamonStandBubbleCutterModel> {
    private static final ResourceLocation BUBBLE = new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/hamon_bubble_cutter.png");
    public HamonStandBubbleCutterRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HamonStandBubbleCutterModel(), BUBBLE);
    }


    @Override
    public ResourceLocation getTextureLocation(HamonStandBubbleCutterEntity entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), BUBBLE);
    }

}

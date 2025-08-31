package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.zeml.rotp_zthm.client.render.entity.model.projectiles.BubblesModel;
import com.zeml.rotp_zthm.entity.projectile.HamonStandBubbleEntity;
import com.zeml.rotp_zthm.entity.projectile.PickUpBubbleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class HamonStandBubbleRenderer extends BubbleRenderer<HamonStandBubbleEntity>{
    public HamonStandBubbleRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BubblesModel<HamonStandBubbleEntity>());
    }
}

package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.zeml.rotp_zthm.client.render.entity.model.projectiles.BubblesModel;
import com.zeml.rotp_zthm.entity.projectile.PickUpBubbleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class PickItemBubbleRenderer extends BubbleRenderer<PickUpBubbleEntity>{

    public PickItemBubbleRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BubblesModel<PickUpBubbleEntity>());
    }
}

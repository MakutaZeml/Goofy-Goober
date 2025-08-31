package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.zeml.rotp_zthm.client.render.entity.model.projectiles.BigBubbleModel;
import com.zeml.rotp_zthm.entity.projectile.ShieldBubble;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class BubbleShieldRenderer extends BigBubbleRenderer<ShieldBubble>{


    public BubbleShieldRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BigBubbleModel<ShieldBubble>());
    }


}

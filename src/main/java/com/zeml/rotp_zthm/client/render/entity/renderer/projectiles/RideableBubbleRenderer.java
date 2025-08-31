package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zthm.client.render.entity.model.projectiles.BigBubbleModel;
import com.zeml.rotp_zthm.entity.projectile.RideableBubble;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class RideableBubbleRenderer extends BigBubbleRenderer<RideableBubble> {
    public RideableBubbleRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BigBubbleModel<RideableBubble>());
    }

    @Override
    protected void renderModel(RideableBubble entity, BigBubbleModel model,
                               float partialTick, MatrixStack matrixStack, IVertexBuilder vertexBuilder, int packedLight) {
        matrixStack.pushPose();
        float size = entity.getSize(partialTick);
        if (size < 1) {
            matrixStack.scale(size, size, size);
        }
        model.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }


}

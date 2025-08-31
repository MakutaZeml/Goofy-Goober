package com.zeml.rotp_zthm.client.render.entity.model.projectiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zthm.entity.projectile.BubblesEntity;
import com.zeml.rotp_zthm.entity.projectile.RideableBubble;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BigBubbleModel<T extends BubblesEntity> extends EntityModel<T> {
    private final ModelRenderer bubble;

    public BigBubbleModel() {
        super(RenderType::entityTranslucent);
        texWidth = 256;
        texHeight = 256;

        bubble = new ModelRenderer(this);
        bubble.setPos(0.0F, -15.0F, 0.0F);
        bubble.texOffs(0, 0).addBox(-12.0F, -12.0F, -12.0F, 24.0F, 24.0F, 24.0F, 0.0F, false);
        bubble.texOffs(96, 0).addBox(-9.0F, -9.0F, -15.0F, 18.0F, 18.0F, 30.0F, 0.0F, false);
        bubble.texOffs(0, 48).addBox(-6.0F, -6.0F, -16.5F, 12.0F, 12.0F, 33.0F, 0.0F, false);
        bubble.texOffs(90, 48).addBox(-15.0F, -9.0F, -9.0F, 30.0F, 18.0F, 18.0F, 0.0F, false);
        bubble.texOffs(90, 84).addBox(-16.5F, -6.0F, -6.0F, 33.0F, 12.0F, 12.0F, 0.0F, false);
        bubble.texOffs(0, 93).addBox(-9.0F, -15.0F, -9.0F, 18.0F, 30.0F, 18.0F, 0.0F, false);
        bubble.texOffs(72, 108).addBox(-6.0F, -16.5F, -6.0F, 12.0F, 33.0F, 12.0F, 0.0F, false);
    }
    @Override
    public void setupAnim(BubblesEntity entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        bubble.yRot = yRotationOffset * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bubble.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

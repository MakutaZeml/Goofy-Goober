package com.zeml.rotp_zthm.client.render.entity.renderer.projectiles;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.zeml.rotp_zthm.client.render.entity.model.projectiles.BubblesModel;
import com.zeml.rotp_zthm.entity.projectile.BubblesEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public abstract class BubbleRenderer<T extends BubblesEntity> extends SimpleEntityRenderer<T, BubblesModel<T>> {

    private static final ResourceLocation BUBBLE = new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/hamon_bubble.png");

    public BubbleRenderer(EntityRendererManager renderManager, BubblesModel<T> model) {
        super(renderManager, model, BUBBLE);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), BUBBLE);
    }


}

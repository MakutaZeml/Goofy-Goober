package com.zeml.rotp_zthm.client.render.entity.renderer.stand;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.model.stand.StarPlatinumModel;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.github.standobyte.jojo.entity.stand.stands.StarPlatinumEntity;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.client.render.entity.model.stand.HouseOfEarthModel;
import com.zeml.rotp_zthm.entity.stands.HouseOfEarthEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HouseOfEarthRenderer extends StandEntityRenderer<HouseOfEarthEntity, StandEntityModel<HouseOfEarthEntity>> {

    public HouseOfEarthRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "house_of_earth"), HouseOfEarthModel::new),
                new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "textures/entity/stand/house_of_earth.png"), 0);
    }
}

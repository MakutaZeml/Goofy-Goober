package com.zeml.rotp_zthm.mixin;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.GlovesLayer;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.IFirstPersonHandLayer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.item.GlovesItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import com.zeml.rotp_zthm.init.InitItems;
import com.zeml.rotp_zthm.init.InitStands;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GlovesLayer.class, remap = false)
public abstract class GlovesLayerMixin <T extends LivingEntity, M extends PlayerModel<T>> extends LayerRenderer<T, M> implements IFirstPersonHandLayer {

    @Shadow
    public static boolean areGloves(ItemStack item) {
        return !item.isEmpty() && item.getItem() instanceof GlovesItem;
    }

    @Shadow
    public static ItemStack getRenderedGlovesItem(LivingEntity entity) {
        ItemStack checkedItem = entity.getMainHandItem();
        if (areGloves(checkedItem)) return checkedItem;
        checkedItem = entity.getOffhandItem();
        if (areGloves(checkedItem)) return checkedItem;
        return ItemStack.EMPTY;
    }

    @Shadow @Final private M glovesModel;

    @Shadow protected abstract ResourceLocation getTexture(GlovesItem gloves);

    public GlovesLayerMixin(IEntityRenderer<T, M> p_i50926_1_) {
        super(p_i50926_1_);
    }


    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void onRender(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ticks, float yRot, float xRot, CallbackInfo ci) {
        ItemStack glovesItemStack = getRenderedGlovesItem(entity);
        if (glovesItemStack.getItem() == InitItems.STAND_GLOVES.get()) {
            ci.cancel();
            if(entity.isInvisible()) {
                ci.cancel();
                return;
            }
            if(!ClientUtil.canSeeStands()){
                glovesItemStack = new ItemStack(ModItems.BUBBLE_GLOVES.get());
            }
            GlovesItem gloves = (GlovesItem) glovesItemStack.getItem();
            M playerModel = getParentModel();
            glovesModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
            playerModel.copyPropertiesTo(glovesModel);
            glovesModel.setupAnim(entity, limbSwing, limbSwingAmount, ticks, yRot, xRot);

            glovesModel.leftArm.visible = playerModel.leftArm.visible;
            glovesModel.leftSleeve.visible = playerModel.leftArm.visible;
            glovesModel.rightArm.visible = playerModel.rightArm.visible;
            glovesModel.rightSleeve.visible = playerModel.rightArm.visible;
            ResourceLocation texture = getTexture(gloves);
            if(ClientUtil.canSeeStands()){
                if(glovesItemStack.getTag().hasUUID("UUID")){
                    PlayerEntity player = entity.level.getPlayerByUUID(glovesItemStack.getTag().getUUID("UUID"));
                    if(player != null){
                        IStandPower standPower = IStandPower.getPlayerStandPower(player);
                        if(standPower.getType() == InitStands.GOOFY_GOOBER.getStandType() && standPower.getStandInstance().isPresent()){
                            texture = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager.getStandSkin(standPower.getStandInstance().get()),texture);
                        }
                    }
                }
            }
            IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, glovesItemStack.hasFoil());
            glovesModel.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }


    @Inject(method = "renderHandFirstPerson", at = @At("HEAD"), cancellable = true)
    private void onRenderHandFirstPerson(HandSide side, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, PlayerRenderer playerRenderer, CallbackInfo ci){
        ItemStack glovesItemStack = getRenderedGlovesItem(player);
        if(glovesItemStack.getItem() == InitItems.STAND_GLOVES.get()){
            ci.cancel();
            GlovesItem glovesItem = (GlovesItem) glovesItemStack.getItem();
            PlayerModel<AbstractClientPlayerEntity> model = (PlayerModel<AbstractClientPlayerEntity>) glovesModel;
            ResourceLocation texture = getTexture(glovesItem);

            if(glovesItemStack.getTag().hasUUID("UUID")){
                PlayerEntity owner = player.level.getPlayerByUUID(glovesItemStack.getTag().getUUID("UUID"));
                if(owner != null){
                    IStandPower standPower = IStandPower.getPlayerStandPower(owner);
                    if(standPower.getType() == InitStands.GOOFY_GOOBER.getStandType() && standPower.getStandInstance().isPresent()){
                        texture = StandSkinsManager.getInstance().getRemappedResPath(manager -> manager.getStandSkin(standPower.getStandInstance().get()),texture);
                    }
                }
            }

            ClientUtil.setupForFirstPersonRender(model, player);
            IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, glovesItemStack.hasFoil());
            ModelRenderer glove = ClientUtil.getArm(model, side);
            ModelRenderer gloveOuter = ClientUtil.getArmOuter(model, side);
            glove.xRot = 0.0F;
            glove.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY);
            gloveOuter.xRot = 0.0F;
            gloveOuter.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY);
        }

    }



}

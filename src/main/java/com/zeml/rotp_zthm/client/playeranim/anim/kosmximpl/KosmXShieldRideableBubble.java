package com.zeml.rotp_zthm.client.playeranim.anim.kosmximpl;

import com.github.standobyte.jojo.client.playeranim.anim.kosmximpl.hamon.KosmXWindupAttackHandler;
import com.github.standobyte.jojo.client.playeranim.kosmx.anim.modifier.KosmXFixedFadeModifier;
import com.github.standobyte.jojo.client.playeranim.kosmx.anim.modifier.KosmXHandsideMirrorModifier;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.util.Ease;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class KosmXShieldRideableBubble extends KosmXWindupAttackHandler {
    private static final ResourceLocation BUBBLE = new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "rideable_bubble");

    public KosmXShieldRideableBubble(ResourceLocation id) {
        super(id);
    }


    @Override
    public boolean setWindupAnim(PlayerEntity player) {
        return setAnimFromName(player, BUBBLE, anim-> new KosmXShieldBubble.ChargedAttackAnimPlayer(anim).windupStopsAt(anim.returnToTick));
    }

    @Override
    public boolean setAttackAnim(PlayerEntity player) {
        return setToSwingTick(player, -1, BUBBLE);
    }

    @Override
    public void stopAnim(PlayerEntity player) {
        fadeOutAnim(player, KosmXFixedFadeModifier.standardFadeIn(10, Ease.OUTCUBIC), null);
    }

    @Override
    protected ModifierLayer<IAnimation> createAnimLayer(AbstractClientPlayerEntity player) {
        return new ModifierLayer<>(null, new KosmXHandsideMirrorModifier(player));
    }
}

package com.zeml.rotp_zthm.client.playeranim.anim;

import com.github.standobyte.jojo.client.playeranim.PlayerAnimationHandler;
import com.github.standobyte.jojo.client.playeranim.anim.interfaces.WindupAttackAnim;
import com.zeml.rotp_zhp.RotpHermitPurpleAddon;
import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import net.minecraft.util.ResourceLocation;

public class HamonPlayerAnimations {
    public static WindupAttackAnim bubbleShield;
    public static WindupAttackAnim rideBubble;

    public static void init(){
        //Goofy Goober
        bubbleShield = PlayerAnimationHandler.getPlayerAnimator().registerAnimLayer(
                "com.zeml.rotp_zthm.client.playeranim.anim.kosmximpl.KosmXShieldBubble",
                new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "grapple"), 1,
                WindupAttackAnim.NoPlayerAnimator::new);
        rideBubble = PlayerAnimationHandler.getPlayerAnimator().registerAnimLayer(
                "com.zeml.rotp_zthm.client.playeranim.anim.kosmximpl.KosmXShieldRideableBubble",
                new ResourceLocation(ExtraHamonStandsAddon.MOD_ID, "rideable_bubble"), 1,
                WindupAttackAnim.NoPlayerAnimator::new);
    }
}

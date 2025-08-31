package com.zeml.rotp_zthm.mixin;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.item.BubbleGlovesItem;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.zeml.rotp_zthm.init.InitItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.standobyte.jojo.action.Action.conditionMessage;

@Mixin(value = HamonBubbleLauncher.class, remap = false)
public abstract class HamonBubbleLauncherMixin{



    @Inject(method = "checkSoap",at = @At("RETURN"), cancellable = true)
    private static void onCheckSoap(LivingEntity entity, CallbackInfoReturnable<ActionConditionResult> cir){
        ItemStack item = goofy_Goober$onGetStandGloves(entity);
        if(item.getItem()  == InitItems.STAND_GLOVES.get()){
            if (item.getItem() == ModItems.BUBBLE_GLOVES.get() && TommyGunItem.getAmmo(item) <= 0){
                cir.setReturnValue(conditionMessage("gloves_no_soap"));
            }
            cir.setReturnValue(ActionConditionResult.POSITIVE);
        }
    }


    @Inject(method = "consumeSoap", at = @At("RETURN"), cancellable = true)
    private static void onConsumeSoap(LivingEntity entity, int glovesAmmo, CallbackInfoReturnable<HamonBubbleLauncher.TookSoapFrom> cir){
        ItemStack item = goofy_Goober$onGetStandGloves(entity);
        if(item.getItem()  == InitItems.STAND_GLOVES.get()){
            BubbleGlovesItem.consumeAmmo(item, glovesAmmo, entity);
            cir.setReturnValue(HamonBubbleLauncher.TookSoapFrom.GLOVES);
        }
    }

    @Unique
    private static ItemStack goofy_Goober$onGetStandGloves(LivingEntity entity){
        if(entity.getMainHandItem().getItem()== InitItems.STAND_GLOVES.get()){
            return entity.getMainHandItem();
        } else if (entity.getOffhandItem().getItem()== InitItems.STAND_GLOVES.get()) {
            return entity.getOffhandItem();
        }
        return ItemStack.EMPTY;
    }


}

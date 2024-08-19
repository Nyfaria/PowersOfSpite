package com.nyfaria.powersofspite.mixin;

import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.init.AbilityInit;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectUtil.class)
public class MobEffectUtilMixin {

    @Inject(method = "hasWaterBreathing", at = @At("HEAD"), cancellable = true)
    private static void hasWaterBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof Player player){
            if(Services.PLATFORM.getAbilityHolder(player).hasAbility(AbilityInit.WATER_BREATHING.get())){
                cir.setReturnValue(true);
            }
        }

    }
}

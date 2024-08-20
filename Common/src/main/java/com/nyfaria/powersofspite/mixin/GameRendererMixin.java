package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @WrapMethod(method = "getNightVisionScale")
    private static float getNightVisionScale(LivingEntity pLivingEntity, float pNanoTime, Operation<Float> original){
        if(pLivingEntity instanceof Player player){
            if(player.isAlive()) {
                if(Services.PLATFORM.getAbilityHolder(player) != null) {
                    if (Services.PLATFORM.getAbilityHolder(player).hasAbility(AbilityInit.CLEAR_VISION.get())) {
                        return 1.0f;
                    }
                }
            }
        }
        return original.call(pLivingEntity, pNanoTime);
    }
}

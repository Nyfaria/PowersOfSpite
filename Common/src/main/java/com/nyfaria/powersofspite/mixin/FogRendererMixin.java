package com.nyfaria.powersofspite.mixin;

import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "setupFog", at= @At(value = "HEAD"), cancellable = true)
    private static void setupColor(Camera pCamera, FogRenderer.FogMode pFogMode, float pFarPlaneDistance, boolean p_234176_, float p_234177_, CallbackInfo ci){
        if(Minecraft.getInstance().player != null){
            if(Minecraft.getInstance().player.isAlive()) {
                if(Services.PLATFORM.getAbilityHolder(Minecraft.getInstance().player) != null) {
                    if (Services.PLATFORM.getAbilityHolder(Minecraft.getInstance().player).hasAbility(AbilityInit.CLEAR_VISION.get())) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}

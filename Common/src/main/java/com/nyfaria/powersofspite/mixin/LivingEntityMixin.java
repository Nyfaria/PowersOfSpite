package com.nyfaria.powersofspite.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyVariable(method="hurt", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"), ordinal=1)
    private float actuallyHurt(float value) {
        return value / 2f;
    }
}

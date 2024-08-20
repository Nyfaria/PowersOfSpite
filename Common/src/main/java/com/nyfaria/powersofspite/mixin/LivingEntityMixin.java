package com.nyfaria.powersofspite.mixin;

import com.nyfaria.powersofspite.ability.passive.DamageReductionPassive;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyVariable(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"), ordinal = 1)
    private float actuallyHurt(float value) {
        if ((Object) this instanceof Player player) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
            double amount = holder.getAbilities().stream().filter(ability -> ability instanceof DamageReductionPassive).mapToDouble(ability2 -> ((DamageReductionPassive) ability2).getAmount()).sum();
            if (amount > 0) {
                return (float) (value / amount);
            }
        }
        return value;
    }

    @Inject(method = "calculateFallDamage", at = @At(value = "HEAD"), cancellable = true)
    private void noFallDamage(float pFallDistance, float pDamageMultiplier, CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Player player) {
            if (player.level().isClientSide()) {
                return;
            }
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
            if (holder.hasAbility(AbilityInit.BOUNCE.get())) {
                cir.setReturnValue(0);
            }
        }
    }
    @Inject(method="hasEffect", at=@At("HEAD"), cancellable = true)
    public void hasClearVision(MobEffect pEffect, CallbackInfoReturnable<Boolean> cir) {
        if((Object)this instanceof Player player){
            if(player.isAlive()) {
                if(pEffect  == MobEffects.NIGHT_VISION) {
                    if (Services.PLATFORM.getAbilityHolder(player) != null) {
                        if (Services.PLATFORM.getAbilityHolder(player).hasAbility(AbilityInit.CLEAR_VISION.get())) {
                            cir.setReturnValue(true);
                        }
                    }
                }
            }
        }
    }
}

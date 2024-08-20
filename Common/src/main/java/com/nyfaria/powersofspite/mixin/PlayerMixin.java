package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.nyfaria.powersofspite.ability.passive.DigSpeedPassive;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract void remove(RemovalReason pReason);

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.isAlive()) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder((Player) (Object) this);
            if (holder != null) {
                holder.tick();
            }
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z", ordinal = 0))
    private boolean onTick(Player instance, Operation<Boolean> original) {
        if (!isSpectator()) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder((Player) (Object) this);
            if (holder != null) {
                return Services.PLATFORM.getAbilityHolder((Player) (Object) this).isTicking(AbilityInit.INTANGIBILITY.get());
            }
        }
        return original.call(instance);
    }

    @WrapMethod(method = "getDestroySpeed")
    private float hasDigSpeed(BlockState pState, Operation<Float> original) {
        double increase = Services.PLATFORM.getAbilityHolder((Player) (Object) this).getAbilities().stream().filter(a -> a instanceof DigSpeedPassive).mapToDouble(ability -> ((DigSpeedPassive) ability).getChange()).sum();
        if (increase != 0) {
            return original.call(pState) * (float) increase;
        }
        return original.call(pState);
    }
}

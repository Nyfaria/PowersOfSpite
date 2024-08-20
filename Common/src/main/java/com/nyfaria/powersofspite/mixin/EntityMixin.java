package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void remove(Entity.RemovalReason pReason);

    @Shadow private Level level;

    @Shadow public abstract void setPos(double pPos, double h,double hs);

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract float getViewYRot(float pPartialTick);

    @Shadow public abstract double getY(double pScale);

    @WrapMethod(method="move")
    public void move(MoverType pType, Vec3 pPos, Operation<Void> original) {
        if((Object)this instanceof Player player) {
            if (player.isAlive()) {
                AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
                if (holder != null) {
                    boolean isIntangible = Services.PLATFORM.getAbilityHolder(player).isTicking(AbilityInit.INTANGIBILITY.get());
                    if (!isIntangible) {
                        original.call(pType, pPos);
                        return;
                    }
                    if (level.getBlockState(player.blockPosition().below()).isAir()) {
                        original.call(pType, pPos);
                        return;
                    }
                    if (!level.getBlockState(player.blockPosition().below()).isSolid()) {
                        original.call(pType, pPos);
                        return;
                    }
                    this.setPos(this.getX() + pPos.x, Mth.clamp(this.getY() + pPos.y, Mth.floor(this.getY()), Double.MAX_VALUE), this.getZ() + pPos.z);
                }
            }
        }
    }
}

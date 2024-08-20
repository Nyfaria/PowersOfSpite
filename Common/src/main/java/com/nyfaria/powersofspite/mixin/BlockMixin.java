package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin {

    @WrapMethod(method ="updateEntityAfterFallOn")
    public void onFallenUpon(BlockGetter pLevel, Entity pEntity, Operation<Void> original) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (pEntity instanceof Player player) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
            if (holder.hasAbility(AbilityInit.BOUNCE.get())) {
                if (vec3.y < 0.0D) {
                    pEntity.setDeltaMovement(vec3.x, -vec3.y, vec3.z);
                    return;
                }
            }
        }
        original.call(pLevel,pEntity);
    }
}

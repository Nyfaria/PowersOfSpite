package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @WrapMethod(method = "getShape")
    public VoxelShape onBlockBreak(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, Operation<VoxelShape> original) {
        if(pContext instanceof EntityCollisionContext entityCollisionContext){
            if(entityCollisionContext.getEntity() instanceof Player player && player.isAlive()){
                AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
                if(holder != null) {
                    boolean hasIntangibility = Services.PLATFORM.getAbilityHolder(player).isTicking(AbilityInit.INTANGIBILITY.get());
                    boolean isBlockAtOrAbovePlayer = pPos.getY() >= player.getBlockY();
                    if (hasIntangibility && isBlockAtOrAbovePlayer) {
                        return Shapes.empty();
                    }
                }
            }
        }

        return original.call(pState, pLevel, pPos, pContext);
    }
}

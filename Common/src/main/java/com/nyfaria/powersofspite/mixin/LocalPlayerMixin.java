package com.nyfaria.powersofspite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.authlib.GameProfile;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @WrapMethod(method="suffocatesAt")
    public boolean suffocatesAt(BlockPos pPos, Operation<Boolean> original){
        if(((Object)this) instanceof LocalPlayer player){
            if(Services.PLATFORM.getAbilityHolder(player).isTicking(AbilityInit.INTANGIBILITY.get())){
                if(pPos.getY() >= getBlockY()){
                    return false;
                }
            }
        }
        return original.call(pPos);
    }
}

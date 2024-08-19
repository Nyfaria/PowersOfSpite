package com.nyfaria.powersofspite.ability.api;

import net.minecraft.world.entity.player.Player;

public interface Ability {
    default void onTick(Player player, long l){

    }
    default boolean isTicking(){
        return false;
    }
}

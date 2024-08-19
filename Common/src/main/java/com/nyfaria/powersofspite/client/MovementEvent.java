package com.nyfaria.powersofspite.client;

import net.minecraft.world.entity.player.Player;

public class MovementEvent {
    public static void forwardMovement(final Player PLAYER) {
            PLAYER.setDeltaMovement(PLAYER.getViewVector(0).multiply(3.15f, 3.15f, 3.15f));
    }
}

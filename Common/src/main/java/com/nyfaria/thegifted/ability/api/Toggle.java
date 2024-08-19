package com.nyfaria.thegifted.ability.api;

import net.minecraft.world.entity.player.Player;

public class Toggle extends Active {

    public void onTick(Player player, long tick, int stacks) {

    }


    public long getMaxTicks() {
        return 0;
    }
    @Override
    public int getMaxStacks() {
        return 1;
    }
}

package com.nyfaria.thegifted.ability.api;

import net.minecraft.world.entity.player.Player;

public class Passive implements Ability {
    public void onActivate(Player player, int stacks){

    }
    public void onTick(Player player,int tick, int stacks){

    }
    public void onDeactivate(Player player, int stacks){

    }

    @Override
    public int getMaxStacks() {
        return 1;
    }
}

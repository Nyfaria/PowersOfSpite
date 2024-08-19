package com.nyfaria.thegifted.ability.api;

import com.nyfaria.thegifted.power.api.Power;
import net.minecraft.world.entity.player.Player;

public class Active implements Ability {
    public void onUse(Player player, int stacks){

    }

    public void onRelease(Player player, int stacks){

    }

    @Override
    public int getMaxStacks() {
        return 1;
    }
}

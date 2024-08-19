package com.nyfaria.thegifted.ability.passive;

import com.nyfaria.thegifted.ability.api.Passive;
import net.minecraft.world.entity.player.Player;

public class FlightPassive extends Passive {
    @Override
    public void onActivate(Player player, int stacks) {
        player.getAbilities().mayfly = true;
        player.getAbilities().flying = true;
    }
    public void onDeactivate(Player player) {
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
    }
}

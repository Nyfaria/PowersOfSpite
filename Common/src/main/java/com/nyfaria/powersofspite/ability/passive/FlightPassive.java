package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;
import net.minecraft.world.entity.player.Player;

public class FlightPassive extends Passive {
    @Override
    public void onActivate(Player player) {
        player.getAbilities().mayfly = true;
    }
    public void onDeactivate(Player player) {
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
    }
}

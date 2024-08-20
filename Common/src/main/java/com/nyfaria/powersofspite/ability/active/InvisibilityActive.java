package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import net.minecraft.world.entity.player.Player;

public class InvisibilityActive extends Active {
    @Override
    public void onToggle(Player player, boolean onOff) {
        player.setInvisible(onOff);
    }

    @Override
    public boolean isToggle() {
        return true;
    }
}

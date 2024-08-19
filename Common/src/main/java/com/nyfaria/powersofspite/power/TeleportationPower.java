package com.nyfaria.powersofspite.power;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.power.api.Power;

import java.util.List;

public class TeleportationPower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return null;
    }

    @Override
    public boolean hasActive() {
        return true;
    }
}

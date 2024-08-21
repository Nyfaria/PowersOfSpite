package com.nyfaria.powersofspite.power;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.power.api.Power;

import java.util.List;

public class NonePower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of();
    }

    @Override
    public boolean hasActiveAbility() {
        return false;
    }
}

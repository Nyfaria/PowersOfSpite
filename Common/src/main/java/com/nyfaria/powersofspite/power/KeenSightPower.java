package com.nyfaria.powersofspite.power;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.power.api.Power;

import java.util.List;

public class KeenSightPower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of(AbilityInit.CLEAR_VISION.get());
    }

    @Override
    public boolean hasActiveAbility() {
        return false;
    }
}

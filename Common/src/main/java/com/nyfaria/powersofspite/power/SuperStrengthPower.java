package com.nyfaria.powersofspite.power;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.power.api.Power;

import java.util.List;

public class SuperStrengthPower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of(
                AbilityInit.POWERFUL_DAMAGE.get(),
                AbilityInit.SPEED_DIGGING.get(),
                AbilityInit.HEAVY_HITTER.get()
        );
    }

    @Override
    public boolean hasActive() {
        return false;
    }
}

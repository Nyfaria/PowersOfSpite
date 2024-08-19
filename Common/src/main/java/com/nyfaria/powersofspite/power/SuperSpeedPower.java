package com.nyfaria.powersofspite.power;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.cap.api.AbilityInfo;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.power.api.Power;

import java.util.List;

public class SuperSpeedPower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of(AbilityInit.SUPER_SPEED.get());
    }

    @Override
    public boolean hasActive() {
        return true;
    }
}

package com.nyfaria.thegifted.power;

import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.init.AbilityInit;
import com.nyfaria.thegifted.power.api.Power;

import java.util.List;

public class FlightPower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of(AbilityInit.FLIGHT.get());
    }
}

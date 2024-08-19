package com.nyfaria.thegifted.power;

import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.power.api.Power;

import java.util.List;

public class NonePower implements Power {
    @Override
    public List<Ability> getAbilities() {
        return List.of();
    }
}

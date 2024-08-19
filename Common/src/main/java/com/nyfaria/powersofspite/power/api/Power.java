package com.nyfaria.powersofspite.power.api;

import com.nyfaria.powersofspite.ability.api.Ability;

import java.util.List;

public interface Power {

    List<Ability> getAbilities();

    boolean hasActive();

}

package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;

public class DamageReductionPassive extends Passive {
    final double amount;
    public DamageReductionPassive(double amount) {
        super();
        this.amount = amount;
    }
    public DamageReductionPassive() {
        this(0.5d);
    }

}

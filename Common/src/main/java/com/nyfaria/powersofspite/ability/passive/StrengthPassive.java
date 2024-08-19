package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;

public class StrengthPassive extends Passive {
    final double amount;
    public StrengthPassive(double amount) {
        super();
        this.amount = amount;
    }
    public StrengthPassive() {
        this(4.0d);
    }

}

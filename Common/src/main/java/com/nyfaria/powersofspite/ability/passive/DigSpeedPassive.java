package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;

public class DigSpeedPassive extends Passive {
    private final double increase;

    public DigSpeedPassive(double increase) {
        this.increase = increase;
    }
    public DigSpeedPassive() {
        this(1.5);
    }

    public double getChange() {
        return increase;
    }
}

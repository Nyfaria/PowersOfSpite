package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SwimSpeedActive extends Active {

    public static final UUID SWIM_SPEED_UUID = UUID.fromString("c814cf85-88d2-4573-bff8-5e2345ab66ea");
    public final double amount;
    public SwimSpeedActive(double amount) {
        super();
        this.amount = amount;
    }
    public SwimSpeedActive() {
        this(0.5d);
    }
    @Override
    public void onToggle(Player player, boolean onOff) {
        if(onOff){
            player.getAttribute(Services.PLATFORM.getSwimSpeedAttribute()).addTransientModifier(new AttributeModifier(SWIM_SPEED_UUID, "Swim Speed", amount, AttributeModifier.Operation.ADDITION));
        }else{
            player.getAttribute(Services.PLATFORM.getSwimSpeedAttribute()).removeModifier(SWIM_SPEED_UUID);
        }
    }

    @Override
    public boolean isToggle() {
        return true;
    }
}

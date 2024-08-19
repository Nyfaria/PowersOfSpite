package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SpeedActive extends Active {
    private static final UUID SPEED_UUID = UUID.fromString("45a27846-be50-4518-b4b2-90847a55df61");
    final double amount;
    public SpeedActive(double amount) {
        super();
        this.amount = amount;
    }
    public SpeedActive() {
        this(0.2d);
    }

    @Override
    public void onToggle(Player player, boolean onOff) {
        if(onOff){
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(SPEED_UUID, "Speed", amount, AttributeModifier.Operation.ADDITION));
        }else{
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_UUID);
        }
    }

    @Override
    public boolean isToggle() {
        return true;
    }
}

package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.compress.archivers.StreamingNotSupportedException;

import java.util.UUID;

public class DamagePassive extends Passive {
    private static final UUID STRENGTH_UUID = UUID.fromString("99c5e14e-23ef-47cc-bb84-73491656346f");
    final double amount;
    public DamagePassive(double amount) {
        super();
        this.amount = amount;
    }

    @Override
    public void onActivate(Player player) {
        if(player.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(STRENGTH_UUID) == null) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(STRENGTH_UUID,"super_strength", amount, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void onDeactivate(Player player) {
        if (player.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(STRENGTH_UUID) != null) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(STRENGTH_UUID);
        }
    }

    public DamagePassive() {
        this(4.0d);
    }

}

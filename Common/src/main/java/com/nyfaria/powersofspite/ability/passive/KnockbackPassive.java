package com.nyfaria.powersofspite.ability.passive;

import com.nyfaria.powersofspite.ability.api.Passive;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class KnockbackPassive extends Passive {
    private static final UUID KNOCKBACK_UUID = UUID.fromString("9e634d4c-e40d-42a7-b99b-a4410697707e");
    final double amount;
    public KnockbackPassive(double amount) {
        super();
        this.amount = amount;
    }

    @Override
    public void onActivate(Player player) {
        if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).getModifier(KNOCKBACK_UUID)== null){
            player.getAttribute(Attributes.ATTACK_KNOCKBACK).addTransientModifier(new AttributeModifier(KNOCKBACK_UUID,"knockback", amount, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void onDeactivate(Player player) {
        if (player.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(KNOCKBACK_UUID) != null) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(KNOCKBACK_UUID);
        }
    }

    public KnockbackPassive() {
        this(4.0d);
    }

}

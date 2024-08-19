package com.nyfaria.powersofspite.init;

import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.packets.c2s.OnMovementPacket;
import com.nyfaria.powersofspite.packets.s2c.SetAnimationPacket;
import com.nyfaria.powersofspite.packets.s2c.UpdatePowerCapPacket;
import com.nyfaria.powersofspite.packets.s2c.UseAbilityPacket;
import com.nyfaria.powersofspite.packets.s2c.UpdateAbilityCapPacket;
import commonnetwork.api.Network;

public class NetworkInit {
    public static void loadClass() {
        Network.registerPacket(Constants.modLoc("update_power_cap"), UpdatePowerCapPacket.class, UpdatePowerCapPacket::encode, UpdatePowerCapPacket::decode, UpdatePowerCapPacket::handle);
        Network.registerPacket(Constants.modLoc("update_ability_cap"), UpdateAbilityCapPacket.class, UpdateAbilityCapPacket::encode, UpdateAbilityCapPacket::decode, UpdateAbilityCapPacket::handle);
        Network.registerPacket(Constants.modLoc("use_ability"), UseAbilityPacket.class, UseAbilityPacket::encode, UseAbilityPacket::decode, UseAbilityPacket::handle);
        Network.registerPacket(Constants.modLoc("set_animation"), SetAnimationPacket.class, SetAnimationPacket::encode, SetAnimationPacket::decode, SetAnimationPacket::handle);
        Network.registerPacket(Constants.modLoc("on_movement"), OnMovementPacket.class, OnMovementPacket::encode, OnMovementPacket::decode, OnMovementPacket::handle);
    }
}

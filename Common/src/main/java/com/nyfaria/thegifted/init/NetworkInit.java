package com.nyfaria.thegifted.init;

import com.nyfaria.thegifted.Constants;
import com.nyfaria.thegifted.packets.s2c.UpdateAbilityCapPacket;
import com.nyfaria.thegifted.packets.s2c.UpdatePowerCapPacket;
import com.nyfaria.thegifted.packets.s2c.UseAbilityPacket;
import commonnetwork.api.Network;

public class NetworkInit {
    public static void loadClass() {
        Network.registerPacket(Constants.modLoc("update_power_cap"),UpdatePowerCapPacket.class, UpdatePowerCapPacket::encode, UpdatePowerCapPacket::decode, UpdatePowerCapPacket::handle);
        Network.registerPacket(Constants.modLoc("update_ability_cap"), UpdateAbilityCapPacket.class, UpdateAbilityCapPacket::encode, UpdateAbilityCapPacket::decode, UpdateAbilityCapPacket::handle);
        Network.registerPacket(Constants.modLoc("use_ability"), UseAbilityPacket.class, UseAbilityPacket::encode, UseAbilityPacket::decode, UseAbilityPacket::handle);
    }
}

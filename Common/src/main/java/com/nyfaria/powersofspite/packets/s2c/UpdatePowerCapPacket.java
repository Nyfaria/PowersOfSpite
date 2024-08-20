package com.nyfaria.powersofspite.packets.s2c;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.client.ClientUtils;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record UpdatePowerCapPacket(UUID player, CompoundTag holder) {
    public static final ResourceLocation LOCATION = SpiteConstants.modLoc("set_cosmetic");


    public static UpdatePowerCapPacket decode(FriendlyByteBuf buf) {
        return new UpdatePowerCapPacket(buf.readUUID(), buf.readNbt());
    }

    public static void handle(PacketContext<UpdatePowerCapPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side())) {
            Services.PLATFORM.getPowerHolder(ClientUtils.getPlayerByUUID(ctx.message().player())).load(ctx.message().holder());
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(player);
        buf.writeNbt(holder);
    }
}

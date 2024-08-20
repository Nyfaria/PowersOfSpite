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

public record UpdateAbilityCapPacket(UUID player, CompoundTag holder) {
    public static final ResourceLocation LOCATION = SpiteConstants.modLoc("set_cosmetic");


    public static UpdateAbilityCapPacket decode(FriendlyByteBuf buf) {
        return new UpdateAbilityCapPacket(buf.readUUID(), buf.readNbt());
    }

    public static void handle(PacketContext<UpdateAbilityCapPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side())) {
            Services.PLATFORM.getAbilityHolder(ClientUtils.getPlayerByUUID(ctx.message().player())).load(ctx.message().holder());
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(player);
        buf.writeNbt(holder);
    }
}

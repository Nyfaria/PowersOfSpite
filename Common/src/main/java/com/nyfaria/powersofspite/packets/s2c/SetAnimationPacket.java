package com.nyfaria.powersofspite.packets.s2c;

import com.nyfaria.powersofspite.client.ClientUtils;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record SetAnimationPacket(UUID player, String animation, boolean force) {

    public static SetAnimationPacket decode(FriendlyByteBuf buf) {
        return new SetAnimationPacket(buf.readUUID(), buf.readUtf(), buf.readBoolean());
    }
    public static void handle(PacketContext<SetAnimationPacket> ctx){
        if(Side.CLIENT.equals(ctx.side())){
            ClientUtils.setPlayerAnimation(ctx.message().player(), ctx.message().animation(), ctx.message().force());
        }
    }
    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(player);
        buf.writeUtf(animation);
        buf.writeBoolean(force);
    }
}

package com.nyfaria.powersofspite.packets.c2s;

import com.nyfaria.powersofspite.client.AnimationHandler;
import com.nyfaria.powersofspite.utils.MovementKey;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;

public record OnMovementPacket(MovementKey movementKey) {
    public static OnMovementPacket decode(FriendlyByteBuf buf) {
        return new OnMovementPacket(buf.readEnum(MovementKey.class));
    }
    public static void handle(PacketContext<OnMovementPacket> buf){
        if(Side.SERVER.equals(buf.side())){
            AnimationHandler.handleMovementAnimations(buf.sender(), buf.message().movementKey());
        }
    }
    public void encode(FriendlyByteBuf buf){
        buf.writeEnum(movementKey);
    }
}

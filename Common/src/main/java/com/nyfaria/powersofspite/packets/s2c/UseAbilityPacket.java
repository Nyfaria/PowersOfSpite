package com.nyfaria.powersofspite.packets.s2c;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public record UseAbilityPacket(int abilityId, int action) {
    public static final ResourceLocation LOCATION = SpiteConstants.modLoc("set_cosmetic");


    public static UseAbilityPacket decode(FriendlyByteBuf buf) {
        return new UseAbilityPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(PacketContext<UseAbilityPacket> ctx) {
        if (!Side.CLIENT.equals(ctx.side())) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(ctx.sender());
            Ability ability = holder.getActiveAbility(ctx.message().abilityId());
            if (ability instanceof Active) {
                if (GLFW.GLFW_PRESS == ctx.message().action()) {
                    ((Active) ability).onUse(ctx.sender());
                } else if (GLFW.GLFW_RELEASE == ctx.message().action()) {
                    ((Active) ability).onRelease(ctx.sender());
                }
            }
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(abilityId);
        buf.writeInt(action);
    }
}

package com.nyfaria.thegifted.packets.s2c;

import com.nyfaria.thegifted.Constants;
import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.ability.api.Active;
import com.nyfaria.thegifted.ability.api.Toggle;
import com.nyfaria.thegifted.cap.AbilityHolder;
import com.nyfaria.thegifted.platform.Services;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public record UseAbilityPacket(int abilityId, int action) {
    public static final ResourceLocation LOCATION = Constants.modLoc("set_cosmetic");


    public static UseAbilityPacket decode(FriendlyByteBuf buf) {
        return new UseAbilityPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(PacketContext<UseAbilityPacket> ctx) {
        if (!Side.CLIENT.equals(ctx.side())) {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(ctx.sender());
            Ability ability = holder.getAbility(ctx.message().abilityId());
            if (ability instanceof Active) {
                if (ability instanceof Toggle) {
                    if(holder.isTicking(ability)) {
                        holder.removeTickingAbility((Toggle) ability);
                    } else {
                        holder.addTickingAbility((Toggle) ability, ctx.sender().level().getGameTime());
                    }
                }
                if(GLFW.GLFW_PRESS == ctx.message().action()) {
                    ((Active) ability).onUse(ctx.sender(),holder.getStacks(ability));
                } else if (GLFW.GLFW_RELEASE == ctx.message().action()) {
                    ((Active) ability).onRelease(ctx.sender(),holder.getStacks(ability));
                }
            }
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(abilityId);
        buf.writeInt(action);
    }
}

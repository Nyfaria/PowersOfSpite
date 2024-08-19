package com.nyfaria.thegifted.event;

import com.nyfaria.thegifted.init.AbilityInit;
import com.nyfaria.thegifted.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonGameEvents {

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            Services.PLATFORM.getAbilityHolder(serverPlayer).addAbility(AbilityInit.FLIGHT.get());
        }
    }
}

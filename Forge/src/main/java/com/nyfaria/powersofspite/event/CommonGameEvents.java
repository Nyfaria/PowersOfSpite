package com.nyfaria.powersofspite.event;

import com.nyfaria.powersofspite.CommonClass;
import com.nyfaria.powersofspite.init.CommandInit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonGameEvents {

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if(event.getEntity()== null)return;
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            CommonClass.onPlayerJoin(serverPlayer);
        }
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandInit.initCommands(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            CommonClass.onRespawn(serverPlayer);
        }
    }
}

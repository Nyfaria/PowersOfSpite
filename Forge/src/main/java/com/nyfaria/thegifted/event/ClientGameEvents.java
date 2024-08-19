package com.nyfaria.thegifted.event;

import com.nyfaria.thegifted.client.CommonClientClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientGameEvents {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        CommonClientClass.flyingTicks();
    }
}

package com.nyfaria.thegifted.event;

import com.nyfaria.thegifted.init.EntityInit;
import com.nyfaria.thegifted.init.KeyBindInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onKeyBinds(RegisterKeyMappingsEvent event) {
        KeyBindInit.initKeyBinds();
    }
}

package com.nyfaria.thegifted;

import com.nyfaria.thegifted.client.CommonClientClass;
import com.nyfaria.thegifted.init.NetworkInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TheGiftedClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            CommonClientClass.flyingTicks();
        });
        NetworkInit.loadClass();
    }
}

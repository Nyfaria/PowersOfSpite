package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.client.CommonClientClass;
import com.nyfaria.powersofspite.init.NetworkInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PowersOfSpiteClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            CommonClientClass.flyingTicks();
        });
        NetworkInit.loadClass();
    }
}

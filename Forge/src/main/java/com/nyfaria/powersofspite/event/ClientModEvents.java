package com.nyfaria.powersofspite.event;

import com.mojang.datafixers.kinds.Const;
import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.init.KeyBindInit;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onKeyBinds(RegisterKeyMappingsEvent event) {
        KeyBindInit.initKeyBinds();
    }
    @SubscribeEvent
    public static void onFMLClient(FMLClientSetupEvent event){
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                Constants.modLoc("animation"),42, ClientModEvents::registerPlayerAnimation
        );
    }
    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }
}

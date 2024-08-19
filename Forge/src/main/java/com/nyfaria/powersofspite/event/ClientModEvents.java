package com.nyfaria.powersofspite.event;

import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.init.KeyBindInit;
import com.nyfaria.powersofspite.platform.Services;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onKeyBinds(RegisterKeyMappingsEvent event) {
        KeyBindInit.initKeyBinds();
    }

    @SubscribeEvent
    public static void onFMLClient(FMLClientSetupEvent event) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                Constants.modLoc("animation"), 42, ClientModEvents::registerPlayerAnimation
        );
    }

    @SubscribeEvent
    public static void onOverlayRegister(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("ability_overlay", (forgeGui, guiGraphics, partialTicks, width, height) -> {
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(Minecraft.getInstance().player);
            if (holder != null) {
                int y = 0;
                for (Ability ability : holder.getAbilities()) {
                    if (ability instanceof Active active) {
                        ResourceLocation texture = Constants.modLoc("textures/power/" + AbilityInit.REG.get().getKey(active).getPath() + ".png");
                        guiGraphics.blit(texture, 0, y, 0, 0, 16, 16, 16, 16);
                        y += 16;
                    }

                }
            }
        });
    }

    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }
}

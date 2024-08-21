package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.client.CommonClientClass;
import com.nyfaria.powersofspite.init.KeyBindInit;
import com.nyfaria.powersofspite.init.NetworkInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.power.api.Power;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class PowersOfSpiteClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            CommonClientClass.flyingTicks();
        });
        NetworkInit.loadClass();
        CommonClientClass.registerEntityRenderers();
        HudRenderCallback.EVENT.register((guiGraphics, deltaTime) -> {
            PowerHolder holder = Services.PLATFORM.getPowerHolder(Minecraft.getInstance().player);
            if (holder != null) {
                int y = 3;
                int keyIndex = 0;
                for (Power ability : holder.getPowers()) {
                    if (ability.hasActiveAbility()) {
                        guiGraphics.blit(SpiteConstants.modLoc("textures/gui/power_slot.png"), 3, y, 0, 0, 20, 20, 20, 20);
                        ResourceLocation texture = SpiteConstants.modLoc("textures/power/" + PowerInit.REG.get().getKey(ability).getPath() + ".png");
                        guiGraphics.blit(texture, 5, y + 2, 0, 0, 16, 16, 16, 16);
                        int fontWidth = Minecraft.getInstance().font.width(KeyBindInit.ABILITY_KEYS.get(keyIndex).key.getDisplayName());
                        guiGraphics.drawString(Minecraft.getInstance().font,KeyBindInit.ABILITY_KEYS.get(keyIndex).key.getDisplayName(), 25 - fontWidth, y + 20 -Minecraft.getInstance().font.lineHeight , 0xFFFFFFFF);
                        y += 22;
                        keyIndex++;
                    }

                }
            }

        });
        CommonClientClass.registerAnimationLayer();
        CommonClientClass.getLayerDefinitions().forEach(
                layerdef -> EntityModelLayerRegistry.registerModelLayer(layerdef.layerLocation(), layerdef::supplier)
        );
        KeyBindInit.initKeyBinds();
        KeyBindInit.ABILITY_KEYS.forEach(KeyBindingHelper::registerKeyBinding);
        KeyBindingHelper.registerKeyBinding(KeyBindInit.OPEN_SCREEN);

    }

}

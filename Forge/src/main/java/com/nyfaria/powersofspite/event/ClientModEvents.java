package com.nyfaria.powersofspite.event;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.client.CommonClientClass;
import com.nyfaria.powersofspite.init.KeyBindInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
        KeyBindInit.ABILITY_KEYS.forEach(event::register);
        event.register(KeyBindInit.OPEN_SCREEN);
    }

    @SubscribeEvent
    public static void onFMLClient(FMLClientSetupEvent event) {
        CommonClientClass.registerAnimationLayer();
    }
    @SubscribeEvent
    public static void onEntityRenderLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CommonClientClass.getLayerDefinitions().forEach(
                layerdef -> event.registerLayerDefinition(layerdef.layerLocation(), layerdef::supplier)
        );
    }
    @SubscribeEvent
    public static void onOverlayRegister(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("ability_overlay", (forgeGui, guiGraphics, partialTicks, width, height) -> {
            PowerHolder holder = Services.PLATFORM.getPowerHolder(Minecraft.getInstance().player);
            if (holder != null) {
                int y = 3;
                int keyIndex = 0;
                for (Power ability : holder.getPowers()) {
                    if (ability.hasActive()) {
                        guiGraphics.blit(SpiteConstants.modLoc("textures/gui/power_slot.png"), 3, y, 0, 0, 20, 20, 20, 20);
                        ResourceLocation texture = SpiteConstants.modLoc("textures/power/" + PowerInit.REG.get().getKey(ability).getPath() + ".png");
                        guiGraphics.blit(texture, 5, y + 2, 0, 0, 16, 16, 16, 16);
                        int fontWidth = Minecraft.getInstance().font.width(KeyBindInit.ABILITY_KEYS.get(keyIndex).getKey().getDisplayName());
                        guiGraphics.drawString(Minecraft.getInstance().font,KeyBindInit.ABILITY_KEYS.get(keyIndex).getKey().getDisplayName(), 25 - fontWidth, y + 20 -Minecraft.getInstance().font.lineHeight , 0xFFFFFFFF);
                        y += 22;
                        keyIndex++;
                    }

                }
            }
        });
    }
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        CommonClientClass.registerEntityRenderers();
    }

}

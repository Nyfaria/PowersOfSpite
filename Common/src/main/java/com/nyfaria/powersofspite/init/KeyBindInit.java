package com.nyfaria.powersofspite.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.nyfaria.powersofspite.client.screen.PowerScreen;
import com.nyfaria.powersofspite.packets.s2c.UseAbilityPacket;
import com.nyfaria.powersofspite.power.api.Power;
import commonnetwork.api.Network;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeyBindInit {
    public static List<KeyMapping> ABILITY_KEYS = new ArrayList<>();
    public static KeyMapping OPEN_SCREEN = new KeyMapping("key.powersofspite.open_screen", InputConstants.KEY_V, "key.categories.powersofspite");

    public static void initKeyBinds() {
        for (int i = 0; i < 3; i++) {
            KeyMapping key = new KeyMapping("key.powersofspite.ability_" + i, InputConstants.KEY_NUMPAD0 + i, "key.categories.powersofspite");
            ABILITY_KEYS.add(key);
        }
//        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, key);
//        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, OPEN_SCREEN);
    }

    public static void onKeyInput(int key, int scanCode, int action, int modifiers) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;
        if(mc.screen!=null)
            return;
        if (action != GLFW.GLFW_REPEAT) {
            for (int i = 0; i < ABILITY_KEYS.size(); i++) {
                KeyMapping abilityKey = ABILITY_KEYS.get(i);
                if (isKey(key,scanCode, abilityKey)) {
                    Network.getNetworkHandler().sendToServer(new UseAbilityPacket(i,action));
                }
            }
            if(didPress(key, scanCode, action, OPEN_SCREEN)) {
                mc.setScreen(new PowerScreen());
            }
        }
    }

    private static boolean didPress(int key, int scanCode, int action, KeyMapping keyBinding) {
        return action == GLFW.GLFW_PRESS && isKey(key,scanCode, keyBinding);
    }
    private static boolean isKey(int key, int scanCode, KeyMapping keyBinding) {
        return keyBinding.matches(key, scanCode);
    }
}

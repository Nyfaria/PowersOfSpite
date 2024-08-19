package com.nyfaria.powersofspite.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.nyfaria.powersofspite.packets.s2c.UseAbilityPacket;
import commonnetwork.api.Network;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeyBindInit {
    public static List<KeyMapping> ABILITY_KEYS = new ArrayList<>();

    public static void initKeyBinds() {
        for (int i = 0; i < 3; i++) {
            KeyMapping key = new KeyMapping("key.powersofspite.ability_" + i, InputConstants.KEY_NUMPAD0 + i, "key.categories.powersofspite");
            Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, key);
            ABILITY_KEYS.add(key);
        }
    }

    public static void onKeyInput(int key, int scanCode, int action, int modifiers) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        if (action != GLFW.GLFW_REPEAT) {
            for (int i = 0; i < ABILITY_KEYS.size(); i++) {
                KeyMapping abilityKey = ABILITY_KEYS.get(i);
                if (isKey(key,scanCode, abilityKey)) {
                    Network.getNetworkHandler().sendToServer(new UseAbilityPacket(i,action));
                }
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

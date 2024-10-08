package com.nyfaria.powersofspite.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientUtils {
    public static List<KeyMapping> movementKeys(){
        final Options OPTIONS = Minecraft.getInstance().options;
        List<KeyMapping> keyList = new ArrayList<>();

        keyList.add(OPTIONS.keyUp);
        keyList.add(OPTIONS.keyDown);
        keyList.add(OPTIONS.keyLeft);
        keyList.add(OPTIONS.keyRight);

        return keyList;
    }

    public static boolean isMoving(Vec3 deltaMovement) {
        return !(deltaMovement.x <= 0.1f && deltaMovement.x >= -0.1f &&
                deltaMovement.y <= 0.1f && deltaMovement.y >= -0.1f &&
                deltaMovement.z <= 0.1f && deltaMovement.z >= -0.1f);
    }

    public static Player getPlayerByUUID(UUID uuid) {
        for (Player player : Minecraft.getInstance().level.players()) {
            if (player.getUUID().equals(uuid)) return player;
        }

        return null;
    }
    public static void setPlayerAnimation(UUID playerUUID, String animation, boolean forcePut) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(playerUUID);
        boolean stopAnimation = animation.equals("none");

        if(stopAnimation){
            AnimationHandler.stopAnimation(player);
        } else {
            AnimationHandler.updateAnimation(animation, player, forcePut);
        }
    }
}

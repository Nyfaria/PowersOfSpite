package com.nyfaria.powersofspite.client;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.client.model.PortalModel;
import com.nyfaria.powersofspite.client.renderer.CloneRenderer;
import com.nyfaria.powersofspite.client.renderer.PortalRenderer;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.packets.c2s.OnMovementPacket;
import com.nyfaria.powersofspite.utils.MovementKey;
import commonnetwork.api.Network;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;

public class CommonClientClass {

    private static MovementKey LAST_KEY = MovementKey.NONE;

    public static void flyingTicks() {
        Player player = Minecraft.getInstance().player;

        //If player is pressing a movement key, run his event
        ClientUtils.movementKeys().forEach(keyMapping -> {
            int key = keyMapping.key.getValue();

            //If current keyMapping is being pressed, send the movementKey to server and run event
            MovementKey movementKey = MovementKey.NONE;
            if (keyMapping.isDown()) {
                //Checks what key is being pressed and transform it to ENUM
                final Options options = Minecraft.getInstance().options;

                final int forward = options.keyUp.key.getValue();
                final int backward = options.keyDown.key.getValue();
                final int leftward = options.keyLeft.key.getValue();
                final int rightward = options.keyRight.key.getValue();

                if (key == forward) movementKey = MovementKey.FORWARD;
                if (key == backward) movementKey = MovementKey.BACKWARD;
                if (key == leftward) movementKey = MovementKey.LEFTWARD;
                if (key == rightward) movementKey = MovementKey.RIGHTWARD;

            }
            if(movementKey!=LAST_KEY) {
                runMovementEvent(movementKey, player);
                Network.getNetworkHandler().sendToServer(new OnMovementPacket(movementKey));
                LAST_KEY = movementKey;
            }
        });
    }


    public static void runMovementEvent(MovementKey key, Player player) {
        if (Objects.requireNonNull(key) == MovementKey.FORWARD) {
            if (player.getAbilities().mayfly && player.getAbilities().flying && player.isSprinting()) {
                MovementEvent.forwardMovement(player);
            }
        }
    }
    public static void registerEntityRenderers() {
        EntityRenderers.register(EntityInit.CLONE.get(), (rm) -> new CloneRenderer(rm, true));
        EntityRenderers.register(EntityInit.PORTAL.get(), (rm) -> new PortalRenderer(rm));
    }
    public static List<LayerDefInfo> getLayerDefinitions(){
        return List.of(
                new LayerDefInfo(PortalModel.LAYER_LOCATION, PortalModel.createBodyLayer())
        );
    }
    public static void registerAnimationLayer(){
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                SpiteConstants.modLoc("animation"), 42, CommonClientClass::registerPlayerAnimation
        );
    }
    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }
    public record LayerDefInfo(ModelLayerLocation layerLocation, LayerDefinition supplier) {
    }
}



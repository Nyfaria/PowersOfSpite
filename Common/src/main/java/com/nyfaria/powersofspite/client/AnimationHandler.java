package com.nyfaria.powersofspite.client;

import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.packets.s2c.SetAnimationPacket;
import com.nyfaria.powersofspite.utils.MovementKey;
import commonnetwork.api.Network;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class AnimationHandler {
    static Supplier<AbstractFadeModifier> fadeModifierFactory = () -> AbstractFadeModifier.standardFadeIn(5, Ease.INOUTSINE);
    public static ConcurrentHashMap<UUID, String> currentPlayingAnimation = new ConcurrentHashMap<>();
    public static void setAnimation(String animationName, Player player) {
        if (player == null || player.level().isClientSide) return;
        if (AnimationHandler.getPlayingAnimation(player).equals(animationName)) return;
        Network.getNetworkHandler().sendToClientsLoadingPos(new SetAnimationPacket(player.getUUID(),animationName, true),(ServerLevel) player.level(),player.blockPosition());
        currentPlayingAnimation.put(player.getUUID(), animationName);
    }
    public static void stopAnimation(Player player) {
        ModifierLayer<IAnimation> modifierLayer = getModifiedLayer(player);
        currentPlayingAnimation.remove(player.getUUID());

        modifierLayer.replaceAnimationWithFade(fadeModifierFactory.get(), null);
    }
    public static String getPlayingAnimation(Player player) {
        if (currentPlayingAnimation.containsKey(player.getUUID())) {
            return currentPlayingAnimation.get(player.getUUID());
        }

        return "null";
    }
    public static ModifierLayer<IAnimation> getModifiedLayer(Player player) {
        IAnimation iAnimation = PlayerAnimationAccess.
                getPlayerAssociatedData((AbstractClientPlayer) player).get(Constants.modLoc( "animation"));


        if (iAnimation != null && iAnimation instanceof ModifierLayer<?>) {
            return (ModifierLayer<IAnimation>) iAnimation;
        }

        return null;
    }
    public static void updateAnimation(String animationName, Player player, boolean forcePut) {
        ModifierLayer<IAnimation> modifierLayer = getModifiedLayer(player);
        ResourceLocation animationRL = Constants.modLoc(animationName);
        KeyframeAnimationPlayer newAnimation = new KeyframeAnimationPlayer(Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(animationRL)));
        boolean replaceFromNothing = !currentPlayingAnimation.containsKey(player.getUUID());

        //If player isn't running an animation, OR the currentAnimation is not equal to newAnimation, change the animation
        //If forcePut is active put it whatever animation is running
        if (forcePut || !currentPlayingAnimation.containsKey(player.getUUID()) || !currentPlayingAnimation.get(player.getUUID()).equals(animationName)) {
            modifierLayer.replaceAnimationWithFade(fadeModifierFactory.get(), newAnimation, replaceFromNothing);
        }

        //Remove previous animation and sets new anim
        currentPlayingAnimation.replace(player.getUUID(), animationName);
    }

    public static void handleMovementAnimations(Player player, MovementKey movementKey){
        if(player.level().isClientSide){
            return;
        }
        if(player.getAbilities().flying){
            AnimationHandler.setAnimation(player.isSprinting() ? "fast_flight" : "flight", player);
        } else {
            AnimationHandler.setAnimation("none", player);
        }

    }
}

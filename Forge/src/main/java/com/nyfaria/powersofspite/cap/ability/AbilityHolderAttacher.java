package com.nyfaria.powersofspite.cap.ability;

import com.nyfaria.powersofspite.Constants;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public class AbilityHolderAttacher extends CapabilityAttacher {
    public static final Capability<AbilityHolderForge> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation LOCATION = new ResourceLocation(Constants.MODID, "ability");
    private static final Class<AbilityHolderForge> CAPABILITY_CLASS = AbilityHolderForge.class;

    @SuppressWarnings("ConstantConditions")
    public static AbilityHolderForge getHolderUnwrap(Player entity) {
        return getHolder(entity).orElse(null);
    }

    public static LazyOptional<AbilityHolderForge> getHolder(Player entity) {
        return entity.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player entity) {
        genericAttachCapability(event, new AbilityHolderForge(entity), CAPABILITY, LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(AbilityHolderAttacher::attach, AbilityHolderAttacher::getHolder,true);
    }
}

package com.nyfaria.powersofspite.cap.power;

import com.nyfaria.powersofspite.SpiteConstants;
import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpiteConstants.MODID)
public class PowerHolderAttacher extends CapabilityAttacher {
    public static final Capability<PowerHolderForge> CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation LOCATION = new ResourceLocation(SpiteConstants.MODID, "example");
    private static final Class<PowerHolderForge> CAPABILITY_CLASS = PowerHolderForge.class;

    @SuppressWarnings("ConstantConditions")
    public static PowerHolderForge getHolderUnwrap(Player entity) {
        return getHolder(entity).orElse(null);
    }

    public static LazyOptional<PowerHolderForge> getHolder(Player entity) {
        return entity.getCapability(CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player entity) {
        genericAttachCapability(event, new PowerHolderForge(entity), CAPABILITY, LOCATION);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(PowerHolderAttacher::attach, PowerHolderAttacher::getHolder,true);
    }
}

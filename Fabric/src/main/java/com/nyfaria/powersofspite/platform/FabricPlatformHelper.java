package com.nyfaria.powersofspite.platform;

import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.cap.AbilityHolderAttacher;
import com.nyfaria.powersofspite.platform.services.IPlatformHelper;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public PowerHolder getPowerHolder(Player player) {
        return AbilityHolderAttacher.POWER_HOLDER.get(player);
    }

    @Override
    public AbilityHolder getAbilityHolder(Player player) {
        return AbilityHolderAttacher.ABILITY_HOLDER.get(player);
    }

    @Override
    public Attribute getSwimSpeedAttribute() {
        return AdditionalEntityAttributes.WATER_SPEED;
    }
}

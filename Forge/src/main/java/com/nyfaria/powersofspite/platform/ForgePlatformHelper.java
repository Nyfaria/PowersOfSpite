package com.nyfaria.powersofspite.platform;

import com.nyfaria.powersofspite.cap.power.PowerHolderAttacher;
import com.nyfaria.powersofspite.platform.services.IPlatformHelper;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.cap.ability.AbilityHolderAttacher;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public PowerHolder getPowerHolder(Player player) {
        return PowerHolderAttacher.getHolderUnwrap(player);
    }

    @Override
    public AbilityHolder getAbilityHolder(Player player) {
        return AbilityHolderAttacher.getHolderUnwrap(player);
    }

    @Override
    public Attribute getSwimSpeedAttribute() {
        return ForgeMod.SWIM_SPEED.get();
    }
}
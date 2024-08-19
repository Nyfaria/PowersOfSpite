package com.nyfaria.thegifted.platform;

import com.nyfaria.thegifted.cap.AbilityHolder;
import com.nyfaria.thegifted.cap.PowerHolder;
import com.nyfaria.thegifted.cap.ability.AbilityHolderAttacher;
import com.nyfaria.thegifted.cap.power.PowerHolderAttacher;
import com.nyfaria.thegifted.platform.services.IPlatformHelper;
import net.minecraft.world.entity.player.Player;
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
}
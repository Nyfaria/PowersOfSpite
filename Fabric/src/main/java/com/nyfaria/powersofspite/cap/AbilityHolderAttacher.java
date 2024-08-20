package com.nyfaria.powersofspite.cap;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.cap.ability.AbilityHolderFabric;
import com.nyfaria.powersofspite.cap.power.PowerHolderFabric;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class AbilityHolderAttacher implements EntityComponentInitializer {
    public static final ComponentKey<AbilityHolderFabric> ABILITY_HOLDER = ComponentRegistryV3.INSTANCE.getOrCreate(SpiteConstants.modLoc( "ability_holder"), AbilityHolderFabric.class);
    public static final ComponentKey<PowerHolderFabric> POWER_HOLDER = ComponentRegistryV3.INSTANCE.getOrCreate(SpiteConstants.modLoc( "power_holder"), PowerHolderFabric.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ABILITY_HOLDER, AbilityHolderFabric::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(POWER_HOLDER, PowerHolderFabric::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}

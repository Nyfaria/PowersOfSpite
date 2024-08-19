package com.nyfaria.thegifted.init;

import com.nyfaria.thegifted.Constants;
import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.ability.api.NoneAbility;
import com.nyfaria.thegifted.ability.passive.FlightPassive;
import com.nyfaria.thegifted.registration.RegistrationProvider;
import com.nyfaria.thegifted.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class AbilityInit {
    public static ResourceKey<Registry<Ability>> ABILITY_KEY = ResourceKey.createRegistryKey(Constants.modLoc("ability"));
    public static RegistrationProvider<Ability> ABILITIES = RegistrationProvider.get(ABILITY_KEY, Constants.MODID);
    public static Supplier<Registry<Ability>> REG = ABILITIES.registryBuilder().build();
    public static RegistryObject<Ability> FLIGHT = ABILITIES.register("flight", FlightPassive::new);
    public static RegistryObject<Ability> NONE = ABILITIES.register("none", NoneAbility::new);


    public static void loadClass() {
    }
}

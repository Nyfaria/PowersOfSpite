package com.nyfaria.powersofspite.init;

import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.ability.active.SpeedActive;
import com.nyfaria.powersofspite.ability.active.SwimSpeedActive;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.ability.api.Passive;
import com.nyfaria.powersofspite.ability.passive.DamageReductionPassive;
import com.nyfaria.powersofspite.ability.passive.StrengthPassive;
import com.nyfaria.powersofspite.ability.api.NoneAbility;
import com.nyfaria.powersofspite.ability.passive.FlightPassive;
import com.nyfaria.powersofspite.ability.passive.WaterBreathingPassive;
import com.nyfaria.powersofspite.registration.RegistrationProvider;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class AbilityInit {
    public static ResourceKey<Registry<Ability>> ABILITY_KEY = ResourceKey.createRegistryKey(Constants.modLoc("ability"));
    public static RegistrationProvider<Ability> ABILITIES = RegistrationProvider.get(ABILITY_KEY, Constants.MODID);
    public static Supplier<Registry<Ability>> REG = ABILITIES.registryBuilder().build();
    public static RegistryObject<Ability> FLIGHT = ABILITIES.register("flight", FlightPassive::new);
    public static RegistryObject<Ability> WATER_BREATHING = ABILITIES.register("water_breathing", WaterBreathingPassive::new);
    public static RegistryObject<Ability> SUPER_STRENGTH = ABILITIES.register("super_strength", StrengthPassive::new);
    public static RegistryObject<Ability> SUPER_SPEED = ABILITIES.register("super_speed", SpeedActive::new);
    public static RegistryObject<Ability> SWIM_SPEED = ABILITIES.register("swim_speed", SwimSpeedActive::new);
    public static RegistryObject<Ability> HALF_DAMAGE = ABILITIES.register("half_damage", DamageReductionPassive::new);
    public static RegistryObject<Ability> BOUNCE = ABILITIES.register("bound", Passive::new);



    public static RegistryObject<Ability> NONE = ABILITIES.register("none", NoneAbility::new);


    public static void loadClass() {
    }
}

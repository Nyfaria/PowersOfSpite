package com.nyfaria.powersofspite.init;

import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.power.ElasticityPower;
import com.nyfaria.powersofspite.power.FlightPower;
import com.nyfaria.powersofspite.power.GhostPower;
import com.nyfaria.powersofspite.power.InvisibilityPower;
import com.nyfaria.powersofspite.power.KeenSightPower;
import com.nyfaria.powersofspite.power.NonePower;
import com.nyfaria.powersofspite.power.SuperSpeedPower;
import com.nyfaria.powersofspite.power.SuperStrengthPower;
import com.nyfaria.powersofspite.power.TritonPower;
import com.nyfaria.powersofspite.power.api.Power;
import com.nyfaria.powersofspite.registration.RegistrationProvider;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class PowerInit {
    public static ResourceKey<Registry<Power>> POWER_KEY = ResourceKey.createRegistryKey(Constants.modLoc("power"));
    public static RegistrationProvider<Power> POWERS = RegistrationProvider.get(POWER_KEY, Constants.MODID);
    public static Supplier<Registry<Power>> REG = POWERS.registryBuilder().build();
    public static RegistryObject<Power> SUPER_SPEED = POWERS.register("super_speed", SuperSpeedPower::new);
    public static RegistryObject<Power> FLIGHT = POWERS.register("flight", FlightPower::new);
    //todo: fix?
    public static RegistryObject<Power> ELASTICITY = POWERS.register("elasticity", ElasticityPower::new);
    public static RegistryObject<Power> SUPER_STRENGTH = POWERS.register("super_strength", SuperStrengthPower::new);
    public static RegistryObject<Power> GHOST = POWERS.register("ghost", GhostPower::new);
    public static RegistryObject<Power> INVISIBILITY = POWERS.register("invisibility", InvisibilityPower::new);
    public static RegistryObject<Power> KEEN_SIGHT = POWERS.register("keen_sight", KeenSightPower::new);
    public static RegistryObject<Power> TRITON = POWERS.register("triton", TritonPower::new);

    //    public static RegistryObject<Power> TELEPORTATION = POWERS.register("teleportation", TeleportationPower::new);

    public static final RegistryObject<Power> NONE = POWERS.register("none", NonePower::new);


    public static void loadClass() {
    }
}

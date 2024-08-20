package com.nyfaria.powersofspite.init;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.entity.CloneEntity;
import com.nyfaria.powersofspite.entity.PortalEntity;
import com.nyfaria.powersofspite.registration.RegistrationProvider;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EntityInit {
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, SpiteConstants.MODID);
    public static final List<AttributesRegister<?>> attributeSuppliers = new ArrayList<>();

    public static final RegistryObject<EntityType<CloneEntity>> CLONE = registerEntity("clone", ()->EntityType.Builder.of(CloneEntity::new, MobCategory.CREATURE), CloneEntity::createAttributes);
    public static final RegistryObject<EntityType<PortalEntity>> PORTAL = registerEntity("portal", ()->EntityType.Builder.of(PortalEntity::new, MobCategory.MISC));
    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, Supplier<EntityType.Builder<T>> supplier) {
        return ENTITIES.register(name, () -> supplier.get().build(SpiteConstants.MODID + ":" + name));
    }

    private static <T extends LivingEntity> RegistryObject<EntityType<T>> registerEntity(String name, Supplier<EntityType.Builder<T>> supplier,
                                                                                         Supplier<AttributeSupplier.Builder> attributeSupplier) {
        RegistryObject<EntityType<T>> entityTypeSupplier = registerEntity(name, supplier);
        attributeSuppliers.add(new AttributesRegister<>(entityTypeSupplier, attributeSupplier));
        return entityTypeSupplier;
    }

    public static void loadClass() {
    }


    public record AttributesRegister<E extends LivingEntity>(Supplier<EntityType<E>> entityTypeSupplier, Supplier<AttributeSupplier.Builder> factory) {}
}

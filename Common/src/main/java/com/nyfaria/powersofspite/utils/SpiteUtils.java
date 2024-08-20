package com.nyfaria.powersofspite.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class SpiteUtils {
    public static HitResult scanLineOfSightHit(LivingEntity shooter, float range) {
        Predicate<Entity> filter = e -> true;
        Vec3 eyePos = shooter.getEyePosition(1);
        Vec3 lookDirection = shooter.getLookAngle();
        Vec3 traceVec = eyePos.add(lookDirection.scale(range));

        HitResult result = shooter.level().clip(new ClipContext(eyePos, traceVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, shooter));
        Vec3 resultVec = traceVec;
        if (result.getType() != HitResult.Type.MISS) {
            resultVec = result.getLocation();
        }

        AABB box = new AABB(eyePos, resultVec);
        HitResult projectileResult = ProjectileUtil.getEntityHitResult(shooter.level(), shooter, eyePos, resultVec, box, filter);
        if (projectileResult != null) {
            result = projectileResult;
        }
        return result;
    }
}

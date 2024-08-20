package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.utils.SpiteUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportationActive extends Active {
    @Override
    public void onUse(Player player) {
        HitResult result = SpiteUtils.scanLineOfSightHit(player, 20);
        BlockPos pos;
        if (result instanceof BlockHitResult blockHitResult) {
            pos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        } else {
            pos = BlockPos.containing(result.getLocation());
        }
        Vec3 position = Vec3.atBottomCenterOf(pos);
        player.randomTeleport(position.x, position.y, position.z,true);
        player.level().playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 0.5f);
    }
}

package com.nyfaria.powersofspite.entity;

import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class PortalEntity extends Entity {
    private UUID owner;

    public PortalEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    protected void defineSynchedData() {
    }


    public Player getOwner() {
        return level().getPlayerByUUID(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= 20 * 60 * 10) {
            discard();
        }
        if (level().isClientSide) return;
        if (tickCount % 10 == 0) {
            level().getEntities(this, getBoundingBox()).forEach(
                    entity -> {
                        if (getOwner() != null) {
                            if (getOwner().isAlive()) {
                                AbilityHolder holder = Services.PLATFORM.getAbilityHolder(getOwner());
                                if (holder != null) {
                                    if (!entity.isOnPortalCooldown()) {
                                        BlockPos otherPortal = holder.getOtherPortalPos(getUUID());
                                        if (otherPortal != BlockPos.ZERO) {
                                            entity.teleportTo(otherPortal.getX(), otherPortal.getY(), otherPortal.getZ());
                                            entity.setPortalCooldown(200);
                                        }
                                    }
                                }
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        AbilityHolder holder = Services.PLATFORM.getAbilityHolder(getOwner());
        if (holder != null) {
            holder.removePortal(getUUID());
        }
        super.remove(pReason);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putUUID("owner", owner);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        owner = pCompound.getUUID("owner");
    }

    public void setOwner(UUID uuid) {
        owner = uuid;
    }

}

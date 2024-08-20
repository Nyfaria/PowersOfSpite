package com.nyfaria.powersofspite.cap;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.entity.PortalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public interface AbilityHolder {
    List<Ability> getAbilities();

    List<Pair<Ability, Long>> getTickingAbilities();

    void setAbility(int slot, Ability ability, boolean update);

    Ability getAbility(int slot);

    void removeAbility(int slot, boolean update);

    void clearAbilities(boolean update);

    boolean hasAbility(Ability ability);

    void addAbility(Ability ability, boolean update);

    void removeAbility(Ability ability, boolean update);

    Player getPlayer();

    void addTickingAbility(Ability ability, long tick);

    void removeTickingAbility(Ability ability);

    boolean isTicking(Ability ability);

    Ability getActiveAbility(int slot);

    void addPortal(UUID portal, BlockPos pos);

    void removePortal(UUID portal);

    default PortalEntity getPortal(UUID portal) {
        if (getPlayer().level().isClientSide) return null;
        if (getPortalInfo().containsPortal(portal)) return null;
        return (PortalEntity) ((ServerLevel) getPlayer().level()).getEntity(portal);
    }

    default PortalEntity getPortal(int portal) {
        return getPortal(getPortalInfo().portal1);
    }

    PortalInfo getPortalInfo();

    default void tick() {
        if (getPlayer().level().isClientSide) return;
        for (Pair<Ability, Long> tickingAbility : getTickingAbilities()) {
            if (tickingAbility.getFirst() == null)
                continue;
            tickingAbility.getFirst().onTick(getPlayer(), getPlayer().level().getGameTime() - tickingAbility.getSecond());
        }

    }

    CompoundTag save();

    void load(CompoundTag holder);

    void addAll(List<Ability> abilities, boolean update);

    default BlockPos getOtherPortalPos(UUID uuid){
        if(getPortalInfo().portal1 == uuid){
            return getPortalInfo().pos2;
        }
        if(getPortalInfo().portal2 == uuid){
            return getPortalInfo().pos1;
        }
        return BlockPos.ZERO;
    }

    void updateTracking();

    record PortalInfo(UUID portal1, BlockPos pos1, UUID portal2, BlockPos pos2, int lastPortal) {
        public PortalInfo(){
            this(null, BlockPos.ZERO, null, BlockPos.ZERO, 0);
        }
        public PortalInfo withPortal1(UUID portal1, BlockPos pos1) {
            return new PortalInfo(portal1, pos1, portal2, pos2, lastPortal);
        }

        public PortalInfo withPortal2(UUID portal2, BlockPos pos2) {
            return new PortalInfo(portal1, pos1, portal2, pos2, lastPortal);
        }

        public PortalInfo withLastPortal(int lastPortal) {
            return new PortalInfo(portal1, pos1, portal2, pos2, lastPortal);
        }

        public PortalInfo withoutPortal(UUID portal) {
            if (portal1.equals(portal)) {
                return new PortalInfo(null, BlockPos.ZERO, portal2, pos2, 1);
            } else if (portal2.equals(portal)) {
                return new PortalInfo(portal1, pos1, null, BlockPos.ZERO, 0);
            }
            return this;
        }
        public boolean containsPortal(UUID portal) {
            return portal1.equals(portal) || portal2.equals(portal);
        }
    }

}

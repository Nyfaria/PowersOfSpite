package com.nyfaria.powersofspite.cap.ability;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.ability.api.Passive;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.cap.AbilityHolderAttacher;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.packets.s2c.UpdateAbilityCapPacket;
import commonnetwork.api.Network;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbilityHolderFabric implements AbilityHolder, ComponentV3, Component, CopyableComponent<AbilityHolderFabric>, AutoSyncedComponent {

    private List<Ability> abilities = NonNullList.withSize(9, AbilityInit.NONE.get());
    private List<Pair<Ability, Long>> tickingAbilities = NonNullList.create();
    private Player entity;
    private PortalInfo portalInfo = new PortalInfo();
    private List<UUID> clones = new ArrayList<>();
    public AbilityHolderFabric(Player entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        for (int i = 0; i < this.abilities.size(); i++) {
            String key = tag.getString("ability_" + i);
            Ability power = AbilityInit.REG.get().get(SpiteConstants.loc(key));
            this.abilities.set(i, power);
        }
        this.tickingAbilities.clear();
        CompoundTag tickingTag = tag.getCompound("ticking_abilities");
        for (int j = 0; j < tickingTag.size(); j++) {
            String tickingKey = tickingTag.getString("ticking_ability_" + j);
            if (tickingKey.equals(tickingKey)) {
                Ability tickingAbility = AbilityInit.REG.get().get(SpiteConstants.loc(tickingKey));
                this.tickingAbilities.add(Pair.of(tickingAbility, tickingTag.getLong("ticking_ability_time_" + j)));
            }
        }
        CompoundTag portalTag = tag.getCompound("portalInfo");
        if (portalTag.contains("portal1u")) {
            portalInfo = portalInfo.withPortal1(portalTag.getUUID("portal1u"), BlockPos.of(portalTag.getLong("portal1p")));
        }
        if (portalTag.contains("portal2u")) {
            portalInfo = portalInfo.withPortal2(portalTag.getUUID("portal2u"), BlockPos.of(portalTag.getLong("portal2p")));
        }
        portalInfo = portalInfo.withLastPortal(portalTag.getInt("lastPortal"));
        clones.clear();
        CompoundTag cloneTag = tag.getCompound("clones");
        for (int i = 0; i < cloneTag.size(); i++) {
            clones.add(cloneTag.getUUID("clone_" + i));
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        for (int i = 0; i < this.abilities.size(); i++) {
            Ability ability = this.abilities.get(i);
            tag.putString("ability_" + i, AbilityInit.REG.get().getKey(ability).toString());
        }
        CompoundTag tickingTag = new CompoundTag();
        for (int i = 0; i < this.tickingAbilities.size(); i++) {
            Pair<Ability, Long> pair = this.tickingAbilities.get(i);
            if (pair.getFirst() == null) {
                continue;
            }
            tickingTag.putString("ticking_ability_" + i, AbilityInit.REG.get().getKey(pair.getFirst()).toString());
            tickingTag.putLong("ticking_ability_time_" + i, pair.getSecond());
        }
        if (!tickingTag.isEmpty()) {
            tag.put("ticking_abilities", tickingTag);
        }
        CompoundTag portalTag = new CompoundTag();
        if(portalInfo.portal1() != null){
            portalTag.putUUID("portal1u", portalInfo.portal1());
            portalTag.putLong("portal1p", portalInfo.pos1().asLong());
        }
        if(portalInfo.portal2() != null){
            portalTag.putUUID("portal2u", portalInfo.portal2());
            portalTag.putLong("portal2p", portalInfo.pos2().asLong());
        }
        portalTag.putInt("lastPortal", portalInfo.lastPortal());
        tag.put("portalInfo", portalTag);
        CompoundTag cloneTag = new CompoundTag();
        for (int i = 0; i < clones.size(); i++) {
            cloneTag.putUUID("clone_" + i, clones.get(i));
        }
        tag.put("clones", cloneTag);

    }


    public void updateTracking() {
        AbilityHolderAttacher.ABILITY_HOLDER.sync(entity);
    }


    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public List<Pair<Ability, Long>> getTickingAbilities() {
        return tickingAbilities;
    }

    @Override
    public void setAbility(int slot, Ability ability, boolean update) {
        abilities.set(slot, ability);
        if (abilities.get(slot) instanceof Passive passive) {
            passive.onActivate(getPlayer());
        }
        if (update) {
            updateTracking();
        }
    }

    @Override
    public Ability getAbility(int slot) {
        return abilities.get(slot);
    }

    @Override
    public void removeAbility(int slot, boolean update) {
        if (abilities.get(slot) instanceof Passive passive) {
            passive.onDeactivate(getPlayer());
        }
        abilities.set(slot, AbilityInit.NONE.get());
        if (update) {
            updateTracking();
        }
    }

    @Override
    public void clearAbilities(boolean update) {
        abilities.replaceAll(ability -> {
            if (ability instanceof Passive passive) {
                passive.onDeactivate(getPlayer());
            }
            return AbilityInit.NONE.get();
        });
        if (update) {
            updateTracking();
        }
    }

    @Override
    public boolean hasAbility(Ability ability) {
        return abilities.stream().anyMatch(pair -> pair.equals(ability));
    }

    @Override
    public void addAbility(Ability ability, boolean update) {
        if (!hasAbility(ability)) {
            for (int i = 0; i < abilities.size(); i++) {
                if (abilities.get(i).equals(AbilityInit.NONE.get())) {
                    abilities.set(i, ability);
                    if (ability instanceof Passive passive) {
                        passive.onActivate(getPlayer());
                    }
                    return;
                }
            }
            if (update) {
                updateTracking();
            }
        }
    }

    @Override
    public void removeAbility(Ability ability, boolean update) {
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i).equals(ability)) {
                if (abilities.get(i) instanceof Passive passive) {
                    passive.onDeactivate(getPlayer());
                }
                abilities.set(i, AbilityInit.NONE.get());
            }
        }
        if (update) {
            updateTracking();
        }
    }

    @Override
    public Player getPlayer() {
        return (Player) entity;
    }


    @Override
    public void addTickingAbility(Ability ability, long tick) {
        getTickingAbilities().add(Pair.of(ability, tick));
        updateTracking();
    }

    @Override
    public void removeTickingAbility(Ability ability) {
        getTickingAbilities().removeIf(pair -> pair.getFirst() == ability);
        updateTracking();
    }


    @Override
    public boolean isTicking(Ability ability) {
        return getTickingAbilities().stream().anyMatch(pair -> pair.getFirst() == ability);
    }

    @Override
    public Ability getActiveAbility(int slot) {
        if (abilities.stream().filter(ability -> ability instanceof Active).count() == 0) {
            return AbilityInit.NONE.get();
        }
        if (abilities.stream().filter(ability -> ability instanceof Active).count() <= slot) {
            return AbilityInit.NONE.get();
        }
        return abilities.stream().filter(ability -> ability instanceof Active).toList().get(slot);
    }

    @Override
    public void addPortal(UUID portal, BlockPos pos) {
        int lastPortal = portalInfo.lastPortal();
        if (lastPortal == 0) {
            portalInfo = portalInfo.withPortal1(portal, pos).withLastPortal(1);
        } else {
            portalInfo = portalInfo.withPortal2(portal, pos).withLastPortal(0);
        }
        updateTracking();
    }

    @Override
    public void removePortal(UUID portal) {
        this.portalInfo = getPortalInfo().withoutPortal(portal);
        updateTracking();
    }

    @Override
    public PortalInfo getPortalInfo() {
        return portalInfo;
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        writeToNbt(tag);
        return tag;
    }

    @Override
    public void load(CompoundTag holder) {
        readFromNbt(holder);
    }

    @Override
    public void addAll(List<Ability> abilities, boolean update) {
        for (Ability ability : abilities) {
            addAbility(ability, false);
        }
        if (update) {
            updateTracking();
        }
    }

    @Override
    public List<UUID> getClones() {
        return clones;
    }

    @Override
    public void addClone(UUID clone) {
        clones.add(clone);
        updateTracking();
    }


    public void sendUpdatePacketToPlayer(ServerPlayer serverPlayer) {
        Network.getNetworkHandler().sendToClient(new UpdateAbilityCapPacket(serverPlayer.getUUID(), save()), serverPlayer);
    }

    @Override
    public void copyFrom(AbilityHolderFabric other) {
        this.abilities = other.abilities;
        this.tickingAbilities = other.tickingAbilities;
    }
}

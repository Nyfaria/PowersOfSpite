package com.nyfaria.powersofspite.cap.ability;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.ability.api.Passive;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.packets.s2c.UpdateAbilityCapPacket;
import commonnetwork.api.Network;
import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.List;

public class AbilityHolderForge extends PlayerCapability implements AbilityHolder {

    private List<Ability> abilities = NonNullList.withSize(9, AbilityInit.NONE.get());
    private List<Pair<Ability, Long>> tickingAbilities = NonNullList.create();

    protected AbilityHolderForge(Player entity) {
        super(entity);
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < this.abilities.size(); i++) {
            Ability ability = this.abilities.get(i);
            tag.putString("ability_" + i, AbilityInit.ABILITIES.getRegistry().getKey(ability).toString());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        for (int i = 0; i < this.abilities.size(); i++) {
            String key = nbt.getString("ability_" + i);
            Ability power = AbilityInit.ABILITIES.getRegistry().get(Constants.loc(key));
            this.abilities.set(i, power);
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), AbilityHolderAttacher.LOCATION, this);
    }

    @Override
    public void updateTracking() {
        if (this.entity.level().isClientSide) {
            Network.getNetworkHandler().sendToAllClients(new UpdateAbilityCapPacket(getPlayer().getUUID(), save()), getPlayer().getServer());
        }
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return null;
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
    public void setAbility(int slot, Ability ability) {
        abilities.set(slot, ability);
        if (abilities.get(slot) instanceof Passive passive) {
            passive.onActivate(getPlayer());
        }
        updateTracking();
    }

    @Override
    public Ability getAbility(int slot) {
        return abilities.get(slot);
    }

    @Override
    public void removeAbility(int slot) {
        if (abilities.get(slot) instanceof Passive passive) {
            passive.onDeactivate(getPlayer());
        }
        abilities.set(slot, AbilityInit.NONE.get());
        updateTracking();
    }

    @Override
    public void clearAbilities() {
        abilities = NonNullList.withSize(3, AbilityInit.NONE.get());
        updateTracking();
    }

    @Override
    public boolean hasAbility(Ability ability) {
        return abilities.stream().anyMatch(pair -> pair.equals(ability));
    }

    @Override
    public void addAbility(Ability ability) {
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
            updateTracking();
        }
    }

    @Override
    public void removeAbility(Ability ability) {
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i).equals(ability)) {
                if (abilities.get(i) instanceof Passive passive) {
                    passive.onDeactivate(getPlayer());
                }
                abilities.set(i, AbilityInit.NONE.get());
            }
        }
        updateTracking();
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
        getTickingAbilities().removeIf(pair -> pair.getFirst()==ability);
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
    public CompoundTag save() {
        return serializeNBT(false);
    }

    @Override
    public void load(CompoundTag holder) {
        deserializeNBT(holder, false);
    }

    @Override
    public void addAll(List<Ability> abilities) {
        for (Ability ability : abilities) {
            addAbility(ability);
        }
    }

    @Override
    public void sendUpdatePacketToPlayer(ServerPlayer serverPlayer) {
        Network.getNetworkHandler().sendToClient(new UpdateAbilityCapPacket(serverPlayer.getUUID(), save()), serverPlayer);
    }

}
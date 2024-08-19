package com.nyfaria.thegifted.cap.ability;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.thegifted.Constants;
import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.ability.api.Passive;
import com.nyfaria.thegifted.ability.api.Toggle;
import com.nyfaria.thegifted.cap.AbilityHolder;
import com.nyfaria.thegifted.init.AbilityInit;
import com.nyfaria.thegifted.packets.s2c.UpdateAbilityCapPacket;
import com.nyfaria.thegifted.packets.s2c.UpdatePowerCapPacket;
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

    private List<Pair<Ability, Integer>> abilities = NonNullList.withSize(20, Pair.of(AbilityInit.NONE.get(),0));
    private List<Pair<Toggle, Long>> tickingAbilities = NonNullList.create();

    protected AbilityHolderForge(Player entity) {
        super(entity);
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < this.abilities.size(); i++) {
            Pair<Ability,Integer> abilityPair = this.abilities.get(i);
            CompoundTag abilityTag = new CompoundTag();
            abilityTag.putString("ability", AbilityInit.REG.get().getKey(abilityPair.getFirst()).toString());
            abilityTag.putInt("level", abilityPair.getSecond());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        for (int i = 0; i < this.abilities.size(); i++) {
            CompoundTag abilityTag = nbt.getCompound("ability_" + i);
            Ability ability = AbilityInit.REG.get().get(Constants.loc(abilityTag.getString("ability")));
            int level = abilityTag.getInt("level");
            this.abilities.set(i, Pair.of(ability, level));
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), AbilityHolderAttacher.LOCATION, this);
    }

    @Override
    public void updateTracking() {
        if (this.entity.level().isClientSide){
            Network.getNetworkHandler().sendToAllClients(new UpdateAbilityCapPacket(getPlayer().getUUID(),save()),getPlayer().getServer());
        }
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return null;
    }

    @Override
    public List<Pair<Ability, Integer>> getAbilities() {
        return abilities;
    }

    @Override
    public List<Pair<Toggle, Long>> getTickingAbilities() {
        return tickingAbilities;
    }

    @Override
    public void setAbility(int slot, Ability ability) {
        abilities.set(slot, Pair.of(ability,1));
        if(abilities.get(slot).getFirst() instanceof Passive passive){
            passive.onActivate(getPlayer(),abilities.get(slot).getSecond());
        }
        updateTracking();
    }

    @Override
    public Ability getAbility(int slot) {
        return abilities.get(slot).getFirst();
    }

    @Override
    public void removeAbility(int slot) {
        if(abilities.get(slot).getFirst() instanceof Passive passive){
            passive.onDeactivate(getPlayer(),abilities.get(slot).getSecond());
        }
        abilities.set(slot, Pair.of(AbilityInit.NONE.get(),0));
        updateTracking();
    }

    @Override
    public void clearAbilities() {
        abilities = NonNullList.withSize(6, Pair.of(AbilityInit.NONE.get(),0));
        updateTracking();
    }

    @Override
    public boolean hasAbility(Ability ability) {
        return abilities.stream().anyMatch(pair -> pair.getFirst().equals(ability));
    }

    @Override
    public void addAbility(Ability ability) {
        if(hasAbility(ability) && ability.getMaxStacks() == 1) return;
        if(!hasAbility(ability)) {
            for (int i = 0; i < abilities.size(); i++) {
                if (abilities.get(i).equals(AbilityInit.NONE.get())) {
                    abilities.set(i, Pair.of(ability, 1));
                    if(ability instanceof Passive passive){
                        passive.onActivate(getPlayer(),1);
                    }
                    return;
                }
            }
        } else {
            for (int i = 0; i < abilities.size(); i++) {
                if(abilities.get(i).getFirst().equals(ability)){
                    if(abilities.get(i).getFirst().getMaxStacks() > abilities.get(i).getSecond()){
                        abilities.set(i, Pair.of(ability,abilities.get(i).getSecond() + 1));
                        if(ability instanceof Passive passive){
                            passive.onActivate(getPlayer(),1);
                        }
                        return;
                    }
                }
            }
        }

        updateTracking();
    }

    @Override
    public void removeAbility(Ability ability) {
        for (int i = 0; i < abilities.size(); i++) {
            if(abilities.get(i).getFirst().equals(ability)){
                if(abilities.get(i).getFirst() instanceof Passive passive){
                    passive.onDeactivate(getPlayer(),abilities.get(i).getSecond());
                }
                abilities.set(i, Pair.of(AbilityInit.NONE.get(),0));
            }
        }
        updateTracking();
    }

    @Override
    public Player getPlayer() {
        return (Player) entity;
    }

    @Override
    public void addTickingAbility(Toggle ability, long tick) {
        getTickingAbilities().add(Pair.of(ability,tick));
        updateTracking();
    }

    @Override
    public void removeTickingAbility(Toggle ability) {
        getTickingAbilities().removeIf(pair -> pair.getFirst().equals(ability));
        updateTracking();
    }

    @Override
    public int getStacks(Ability ability) {
        return abilities.stream().filter(pair -> pair.getFirst().equals(ability)).mapToInt(Pair::getSecond).sum();
    }

    @Override
    public boolean isTicking(Ability ability) {
        return tickingAbilities.stream().anyMatch(pair -> pair.getFirst().equals(ability));
    }

    @Override
    public CompoundTag save() {
        return serializeNBT(false);
    }

    @Override
    public void load(CompoundTag holder) {
        load(holder);
    }
    @Override
    public void sendUpdatePacketToPlayer(ServerPlayer serverPlayer) {
        Network.getNetworkHandler().sendToClient(new UpdateAbilityCapPacket(serverPlayer.getUUID(), save()),serverPlayer);
    }

}

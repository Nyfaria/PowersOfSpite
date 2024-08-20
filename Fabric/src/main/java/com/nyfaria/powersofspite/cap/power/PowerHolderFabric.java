package com.nyfaria.powersofspite.cap.power;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.packets.s2c.UpdatePowerCapPacket;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.power.api.Power;
import commonnetwork.api.Network;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PowerHolderFabric  implements ComponentV3, Component, CopyableComponent<PowerHolderFabric>, AutoSyncedComponent, PowerHolder {

    private List<Power> powers = NonNullList.withSize(3, PowerInit.NONE.get());
    private Player entity;

    public PowerHolderFabric(Player entity) {
        this.entity = entity;
    }


    @Override
    public void readFromNbt(CompoundTag tag) {
        for (int i = 0; i < this.powers.size(); i++) {
            String key = tag.getString("power_" + i);
            Power power = PowerInit.POWERS.getRegistry().get(SpiteConstants.loc(key));
            this.powers.set(i, power);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        for (int i = 0; i < this.powers.size(); i++) {
            Power power = this.powers.get(i);
            tag.putString("power_" + i, PowerInit.POWERS.getRegistry().getKey(power).toString());
        }

    }



    public void updateTracking() {
        if (!this.entity.level().isClientSide){
            Network.getNetworkHandler().sendToClientsLoadingPos(new UpdatePowerCapPacket(this.entity.getUUID(),save()), (ServerLevel)this.entity.level(), getPlayer().blockPosition());
        }
    }



    @Override
    public List<Power> getPowers() {
        return powers;
    }

    @Override
    public void setPower(int slot, Power power) {
        powers.set(slot, power);
        power.getAbilities().forEach(ability -> Services.PLATFORM.getAbilityHolder(getPlayer()).addAbility(ability));
    }

    @Override
    public Power getPower(int slot) {
        return powers.get(slot);
    }

    @Override
    public void removePower(int slot) {
        powers.set(slot, PowerInit.NONE.get());
    }

    @Override
    public void clearPowers() {
        AbilityHolder abilityHolder = Services.PLATFORM.getAbilityHolder(getPlayer());
        abilityHolder.clearAbilities();
        powers.replaceAll(ignored -> PowerInit.NONE.get());
        updateTracking();
    }

    @Override
    public boolean hasPower(Power power) {
        return powers.contains(power);
    }

    @Override
    public void addPower(Power power) {
        int slot = powers.indexOf(PowerInit.NONE.get());
        if (slot != -1) {
            powers.set(slot, power);
            Services.PLATFORM.getAbilityHolder(getPlayer()).addAll(power.getAbilities());
            updateTracking();
        }
        evaluateAbilities();
    }

    @Override
    public void removePower(Power power) {
        int slot = powers.indexOf(power);
        if (slot != -1) {
            powers.set(slot, PowerInit.NONE.get());
        }
        evaluateAbilities();
    }

    @Override
    public Player getPlayer() {
        return (Player) entity;
    }


    @Override
    public CompoundTag save() {
        CompoundTag holder = new CompoundTag();
        writeToNbt(holder);
        return holder;
    }

    @Override
    public void load(CompoundTag holder) {
        readFromNbt(holder);
    }

    @Override
    public void copyFrom(PowerHolderFabric other) {
        this.powers = other.powers;
    }
}

package com.nyfaria.powersofspite.cap.power;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.packets.s2c.UpdatePowerCapPacket;
import com.nyfaria.powersofspite.power.api.Power;
import commonnetwork.api.Network;
import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.List;

public class PowerHolderForge extends PlayerCapability implements PowerHolder {

    private List<Power> powers = NonNullList.withSize(3, PowerInit.NONE.get());

    protected PowerHolderForge(Player entity) {
        super(entity);
    }





    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < this.powers.size(); i++) {
            Power power = this.powers.get(i);
            tag.putString("power_" + i, PowerInit.POWERS.getRegistry().getKey(power).toString());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        for (int i = 0; i < this.powers.size(); i++) {
            String key = nbt.getString("power_" + i);
            Power power = PowerInit.POWERS.getRegistry().get(SpiteConstants.loc(key));
            this.powers.set(i, power);
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), PowerHolderAttacher.LOCATION, this);
    }

    @Override
    public void updateTracking() {
        if (!this.entity.level().isClientSide){
            Network.getNetworkHandler().sendToClientsLoadingPos(new UpdatePowerCapPacket(this.entity.getUUID(),save()), (ServerLevel)this.entity.level(), player.blockPosition());
        }
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        return null;
    }

    @Override
    public List<Power> getPowers() {
        return powers;
    }

    @Override
    public void setPower(int slot, Power power) {
        powers.set(slot, power);
        Services.PLATFORM.getAbilityHolder(getPlayer()).addAll(power.getAbilities(),true);
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
        abilityHolder.clearAbilities(true);
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
            updateTracking();
        }
        evaluateAbilities();
    }

    @Override
    public void removePower(Power power) {
        int slot = powers.indexOf(power);
        if (slot != -1) {
            powers.set(slot, PowerInit.NONE.get());
            updateTracking();
        }
        evaluateAbilities();
    }


    @Override
    public Player getPlayer() {
        return (Player) entity;
    }

    @Override
    public void sendUpdatePacketToPlayer(ServerPlayer serverPlayer) {
        Network.getNetworkHandler().sendToClient(new UpdatePowerCapPacket(serverPlayer.getUUID(), save()),serverPlayer);
    }

    @Override
    public CompoundTag save() {
        return serializeNBT(false);
    }

    @Override
    public void load(CompoundTag holder) {
        deserializeNBT(holder,false);
        evaluateAbilities();
    }
}

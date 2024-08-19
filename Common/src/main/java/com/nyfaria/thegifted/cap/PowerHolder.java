package com.nyfaria.thegifted.cap;

import com.nyfaria.thegifted.power.api.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public interface PowerHolder {
    void setPower(int slot, Power power);
    Power getPower(int slot);
    void removePower(int slot);
    void clearPowers();
    boolean hasPower(Power power);
    void addPower(Power power);
    void removePower(Power power);
    Player getPlayer();
    CompoundTag save();

    void load(CompoundTag holder);
}

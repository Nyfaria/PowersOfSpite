package com.nyfaria.powersofspite.cap;

import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface PowerHolder {
    List<Power> getPowers();
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

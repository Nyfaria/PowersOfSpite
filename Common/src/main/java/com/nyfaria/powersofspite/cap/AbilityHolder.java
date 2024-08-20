package com.nyfaria.powersofspite.cap;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.powersofspite.ability.api.Ability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface AbilityHolder {
    List<Ability> getAbilities();
    List<Pair<Ability,Long>> getTickingAbilities();
    void setAbility(int slot, Ability ability);
    Ability getAbility(int slot);
    void removeAbility(int slot);
    void clearAbilities();
    boolean hasAbility(Ability ability);
    void addAbility(Ability ability);
    void removeAbility(Ability ability);
    Player getPlayer();
    void addTickingAbility(Ability ability, long tick);
    void removeTickingAbility(Ability ability);
    boolean isTicking(Ability ability);
    Ability getActiveAbility(int slot);
    default void tick(){
        if(getPlayer().level().isClientSide) return;
        for (Pair<Ability, Long> tickingAbility : getTickingAbilities()) {
            if(tickingAbility.getFirst()== null)
                continue;
            tickingAbility.getFirst().onTick(getPlayer(), getPlayer().level().getGameTime() - tickingAbility.getSecond());
        }
    }

    CompoundTag save();

    void load(CompoundTag holder);

    void addAll(List<Ability> abilities);
}

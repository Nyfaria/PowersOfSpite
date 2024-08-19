package com.nyfaria.thegifted.cap;

import com.mojang.datafixers.util.Pair;
import com.nyfaria.thegifted.ability.api.Ability;
import com.nyfaria.thegifted.ability.api.Toggle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface AbilityHolder {
    List<Pair<Ability,Integer>> getAbilities();
    List<Pair<Toggle,Long>> getTickingAbilities();
    void setAbility(int slot, Ability ability);
    Ability getAbility(int slot);
    void removeAbility(int slot);
    void clearAbilities();
    boolean hasAbility(Ability ability);
    void addAbility(Ability ability);
    void removeAbility(Ability ability);
    Player getPlayer();
    void addTickingAbility(Toggle ability, long tick);
    void removeTickingAbility(Toggle ability);
    int getStacks(Ability ability);
    boolean isTicking(Ability ability);
    default void tick(){
        if(getPlayer().level().isClientSide) return;
        for (Pair<Toggle, Long> tickingAbility : getTickingAbilities()) {
            int level = getAbilities().stream().filter(pair -> pair.getFirst().equals(tickingAbility.getFirst())).mapToInt(Pair::getSecond).sum();
            tickingAbility.getFirst().onTick(getPlayer(), getPlayer().level().getGameTime() - tickingAbility.getSecond(),level);
        }
    }

    CompoundTag save();

    void load(CompoundTag holder);
}

package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.entity.CloneEntity;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

public class CloneActive extends Active {

    private final int count;
    public CloneActive(int count) {
        super();
        this.count = count;
    }


    @Override
    public void onUse(Player player) {
        if(player.level().isClientSide()) return;
        if(!player.isAlive()) return;
        AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
        if(holder == null) return;
        int cloneCount = holder.getCloneCount();
        for(int i = 0; i < count - cloneCount; i++){
            CloneEntity entity =EntityInit.CLONE.get().spawn((ServerLevel) player.level(),player.blockPosition(), MobSpawnType.EVENT);
            entity.tame(player);
            entity.setOrderedToSit(false);
            entity.setCustomName(player.getName());
            holder.addClone(entity.getUUID());
        }
    }
}

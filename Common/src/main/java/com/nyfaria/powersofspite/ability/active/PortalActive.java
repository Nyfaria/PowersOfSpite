package com.nyfaria.powersofspite.ability.active;

import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.entity.PortalEntity;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.utils.SpiteUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;

public class PortalActive extends Active {

    @Override
    public void onUse(Player player) {
        HitResult hitResult = SpiteUtils.scanLineOfSightHit(player, 10);
        if(hitResult != null){
            PortalEntity portalEntity = EntityInit.PORTAL.get().create(player.level());
            portalEntity.setPos(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
            portalEntity.setOwner(player.getUUID());
            player.level().addFreshEntity(portalEntity);
            AbilityHolder holder = Services.PLATFORM.getAbilityHolder(player);
            if(holder != null){
                holder.addPortal(portalEntity.getUUID(), portalEntity.blockPosition());
            }

        }
    }
}

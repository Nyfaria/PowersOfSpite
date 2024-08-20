package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.ability.api.Passive;
import com.nyfaria.powersofspite.cap.AbilityHolder;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.init.BlockInit;
import com.nyfaria.powersofspite.init.CommandInit;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.init.ItemInit;
import com.nyfaria.powersofspite.init.NetworkInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.entity.player.Player;

public class CommonClass {

    public static void init() {
        ItemInit.loadClass();
        BlockInit.loadClass();
        EntityInit.loadClass();
        AbilityInit.loadClass();
        PowerInit.loadClass();
        NetworkInit.loadClass();
        CommandInit.loadClass();
    }


    public static void onPlayerJoin(Player player){
        AbilityHolder abilityHolder = Services.PLATFORM.getAbilityHolder(player);
        abilityHolder.getAbilities().forEach(ability -> {
            if(ability instanceof Passive passive){
                passive.onActivate(player);
            }
        });
    }
    public static void onRespawn(Player player){
        AbilityHolder abilityHolder = Services.PLATFORM.getAbilityHolder(player);
        abilityHolder.getAbilities().forEach(ability -> {
            if(ability instanceof Passive passive){
                passive.onActivate(player);
            }
        });
    }
}
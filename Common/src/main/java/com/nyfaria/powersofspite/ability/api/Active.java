package com.nyfaria.powersofspite.ability.api;

import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.world.entity.player.Player;

public class Active implements Ability {
    public void onUse(Player player){

    }

    public void onRelease(Player player){
        tickingCheck(player);
    }
    private void tickingCheck(Player player){
        if(isTicking()){
            if(Services.PLATFORM.getAbilityHolder(player).isTicking(this)){
                Services.PLATFORM.getAbilityHolder(player).removeTickingAbility(this);
                if(isToggle()) {
                    onToggle(player, false);
                }
            }else{
                Services.PLATFORM.getAbilityHolder(player).addTickingAbility(this, player.level().getGameTime());
                if(isToggle()) {
                    onToggle(player, true);
                }
            }
        }
    }

    public void onToggle(Player player, boolean onOff){

    }
    public boolean isToggle(){
        return false;
    }
    public boolean isTicking(){
        return isToggle() || getMaxTicks() > 0;
    }

    private int getMaxTicks() {
        return 0;
    }
}

package com.nyfaria.powersofspite.item;

import com.nyfaria.powersofspite.ability.api.Active;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.power.api.Power;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SerumSyringeItem extends Item {
    public SerumSyringeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (pUsedHand == InteractionHand.MAIN_HAND) {
                if (Services.PLATFORM.getPowerHolder(pPlayer).getPowers().stream().anyMatch(power -> power == PowerInit.NONE.get())) {
                    pPlayer.startUsingItem(pUsedHand);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
                } else {
                    pPlayer.displayClientMessage(Component.literal("You can only have 3 powers at a time"), true);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            float activeChance = Services.PLATFORM.getAbilityHolder((Player) pLivingEntity).getAbilities().stream().anyMatch(ability -> ability instanceof Active) ? 0.01f : 0.5f;
            List<Power> potentialAbilities;
            if (pLevel.random.nextFloat() < activeChance) {
                potentialAbilities = PowerInit.POWERS.getEntries().stream().filter(powerRO -> !Services.PLATFORM.getPowerHolder((Player) pLivingEntity).hasPower(powerRO.get())).map(RegistryObject::get).toList();
            } else {
                potentialAbilities = PowerInit.POWERS.getEntries().stream().filter(powerRO -> !powerRO.get().hasActive() && !Services.PLATFORM.getPowerHolder((Player) pLivingEntity).hasPower(powerRO.get())).map(RegistryObject::get).toList();
            }
            if (potentialAbilities.isEmpty()) {
                potentialAbilities = PowerInit.POWERS.getEntries().stream().filter(powerRO -> !Services.PLATFORM.getPowerHolder((Player) pLivingEntity).hasPower(powerRO.get())).map(RegistryObject::get).toList();
            }
            if (potentialAbilities.isEmpty()) {
                return pStack.copy();
            }
            Power power = potentialAbilities.get(pLevel.random.nextInt(potentialAbilities.size()));
            Services.PLATFORM.getPowerHolder((Player) pLivingEntity).addPower(power);
        }
        pStack.shrink(1);
        return pStack.copy();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        if (Services.PLATFORM.getEnvironmentName().equals("development")) {
            return 1;
        }
        return 20 * 10;
    }
}

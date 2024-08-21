package com.nyfaria.powersofspite.event;

import com.nyfaria.powersofspite.init.CommandInit;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.init.ItemInit;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {


    @SubscribeEvent
    public static void attribs(EntityAttributeCreationEvent e) {
        EntityInit.attributeSuppliers.forEach(p -> e.put(p.entityTypeSupplier().get(), p.factory().get().build()));
    }
    @SubscribeEvent
    public static void onFMLCommon(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(Items.POTION.getDefaultInstance(), Potions.AWKWARD)),
                    Ingredient.of(ItemInit.RAW_SPITE.get()),
                    ItemInit.CHEMICAL_S.get().getDefaultInstance()
            );
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(ItemInit.CHEMICAL_S.get()),
                    Ingredient.of(Items.DIAMOND_BLOCK),
                    ItemInit.SPITE_SERUM.get().getDefaultInstance());
        });
    }


}

package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.init.CommandInit;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.init.ItemInit;
import io.github.fabricators_of_create.porting_lib.brewing.BrewingRecipeRegistry;
import io.github.fabricators_of_create.porting_lib.core.PortingLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.GenerationStep;

public class PowersOfSpite implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        EntityInit.attributeSuppliers.forEach(
                p -> FabricDefaultAttributeRegistry.register(p.entityTypeSupplier().get(), p.factory().get().build())
        );
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            CommonClass.onPlayerJoin(handler.player);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext, commandSelection) -> {
            CommandInit.initCommands(dispatcher);
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            CommonClass.onRespawn(newPlayer);
        });
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, SpiteConstants.SPITE_ORE_PLACED);
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(Items.POTION.getDefaultInstance(), Potions.AWKWARD)),
                Ingredient.of(ItemInit.RAW_SPITE.get()),
                ItemInit.CHEMICAL_S.get().getDefaultInstance()
        );
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(ItemInit.CHEMICAL_S.get()),
                Ingredient.of(Items.DIAMOND_BLOCK),
                ItemInit.SPITE_SERUM.get().getDefaultInstance());

    }
}

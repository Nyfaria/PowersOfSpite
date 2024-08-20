package com.nyfaria.powersofspite.datagen;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModDataPackProvider::biomeModifiers)
            .add(Registries.CONFIGURED_FEATURE, ModDataPackProvider::configuredFeature)
            .add(Registries.PLACED_FEATURE, ModDataPackProvider::placedFeatures);
    private static final ResourceKey<BiomeModifier> SPITE_ORE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, SpiteConstants.modLoc("spire_ore"));
    public ModDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(SpiteConstants.MODID));
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context) {
        context.register(SPITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(
                        context.lookup(Registries.PLACED_FEATURE).getOrThrow(SpiteConstants.SPITE_ORE_PLACED)
                ), GenerationStep.Decoration.UNDERGROUND_ORES)
        );
    }

    public static void configuredFeature(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(SpiteConstants.SPITE_ORE, new ConfiguredFeature<>(
                        Feature.ORE,
                        new OreConfiguration(
                                List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), BlockInit.SPITE_ORE.get().defaultBlockState())),
                                1,
                                1
                        )
                )
        );


    }

    public static void placedFeatures(BootstapContext<PlacedFeature> context) {
        context.register(SpiteConstants.SPITE_ORE_PLACED, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).get(SpiteConstants.SPITE_ORE).get(),
                        List.of(
                                CountPlacement.of(UniformInt.of(4,10)),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),VerticalAnchor.absolute(-40)),
                                InSquarePlacement.spread(),
                                BiomeFilter.biome()
                        )
                )
        );
    }

}
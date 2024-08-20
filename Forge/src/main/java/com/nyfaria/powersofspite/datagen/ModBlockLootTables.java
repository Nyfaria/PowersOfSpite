package com.nyfaria.powersofspite.datagen;

import com.nyfaria.powersofspite.init.ItemInit;
import com.nyfaria.powersofspite.registration.RegistryObject;
import com.nyfaria.powersofspite.init.BlockInit;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    @Override
    protected void generate() {
        this.getBlockStream().filter(this::shouldDropSelf).forEach(this::dropSelf);
        add(BlockInit.SPITE_ORE.get(),createOreDrop(BlockInit.SPITE_ORE.get(), ItemInit.RAW_SPITE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.getBlockStream().filter(this::shouldGenerateLoot).toList();
    }

    protected Stream<Block> getBlockStream() {
        return BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get);
    }

    protected boolean shouldDropSelf(Block block) {
        return shouldGenerateLoot(block)&& !(block instanceof DropExperienceBlock);
    }

    protected boolean shouldGenerateLoot(Block block) {
        return block.asItem() != Items.AIR;
    }

}

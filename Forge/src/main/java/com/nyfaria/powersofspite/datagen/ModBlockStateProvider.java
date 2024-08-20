package com.nyfaria.powersofspite.datagen;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, SpiteConstants.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        Stream.of(
                        BlockInit.SPITE_ORE
                )
                .map(Supplier::get)
                .forEach(this::simpleCubeBottomTopBlockState);
        //
        // Stream.of(
        //
        // ).map(Supplier::get)
        //         .forEach(this::simpleBlock);

    }

    protected void simpleCubeBottomTopBlockState(Block block) {
        simpleBlock(block, blockCubeTopModel(block));
    }

    protected BlockModelBuilder blockCubeTopModel(Block block) {
        String name = getName(block);
        return models().cubeBottomTop(name, modLoc("block/" + name), modLoc("block/" + name + "_top"), modLoc("block/" + name));
    }

    protected String getName(Block item) {
        return ForgeRegistries.BLOCKS.getKey(item).getPath();
    }
}

package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.cap.ability.AbilityHolderAttacher;
import com.nyfaria.powersofspite.cap.power.PowerHolderAttacher;
import com.nyfaria.powersofspite.datagen.ModBlockStateProvider;
import com.nyfaria.powersofspite.datagen.ModItemModelProvider;
import com.nyfaria.powersofspite.datagen.ModLangProvider;
import com.nyfaria.powersofspite.datagen.ModLootTableProvider;
import com.nyfaria.powersofspite.datagen.ModRecipeProvider;
import com.nyfaria.powersofspite.datagen.ModTagProvider;
import com.nyfaria.powersofspite.datagen.ModSoundProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PowersOfSpite {
    
    public PowersOfSpite() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
        PowerHolderAttacher.register();
        AbilityHolderAttacher.register();
    }

    @SubscribeEvent
    public static void fmlCommon(FMLCommonSetupEvent event){
//        Network.registerPacket()
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();
        generator.addProvider(includeServer, new ModRecipeProvider(packOutput));
        generator.addProvider(includeServer, new ModLootTableProvider(packOutput));
        generator.addProvider(includeServer, new ModSoundProvider(packOutput, existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Blocks(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Items(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeClient, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModLangProvider(packOutput));
    }
}
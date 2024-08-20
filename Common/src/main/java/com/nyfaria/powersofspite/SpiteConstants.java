package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SpiteConstants {

	public static final String MODID = "powersofspite";
	public static final String MOD_NAME = "Powers of Spite";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceKey<ConfiguredFeature<?,?>> SPITE_ORE = FeatureUtils.createKey(SpiteConstants.MODID +":spite_ore");
	public static ResourceKey<PlacedFeature> SPITE_ORE_PLACED = PlacementUtils.createKey(SpiteConstants.MODID +":spite_ore_placed");
	public static Map<String, ResourceLocation> MOD_LOCS_STORE = new HashMap<>();
	public static Map<String, ResourceLocation> MC_LOCS_STORE = new HashMap<>();
	public static Map<String, ResourceLocation> LOCS_STORE = new HashMap<>();


	public static ResourceLocation modLoc(String path){
		return MOD_LOCS_STORE.computeIfAbsent(path, p-> new ResourceLocation(MODID, p));
	}
	public static ResourceLocation mcLoc(String path){

		return MC_LOCS_STORE.computeIfAbsent(path, ResourceLocation::new);
	}
	public static ResourceLocation loc(String path){
		return LOCS_STORE.computeIfAbsent(path, ResourceLocation::new);
	}

	public static String getDescriptionId(Power power) {
		return "power." + MODID + "." +PowerInit.REG.get().getKey(power).getPath() ;
	}
	public static String getPowerDescription(Power power, int line) {
		return "power." + MODID + "." +PowerInit.REG.get().getKey(power).getPath() + ".desc" + line;
	}
	public static String getDescriptionId(Ability power) {
		return "ability." + MODID + "." + AbilityInit.REG.get().getKey(power).getPath() ;
	}
	public static String getAbilityDescription(Ability power, int line) {
		return "ability." + MODID + "." + AbilityInit.REG.get().getKey(power).getPath() + ".desc" + line;
	}
}
package com.nyfaria.powersofspite;

import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MODID = "powersofspite";
	public static final String MOD_NAME = "Powers of Spite";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation modLoc(String path){
		return new ResourceLocation(MODID, path);
	}
	public static ResourceLocation mcLoc(String path){
		return new ResourceLocation("minecraft", path);
	}
	public static ResourceLocation loc(String path){
		return new ResourceLocation(path);
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
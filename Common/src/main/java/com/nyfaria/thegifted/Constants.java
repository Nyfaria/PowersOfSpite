package com.nyfaria.thegifted;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MODID = "thegifted";
	public static final String MOD_NAME = "The Gifted";
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

}
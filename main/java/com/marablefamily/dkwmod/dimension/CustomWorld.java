package com.marablefamily.dkwmod.dimension;

import com.marablefamily.dkwmod.DKWMod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomWorld extends MyWorldBase {

	@Override
	protected int customID() {
		return 2;
	}

	@Override
	protected BiomeGenBase[] customBiomes() {
		BiomeGenBase[] biomes = {
				BiomeGenBase.frozenOcean
		};
		return biomes;
	}
	
	@Override
	protected int customBiomeSize() {
		return 4;
	}

	@Override
	protected String customSkyColor() {
		return "#FFF991";
	}

	@Override
	protected String customFogColor() {
		return "#5CFF8E";
	}

	@Override
	protected String customSunriseSunsetColor() {
		return "#54FF00";
	}

	@Override
	protected float customDaySpeed() {
		return 4.0F;
	}

	@Override
	protected String customCloudColor() {
		return "#35841D";
	}

	@Override
	protected Block getBaseBlock() {
		return Blocks.stone;
	}

}

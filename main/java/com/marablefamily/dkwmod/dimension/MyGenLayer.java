package com.marablefamily.dkwmod.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class MyGenLayer extends GenLayer {

	public MyGenLayer(long seed) {
		super(seed);
	}
	
	public static GenLayer[] makeTheWorld(long seed, BiomeGenBase[] b, int biomeSize) {

		if (biomeSize < 1) biomeSize = 1;
		GenLayer biomes = new MyGenLayerBiomes(1L, b);

		// more GenLayerZoom calls = bigger biomes
		long l = 1000L;
		for (int i = 0; i < biomeSize; i++) {
			biomes = new GenLayerZoom(l, biomes);
			l += 1L;
		}

		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);

		return new GenLayer[] {biomes, genlayervoronoizoom};
	}

}

package com.marablefamily.dkwmod.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class MyGenLayerBiomes extends GenLayer {

	protected BiomeGenBase[] allowedBiomes = {
			BiomeGenBase.frozenOcean};

	public MyGenLayerBiomes(long seed, GenLayer genlayer) {
		super(seed);
		this.parent = genlayer;
	}

	public MyGenLayerBiomes(long seed) {
		super(seed);
	}
	
	public MyGenLayerBiomes(long seed, BiomeGenBase[] biomes) {
		super(seed);
		this.allowedBiomes = biomes;
	}
	
	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width*depth);

		for (int dz=0; dz<depth; dz++)
		{
			for (int dx=0; dx<width; dx++)
			{
				this.initChunkSeed(dx+x, dz+z);
				dest[(dx+dz*width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
			}
		}
		return dest;
	}

}

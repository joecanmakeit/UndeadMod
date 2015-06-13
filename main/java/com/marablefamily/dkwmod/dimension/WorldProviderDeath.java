package com.marablefamily.dkwmod.dimension;

import com.marablefamily.dkwmod.DKWMod;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldProviderDeath extends WorldProvider {
	
	private static BiomeGenBase[] biomes = {
		BiomeGenBase.beach
	};

	@Override
	public String getDimensionName() {
		return "Death World";
	}
	
	public void registerWorldChunkManager() {
		this.dimensionId = DKWMod.deathWorldId;
		this.terrainType = WorldType.DEFAULT;
		this.worldObj.getWorldInfo().setTerrainType(WorldType.DEFAULT);
		this.worldChunkMgr = new WorldChunkManagerDeath(worldObj.getSeed(), WorldType.DEFAULT);
	}

}

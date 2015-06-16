package com.marablefamily.dkwmod;

import java.util.Random;

import com.marablefamily.dkwmod.block.CorruptSoil;
import com.marablefamily.dkwmod.dimension.WorldGenSpike;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class DeathGenerator implements IWorldGenerator {
	
    private WorldGenSpike obsidianSpikeGen;
    
    public DeathGenerator() {
    	obsidianSpikeGen = new WorldGenSpike(Blocks.coal_block);
    }
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -2:
		    generateDeath(world, random, chunkX * 16, chunkZ * 16);
		    break;
		default: break;
		}
	}
	
	private static final float spikeChance = 0.04F;
	private static final float urnChance = 0.6F;
	
	public void generateDeath(World world, Random random, int chunkX, int chunkZ) {
		int k;
		int l;
		int i1;
        if (random.nextFloat() < spikeChance) {
        	k = chunkX + random.nextInt(64) - 32;
            l = chunkZ + random.nextInt(64) - 32;
        	double more;
        	do {
	            this.obsidianSpikeGen.generate(world, random, k, 255, l);
	            k += random.nextInt(64) - 32;
	            l += random.nextInt(64) - 32;
	            more = random.nextFloat();
        	} while (more < 0.3);
        }
        if (random.nextFloat() < urnChance) {
        	k = chunkX + random.nextInt(16) - 8;
            l = chunkZ + random.nextInt(64) - 8;
            trySpawnUrn(world,k,l);
        }
	}
	
	private void trySpawnUrn(World w, int x, int z) {
		Block block;
		int y = 255;
        do
        {
            block = w.getBlock(x, y, z);
            if (block == CorruptSoil.instance)
            {
                break;
            }
            --y;
        } while (y > 5);
		if (block != CorruptSoil.instance) return;
		w.setBlock(x, y+1, z, DKWMod.urn);
	}
	
    private int nextInt(Random r, int i) {
        if (i <= 1)
            return 0;
        return r.nextInt(i);
	}

}

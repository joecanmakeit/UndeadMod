package com.marablefamily.dkwmod;

import java.util.Random;

import com.marablefamily.dkwmod.dimension.WorldGenObsidianSpike;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class DeathGenerator implements IWorldGenerator {
	
    private WorldGenObsidianSpike obsidianSpikeGen;
    
    public DeathGenerator() {
    	obsidianSpikeGen = new WorldGenObsidianSpike();
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
	
	public void generateDeath(World world, Random random, int chunkX, int chunkZ) {
		int k;
		int l;
		int i1;
        if (random.nextDouble() < 0.04) {
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
	}
	
    private int nextInt(Random r, int i) {
        if (i <= 1)
            return 0;
        return r.nextInt(i);
	}

}

package com.marablefamily.dkwmod.dimension;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.marablefamily.dkwmod.DKWMod;
import com.marablefamily.dkwmod.block.CorruptStone;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLantern extends WorldGenerator {

	private enum direction {
		north,
		east,
		south,
		west,
		none
	}
	
	private int candidates = 12;
	private int searchX = 4;
	private int searchY = 4;
	private int searchZ = 4;
	
	public int lanternColor = 0;
	public int maxPoleLength = 4;
	public Block base = CorruptStone.instance;
	
	@Override
	public boolean generate(World world, Random r, int x, int y, int z) {

		for (int i = 0; i < candidates; ++i)
        {
            int x1 = x - searchX + r.nextInt(searchX*2);
            int y1 = y - searchY + r.nextInt(searchY*2);
            int z1 = z - searchZ + r.nextInt(searchZ*2);

            direction dir = isValidLocation(world, x1, y1, z1);
            
            if (dir != direction.none)
            {
            	//System.out.printf("Lantern: [%d,%d,%d]\n",x,y,z);
            	buildLantern(world, x1, y1, z1, dir);
            	return true;
            }
        }

        return true;
	}
	
	private boolean isClear(World world, int x, int y, int z) {
		int i, j, k;
		
		for (i = -1; i <= 1; ++i) {
			for (j = -3; j <= 0; ++j) {
				for (k = -1; k <= 1; ++k) {
					if (!world.isAirBlock(x + i, y + j, z + k)) return false;
				}
			}
		}
		
		return true;
	}
	
	private direction isValidLocation(World world, int x, int y, int z) {
		if (!isClear(world,x,y,z)) return direction.none;
		
		int i;
		for (i = 1; i < maxPoleLength; ++i) {
			if (world.getBlock(x - i, y, z) == this.base) return direction.west;
			if (world.getBlock(x + i, y, z) == this.base) return direction.east;
			if (world.getBlock(x, y, z - i) == this.base) return direction.north;
			if (world.getBlock(x, y, z + i) == this.base) return direction.south;
		}
		
		return direction.none;
	}
	
	private void buildLantern(World world, int x, int y, int z, direction dir) {
		int dx, dz;
		
		switch(dir) {
		case north: dz = -1; dx = 0; break;
		case east: dx = 1; dz = 0; break;
		case south: dz = 1; dx = 0; break;
		case west: dx = -1; dz = 0; break;
		default: return;
		}
		
		int x1 = x + dx;
		int z1 = z + dz;
		
		// Generate the pole
		while (world.isAirBlock(x1, y, z1)) {
			world.setBlock(x1, y, z1, Blocks.fence, 0, 2);
			
			x1 += dx;
			z1 += dz;
		}
		
		// Fixture
		world.setBlock(x,y,z,Blocks.log, 0, 2);
		
		// Generate the lantern
		int i, j, k;
		for (i = -1; i <= 1; ++i) {
			for (j = -3; j <= -1; ++j) {
				for (k = -1; k <= 1; ++k) {
					if (i == 0 && k == 0 && j == -2) continue;
					else world.setBlock(x + i,y + j,z + k,Blocks.stained_glass, lanternColor, 2);
				}
			}
		}
		
		// Light
		world.setBlock(x, y - 2, z, Blocks.glowstone);
	}
}

package com.marablefamily.dkwmod.dimension;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.marablefamily.dkwmod.DKWMod;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSphere extends WorldGenerator {

	private double minRad = 1;
	private double maxRad = 2.5;
	
	@Override
	public boolean generate(World world, Random r, int x, int y, int z) {
		
		double rad = r.nextDouble()*(maxRad - minRad) + minRad;
		
		for (int i = (int)-rad; i <= rad; ++i) {
        	for (int j = (int)-rad; j <= rad; ++j) {
        		for (int k = (int)-rad; k <= rad; ++k) {
        			if (measure(i,j,k) > rad) continue;
        			
        			world.setBlock(x + i, y + j, z + k, Blocks.wool, r.nextInt(16), 2);
        		}
        	}
        }

        return true;
	}
	
	private double measure(int dx, int dy, int dz) {
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
}

package com.marablefamily.dkwmod.dimension;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.marablefamily.dkwmod.DKWMod;
import com.marablefamily.dkwmod.block.CorruptSoil;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenObelisk extends WorldGenerator {

	@Override
	public boolean generate(World world, Random r, int x, int y, int z) {
		
		while (world.isAirBlock(x, y, z) && y > 2)
        {
            --y;
        }
		
		if (world.getBlock(x,y,z) == CorruptSoil.instance) {
			for (int i = 1; i < 5; ++i) {
				world.setBlock(x, y + i, z, Blocks.stonebrick,0,2);
			}
		}
		
        return true;
	}
}

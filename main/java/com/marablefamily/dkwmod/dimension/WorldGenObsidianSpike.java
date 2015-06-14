package com.marablefamily.dkwmod.dimension;

import java.util.Random;

import com.marablefamily.dkwmod.block.CorruptSoil;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenObsidianSpike extends WorldGenerator {

	private static double minLen = 15.0F;
	private static double maxLen = 60.0F;
	private static double maxAng = Math.PI/4.0;
	private static double minRad = 3.0F;
	private static double maxRad = 5.0F;
	
	@Override
	public boolean generate(World w, Random r, int x, int y, int z) {
		Block block;

        do
        {
            block = w.getBlock(x, y, z);
            if (block == CorruptSoil.instance)
            {
                break;
            }
            --y;
        } while (y > 5);
		y -= 5;
		if (block != CorruptSoil.instance) return false;
		
		double xStep = Math.sin(-(2*maxAng*r.nextDouble()-maxAng));
		double zStep = Math.sin(-(2*maxAng*r.nextDouble()-maxAng));
		double height = (maxLen-minLen)*r.nextDouble() + minLen;
		double rad = (maxRad-minRad)*r.nextDouble() + minRad;
		double angStep;
		double rCurr;
		double xCurr = x;
		double zCurr = z;
		for (double yCurr = 0; yCurr < height; yCurr += 1.0) {
			rCurr = rad*((height-yCurr)/height);
			xCurr += xStep/2.0;
			zCurr += zStep/2.0;
			angStep = (2*Math.PI)/(4.0*rCurr);
			for (double rot = 0; rot < 360.0; rot += angStep) {
				double xEdge = Math.cos(rot)*rCurr;
				double zEdge = Math.sin(rot)*rCurr;
				int xT = (int)Math.floor(xCurr+xEdge);
				int yT = (int)Math.floor(y+yCurr);
				int zT = (int)Math.floor(zCurr+zEdge);
				w.setBlock(xT, yT, zT, Blocks.obsidian, 0, 2);
			}
		}
		for (double yCurr = 0; yCurr > -rad*2; yCurr--) {
			rCurr = rad*(((rad*2)+yCurr)/(rad*2));
			angStep = (2*Math.PI)/(4.0*rCurr);
			for (double rot = 0; rot < 360.0; rot += angStep) {
				double xEdge = Math.cos(rot)*rCurr;
				double zEdge = Math.sin(rot)*rCurr;
				int xT = (int)Math.floor(x+xEdge);
				int yT = (int)Math.floor(y+yCurr);
				int zT = (int)Math.floor(z+zEdge);
				w.setBlock(xT, yT, zT, Blocks.obsidian, 0, 2);
			}
		}
		return true;
	}

}

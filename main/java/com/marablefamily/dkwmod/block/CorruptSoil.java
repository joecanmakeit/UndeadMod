package com.marablefamily.dkwmod.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CorruptSoil extends Block {
	public static CorruptSoil instance;
	
	static {
		instance = new CorruptSoil();
	}
	
	public CorruptSoil() {
		super(Material.ground);
		
		this.setBlockName("corruptSoil");
		this.setBlockTextureName("dkwmod:corruptsoil");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(0.8F);
		this.setTickRandomly(false);
	}
	
	/**
	 * Chance of expanding into an adjacent air block
	 * Index in array is current number of adjacent air blocks
	 */
	private static float[] expansionChance = {0.0f, 1.0f, 0.707f, 0.5f, 0.333f, 0.5f, 1.0f};
	
	/**
	 * Chance of deleting self
	 * Index in array is current number of adjacent corruption blocks
	 */
	private static float[] reductionChance = {0.05f, 0.333f, 0.666f, 1.0f, 0.666f, 0.333f, 0.0f};

    /**
     * Ticks the block if it's been scheduled
     */
	/*
    public void updateTick(World world, int x, int y, int z, Random r) {

    	if (r.nextDouble() > 0.8) {
	    	double corruptionPulse = Math.sin((double)world.getWorldTime() * 0.001) * 0.5 + 0.5;
	    	
	    	if (r.nextDouble() > corruptionPulse) {
	    		if (r.nextDouble() < expansionChance[countAdjacentBlocks(world, x, y, z, Blocks.air)]) {
	    			convertAdjacentBlock(world, x, y, z, r);
	    		}
	    	} else {
	    		if (r.nextDouble() < reductionChance[countAdjacentBlocks(world,x,y,z,instance)]) {
	    			world.setBlock(x,y,z,Blocks.air);
	    		}
	    	}
    	}
    }
    */
    
    private int countAdjacentBlocks(World world, int x, int y, int z, Block blockType) {
    	int count = 0;
    	
    	for (int i = 0; i < 6; ++i) {
    		if (getNeighbor(world,x,y,z,i) == blockType) ++count;
    	}
    	
    	return count;
    }
    
    private void convertAdjacentBlock(World world, int x, int y, int z, Random r) {
    	ArrayList<Integer> validDirections = new ArrayList<Integer>();

    	for (int i = 0; i < 6; ++i) {
    		if (getNeighbor(world,x,y,z,i) == Blocks.air) validDirections.add(i);
    	}
    	
    	int d = r.nextInt(validDirections.size());
    	setNeighbor(world,x,y,z,d,instance);
    }
    
    private Block getNeighbor(World world, int x, int y, int z, int dir) {
    	switch(dir) {
    	case 0: return world.getBlock(x+1,y,z);
    	case 1: return world.getBlock(x-1,y,z);
    	case 2: return world.getBlock(x,y+1,z);
    	case 3: return world.getBlock(x,y-1,z);
    	case 4: return world.getBlock(x,y,z+1);
    	case 5: return world.getBlock(x,y,z-1);
    	default: return null;
    	}
    }
    
    private void setNeighbor(World world, int x, int y, int z, int dir, Block block) {
    	switch(dir) {
    	case 0: world.setBlock(x+1,y,z,block);
    	case 1: world.setBlock(x-1,y,z,block);
    	case 2: world.setBlock(x,y+1,z,block);
    	case 3: world.setBlock(x,y-1,z,block);
    	case 4: world.setBlock(x,y,z+1,block);
    	case 5: world.setBlock(x,y,z-1,block);
    	}
    }
}

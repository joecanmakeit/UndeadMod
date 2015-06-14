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
	
	private int timer = 1;
	private boolean currentlySpreading = false;
	private int direction = 0;
	private int debugCount = 0;
	private static int dirX(int dir) {
		switch(dir) {
		case 0:  return -1;
		case 1:  return 1;
		default: return 0;
		}
	}
	private static int dirY(int dir) {
		switch(dir) {
		case 2:  return -1;
		case 3:  return 1;
		default: return 0;
		}
	}
	private static int dirZ(int dir) {
		switch(dir) {
		case 4:  return -1;
		case 5:  return 1;
		default: return 0;
		}
	}
	
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
    public void updateTick(World world, int x, int y, int z, Random r) {
		--this.timer;
    	if(this.timer > 0)
    		return;
    	    	
    	int nx = x + dirX(this.direction);
    	int ny = y + dirY(this.direction);
    	int nz = z + dirZ(this.direction);
    	if(world.getBlock(nx, ny, nz) == Blocks.air) {
    		if(this.currentlySpreading)
    			world.setBlock(nx, ny, nz, instance, 0, 2);
    		else
    			world.setBlock(x, y, z, Blocks.air, 0, 2);
    	}
    	
    	this.timer = 20000;
    	this.direction = (this.direction+1) % 6;
    	this.currentlySpreading = !this.currentlySpreading;
    }
}

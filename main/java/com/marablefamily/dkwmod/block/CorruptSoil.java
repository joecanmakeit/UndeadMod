package com.marablefamily.dkwmod.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CorruptSoil extends Block {
	public CorruptSoil() {
		super(Material.ground);
		
		this.setBlockName("corruptSoil");
		this.setBlockTextureName("myassets:corruptsoil");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(0.8F);
		this.setTickRandomly(true);
	}

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random p_149674_5_) {
    	
    	/*if (world.getBlock(x, y, z)) {
    		
    	}
    	
		// TEMP FIX -- Change boots to Frost Boots!
		if (boots != null && boots.getItem() == Items.chainmail_boots) {
			int time = playerStandingTimes.get(name);
			int rad = minRad + ((maxRad-minRad)*(Math.min(timeToMax, time)/timeToMax));
			int x = (int)(e.player.posX);
			int y = (int)(e.player.posY - 1);
			int z = (int)(e.player.posZ);
			e.player.worldObj.setBlock(x,y,z,Blocks.ice,0,2);
			int i = (int) (Math.random()*2*rad - rad);
			int k = (int) (Math.random()*2*rad - rad);
			while (Math.sqrt(i*i + k*k) > rad) {
				i = (int) (Math.random()*2*rad - rad);
				k = (int) (Math.random()*2*rad - rad);
			}
			e.player.worldObj.setBlock(x+i, y, z+k, Blocks.ice, 0, 2);
		}*/
    }
}

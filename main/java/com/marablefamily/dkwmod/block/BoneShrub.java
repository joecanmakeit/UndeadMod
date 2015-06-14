package com.marablefamily.dkwmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class BoneShrub extends BlockDeadBush {
	public static BoneShrub instance;
	
	static {
		instance = new BoneShrub();
	}

	protected BoneShrub() {
		super();
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName("boneShrub");
		this.setBlockTextureName("dkwmod:boneShrub");
	}
	
	protected boolean canPlaceBlockOn(Block b)
    {
        return  b == Blocks.sand || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
        		b == Blocks.dirt || b == CorruptStone.instance || b == CorruptSoil.instance;
    }

}

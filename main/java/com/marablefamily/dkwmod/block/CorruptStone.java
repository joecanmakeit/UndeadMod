package com.marablefamily.dkwmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class CorruptStone extends BlockStone {
	
	public static CorruptStone instance;
	
	static {
		instance = new CorruptStone();
	}

	protected CorruptStone() {
		super();
		this.setBlockName("corruptStone");
		this.setBlockTextureName("dkwmod:corruptStone");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(2.0F);
		this.setResistance(30.0F);
		this.setStepSound(Block.soundTypeStone);
		this.setHarvestLevel("pickaxe", 1);
	}

}

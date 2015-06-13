package com.marablefamily.dkwmod;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.marablefamily.dkwmod.block.CorruptSoil;
import com.marablefamily.dkwmod.dimension.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="dkwmod", name="Death Knight World", version="0.1")
public class DKWMod {
	@Instance(value = "1")
	public static DKWMod instance;
	@SidedProxy(clientSide="com.marablefamily.dkwmod.client.ClientProxy", serverSide="com.marablefamily.dkwmod.CommonProxy")
	public static CommonProxy proxy;
	
	public static int deathWorldId = -2;
	public static int biomeId = 50;
	public static MyTeleporterItem tpDeath = new MyTeleporterItem("tpDeath", deathWorldId);
	public static BiomeGenDeadPlains deadPlains = new BiomeGenDeadPlains(biomeId++, Blocks.stone);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerItem(tpDeath, "tpDeath");
		GameRegistry.registerBlock(CorruptSoil.instance, "corruptSoil");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		DimensionManager.registerProviderType(deathWorldId, WorldProviderDeath.class, false);
		DimensionManager.registerDimension(deathWorldId, deathWorldId);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}

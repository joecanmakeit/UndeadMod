package com.marablefamily.dkwmod.dimension;

import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.CUSTOM;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE;

import java.util.ArrayList;

import com.marablefamily.dkwmod.DKWMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomBiome extends MyBiomeBase {

	public CustomBiome(int i, Block base) {
		super(i, base);
		MyBiomeDecorator decorator = (MyBiomeDecorator)this.theBiomeDecorator;
		this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
		this.enableSnow = true;

		// BASICS
		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		this.setHeight(new BiomeGenBase.Height(-0.5F, 1.0F));
		this.setTemperatureRainfall(2.0F, 0.1F);
		this.grassColor = 0x078CE9;
		this.foliageColor = 0xF014ED;
		this.waterColorMultiplier = 0x00F34A;
		
		// DECORATIONS
		decorator.treesPerChunk = 1;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 2;
		decorator.mushroomsPerChunk = 4;
		decorator.bigMushroomsPerChunk = 0;
		decorator.deadBushPerChunk = 0;
		decorator.cactiPerChunk = 0;
		decorator.reedsPerChunk = 6;
		decorator.waterlilyPerChunk = 6;
		decorator.underwaterSandPerChunk = 3;
		decorator.underwaterGravelPerChunk = 1;
		decorator.clayPerChunk = 1;
		decorator.generateLakes = true;

        // ORE SPAWNING
		decorator.addOreSpawn(Blocks.dirt, 32, 20, 0, 256, base, DIRT);
		decorator.addOreSpawn(Blocks.gravel, 32, 10, 0, 256, base, GRAVEL);
		decorator.addOreSpawn(Blocks.coal_ore, 16, 20, 0, 128, base, COAL);
		decorator.addOreSpawn(Blocks.iron_ore, 8, 20, 0, 256, base, IRON);
		decorator.addOreSpawn(Blocks.gold_ore, 8, 2, 0, 32, base, GOLD);
		decorator.addOreSpawn(Blocks.redstone_ore, 7, 8, 0, 16, base, REDSTONE);
		decorator.addOreSpawn(Blocks.diamond_ore, 7, 1, 0, 16, base, DIAMOND);
		decorator.addOreSpawn(Blocks.lapis_ore, 6, 1, 16, 16, base, LAPIS);

    	// CREATURE SPAWNING
		this.addCreatureSpawn(EntitySheep.class, 12, 4, 4);
		this.addCreatureSpawn(EntityPig.class, 10, 4, 4);
		this.addCreatureSpawn(EntityChicken.class, 10, 4, 4);
		this.addCreatureSpawn(EntityCow.class, 8, 4, 4);
		
		// MONSTER SPAWNING
		this.addMonsterSpawn(EntitySpider.class, 100, 4, 4);
		this.addMonsterSpawn(EntityZombie.class, 100, 4, 4);
		this.addMonsterSpawn(EntitySkeleton.class, 100, 4, 4);
		this.addMonsterSpawn(EntityCreeper.class, 100, 4, 4);
		this.addMonsterSpawn(EntitySlime.class, 100, 4, 4);
		this.addMonsterSpawn(EntityEnderman.class, 10, 1, 4);
		this.addMonsterSpawn(EntityWitch.class, 5, 1, 1);
	}

}

package com.marablefamily.dkwmod.dimension;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.CUSTOM;

import java.util.ArrayList;
import java.util.Random;

import com.marablefamily.dkwmod.DKWMod;
import com.marablefamily.dkwmod.block.BoneShrub;
import com.marablefamily.dkwmod.dimension.MyBiomeDecorator.Ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorDeath extends BiomeDecorator {

	public ArrayList<Ore> oreGenList = new ArrayList<Ore>();
    public int underwaterSandPerChunk;
    public int underwaterGravelPerChunk;
    public int boneShrubPerChunk;
    public int lanternPerChunk;

    private WorldGenSphere sphereGen;
    private WorldGenObelisk obeliskGen;
    private WorldGenLantern lanternGen;
	
    public BiomeDecoratorDeath(Block base)
    {
    	this.sphereGen = new WorldGenSphere();
    	this.obeliskGen = new WorldGenObelisk();
    	this.lanternGen = new WorldGenLantern();
    } 
 
    @Override
    public void decorateChunk(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_)
    {
        if (this.currentWorld != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.currentWorld = p_150512_1_;
            this.randomGenerator = p_150512_2_;
            this.chunk_X = p_150512_4_;
            this.chunk_Z = p_150512_5_;
            this.genDecorations(p_150512_3_);
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }
    
    @Override
    protected void genDecorations(BiomeGenBase p_150513_1_)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
        this.generateOres();
        int i;
        int j;
        int k;
        int l;
        int i1;

        boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, DEAD_BUSH);
        for (j = 0; doGen && j < this.boneShrubPerChunk; ++j)
        {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            i1 = nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            (new WorldGenDeadBush(BoneShrub.instance)).generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CACTUS);
        if (this.randomGenerator.nextDouble() < 1) {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            i1 = nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            this.sphereGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CACTUS);
        if (this.randomGenerator.nextDouble() < 1) {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            i1 = this.currentWorld.getHeightValue(k, l);
            this.obeliskGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CACTUS);
        for (j = 0; doGen && j < this.lanternPerChunk; ++j) {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            i1 = nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            this.lanternGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }
        
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }
    
    private int nextInt(int i) {
        if (i <= 1)
            return 0;
        return this.randomGenerator.nextInt(i);
	}
   
    class Ore {
    	public WorldGenMinable node;
    	public Block ore;
    	public int minHeight;
    	public int maxHeight;
    	public int freq;
    	public OreGenEvent.GenerateMinable.EventType type;
    	
    	public Ore(Block ore, int size, int freq, int min, int max, Block base, OreGenEvent.GenerateMinable.EventType type) {
    		this.node = new WorldGenMinable(ore, size, base);
    		minHeight = min;
    		maxHeight = max;
    		this.type = type;
    		this.ore = ore;
    		this.freq = freq;
    	}
    }
    
    public void genCustomOre(Ore o) {
    	if (TerrainGen.generateOre(currentWorld, randomGenerator, o.node, chunk_X, chunk_Z, o.type)) {
    		if (o.ore == Blocks.lapis_ore) this.genStandardOre2(o.freq, o.node, o.minHeight, o.maxHeight);
    		else this.genStandardOre1(o.freq, o.node, o.minHeight, o.maxHeight);
    	}
    }
    
    public void addOreSpawn(Block ore, int size, int freq, int min, int max, Block base, OreGenEvent.GenerateMinable.EventType type) {
    	this.oreGenList.add(new Ore(ore, size, freq, min, max, base, type));
    }
    
    public void removeOreSpawn(Block ore) {
    	Ore target = null;
    	for (Ore o : this.oreGenList) {
    		if (o.ore == ore) target = o;
    	}
    	this.oreGenList.remove(target);
    }
    
    
    @Override
    protected void generateOres()
    {
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
    
        // ORE GENERATIONS
        for (Ore o : oreGenList) {
        	genCustomOre(o);
        }
        
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }

	
}

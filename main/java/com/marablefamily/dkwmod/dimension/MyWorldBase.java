package com.marablefamily.dkwmod.dimension;

import java.awt.Color;

import com.marablefamily.dkwmod.DKWMod;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.ForgeHooksClient;

public abstract class MyWorldBase extends WorldProvider {

	public void registerWorldChunkManager() {
		// DIMENSION SETUP
		this.dimensionId = this.customID();
		this.terrainType = WorldType.FLAT;
		this.worldObj.getWorldInfo().setTerrainType(WorldType.FLAT);
		this.worldChunkMgr = new MyWorldChunkManager(worldObj.getSeed(), WorldType.FLAT, this.customBiomes(), this.customBiomeSize());
	}

	@Override
	public String getDimensionName() {
		return "World One";
	}
	
	// CUSTOMIZATION STUFF
	
	protected abstract int customID();
	protected abstract BiomeGenBase[] customBiomes();
	protected abstract String customSkyColor();
	protected abstract String customCloudColor();
	protected abstract String customFogColor();
	protected abstract String customSunriseSunsetColor();
	protected abstract float customDaySpeed();
	protected abstract int customBiomeSize();
	protected abstract Block getBaseBlock();
	
	@Override
    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
    {
        int j = (int)(p_76563_1_ % 24000L);
        float f1 = ((float)j + p_76563_3_) / 24000.0F - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 1.0F - (float)((Math.cos((double)f1 * Math.PI) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1*this.customDaySpeed();
    }
		
	/*
	
	@Override
	public String getWelcomeMessage() {
		return "Entering the Test Dimension...";
	}
	
	@Override
	public String getDepartMessage() {
		return "Leaving the Test Dimension...";
	}
	
	@Override
	public boolean canDoLightning(Chunk chunk) {
		return true;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return true;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}
    
    */
	
	@SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
        float f2 = MathHelper.cos(p_76562_1_ * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        Color color = Color.decode(this.customFogColor());
        float f3 = ((float)(color.getRed()))/255.0F;
        float f4 = ((float)(color.getGreen()))/255.0F;
        float f5 = ((float)(color.getBlue()))/255.0F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3.createVectorHelper((double)f3, (double)f4, (double)f5);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {   
        float f1 = worldObj.getCelestialAngle(partialTicks);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        Color color = Color.decode(this.customSkyColor());
        float f4 = ((float)(color.getRed()))/255.0F;
        float f5 = ((float)(color.getGreen()))/255.0F;
        float f6 = ((float)(color.getBlue()))/255.0F;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = worldObj.getRainStrength(partialTicks);
        float f8;
        float f9;

        if (f7 > 0.0F)
        {
            f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            f9 = 1.0F - f7 * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }

        f8 = worldObj.getWeightedThunderStrength(partialTicks);

        if (f8 > 0.0F)
        {
            f9 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
            float f10 = 1.0F - f8 * 0.75F;
            f4 = f4 * f10 + f9 * (1.0F - f10);
            f5 = f5 * f10 + f9 * (1.0F - f10);
            f6 = f6 * f10 + f9 * (1.0F - f10);
        }

        if (worldObj.lastLightningBolt > 0)
        {
            f9 = (float)worldObj.lastLightningBolt - partialTicks;

            if (f9 > 1.0F)
            {
                f9 = 1.0F;
            }

            f9 *= 0.45F;
            f4 = f4 * (1.0F - f9) + 0.8F * f9;
            f5 = f5 * (1.0F - f9) + 0.8F * f9;
            f6 = f6 * (1.0F - f9) + 1.0F * f9;
        }

        return Vec3.createVectorHelper((double)f4, (double)f5, (double)f6);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(p_76560_1_ * (float)Math.PI * 2.0F) - 0.0F;
        float f4 = -0.0F;
        
        if (f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            Color color = Color.decode(this.customSunriseSunsetColor());
        	float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
            f6 *= f6;
            float[] result = new float[4];
            result[0] = ((float)(color.getRed()))/255.0F;
            result[1] = ((float)(color.getGreen()))/255.0F;
            result[2] = ((float)(color.getBlue()))/255.0F;
            result[3] = f6;
            return result;
        }
        else
        {
            return null;
        }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(float partialTicks)
    {
        float f1 = worldObj.getCelestialAngle(partialTicks);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        Color color = Color.decode(this.customCloudColor());
        float f3 = ((float)(color.getRed()))/255.0F;
        float f4 = ((float)(color.getGreen()))/255.0F;
        float f5 = ((float)(color.getBlue()))/255.0F;
        float f6 = worldObj.getRainStrength(partialTicks);
        float f7;
        float f8;

        if (f6 > 0.0F)
        {
            f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
            f8 = 1.0F - f6 * 0.95F;
            f3 = f3 * f8 + f7 * (1.0F - f8);
            f4 = f4 * f8 + f7 * (1.0F - f8);
            f5 = f5 * f8 + f7 * (1.0F - f8);
        }

        f3 *= f2 * 0.9F + 0.1F;
        f4 *= f2 * 0.9F + 0.1F;
        f5 *= f2 * 0.85F + 0.15F;
        f7 = worldObj.getWeightedThunderStrength(partialTicks);

        if (f7 > 0.0F)
        {
            f8 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
            float f9 = 1.0F - f7 * 0.95F;
            f3 = f3 * f9 + f8 * (1.0F - f9);
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
        }

        return Vec3.createVectorHelper((double)f3, (double)f4, (double)f5);
    }
	
	@Override
	public IChunkProvider createChunkGenerator() {
		worldObj.getWorldInfo().setTerrainType(WorldType.FLAT);
		return new MyChunkProvider(worldObj, worldObj.getSeed(), true, this.getBaseBlock());
	}

}

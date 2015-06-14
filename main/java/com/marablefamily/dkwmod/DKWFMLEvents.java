package com.marablefamily.dkwmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class DKWFMLEvents {

	int tickCount = 0;
	int height = 1;
	int minRad = 2;
	int maxRad = 7;
	int timeToMax = 140;
	
	Random random = new Random();
	HashMap<String, Integer> playerStandingTimes = new HashMap<String, Integer>();
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent e) {		
		if (e.player.dimension != -2) {
			e.player.travelToDimension(-2);
			
			
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent e) {
		
	}
	
	private void sendToDimension(Entity e, int dimension, Teleporter teleporter) {
		World w = e.worldObj;
		
		if (!w.isRemote && !e.isDead)
        {
            w.theProfiler.startSection("changeDimension");
            // Get the current dimension world server
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int currentDimension = e.dimension;
            WorldServer currentWorldServer = minecraftserver.worldServerForDimension(currentDimension);
           
            // Get the target dimension world server
            WorldServer finalWorldServer;
            if (currentDimension == dimension) {
            	finalWorldServer = minecraftserver.worldServerForDimension(0);
                e.dimension = 0;
            } else {
            	 finalWorldServer = minecraftserver.worldServerForDimension(dimension);
                 e.dimension = dimension;
            }

            // Remove this entity from the current dimension
            w.removeEntity(e);
            e.isDead = false;
            w.theProfiler.startSection("reposition");
            minecraftserver.getConfigurationManager().transferEntityToWorld(e, currentDimension, currentWorldServer, finalWorldServer, teleporter);
            w.theProfiler.endStartSection("reloading");
            Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(e), finalWorldServer);

            if (newEntity != null)
            {
            	newEntity.copyDataFrom(e, true);

                if (currentDimension == dimension)
                {
                    ChunkCoordinates chunkcoordinates = finalWorldServer.getSpawnPoint();
                    chunkcoordinates.posY = w.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    newEntity.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, newEntity.rotationYaw, newEntity.rotationPitch);
                }

                finalWorldServer.spawnEntityInWorld(newEntity);
            }

            e.isDead = true;
            w.theProfiler.endSection();
            currentWorldServer.resetUpdateEntityTick();
            finalWorldServer.resetUpdateEntityTick();
            w.theProfiler.endSection();
        }
	}
	
	/*
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		
		if (!e.player.worldObj.isRemote && e.phase == e.phase.END) {
			String name = e.player.getCommandSenderName();
			if (!playerStandingTimes.containsKey(name)) {
				playerStandingTimes.put(name, 0);
			}
			if (Math.abs(e.player.motionX) < 0.1 && Math.abs(e.player.motionY) < 0.1 && Math.abs(e.player.motionZ) < 0.1) {
				playerStandingTimes.put(name, playerStandingTimes.get(name)+1);
			}
			else {
				playerStandingTimes.put(name, 0);
			}
			ItemStack boots = e.player.getCurrentArmor(0);
			
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
			}
		}
	}
	*/
}

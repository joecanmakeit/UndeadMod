package com.marablefamily.dkwmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.marablefamily.dkwmod.block.CorruptSoil;
import com.marablefamily.dkwmod.dimension.MyTeleporter;

import net.minecraft.block.Block;
//import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
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
	
	public DKWFMLEvents() {
		
	}
	
	Random random = new Random();
	
	class ExtraPlayerState {
		public int standingTime;
		public int blocksNearby;
	}
	
	HashMap<String, ExtraPlayerState> extraPlayerState = new HashMap<String, ExtraPlayerState>();
	
	int deathTargetDimension = -2;
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent e) {		
		if (e.player.dimension != deathTargetDimension) {
			FMLCommonHandler.instance()
				.getMinecraftServerInstance()
				.getConfigurationManager()
				.transferPlayerToDimension(
						(EntityPlayerMP)e.player, 
						deathTargetDimension, 
						new MyTeleporter(
								MinecraftServer.getServer()
								.worldServerForDimension(deathTargetDimension)));
			
			ChunkCoordinates c = MinecraftServer.getServer().worldServerForDimension(deathTargetDimension).getSpawnPoint();
			e.player.setPosition(c.posX, c.posY, c.posZ);
		}
	}
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e) {
		World w = e.world;
		
		if(w.isRemote)
			return;
		
/*
		List chunks = ((ChunkProviderServer) w.getChunkProvider()).loadedChunks;
		int len = chunks.size();
		
		if(len <= 0)
			return;
		
		int base = random.nextInt(len);
		//TODO: Tweak probability distribution of this randomness ...
		for(int i=0; i<len/4; ++i) {
			Chunk chunk = (Chunk) chunks.get((i+base) % len);
			
			int x = 16 * chunk.xPosition + random.nextInt(16);
			int y = random.nextInt(100);  // Does this need tweaking???
			int z = 16 * chunk.zPosition + random.nextInt(16);
			
			// Cause the world to crumble
			// TODO: use some criterion other than "air" and "not air"
			if(w.getBlock(x,y,z) == Blocks.air  &&  w.getBlock(x,y+1,z) != Blocks.air) {
//				System.out.println(x + " " + y + " " + z + " " + len);  //debug
				w.setBlock(x,y+1,z,Blocks.sand);  //TODO: don't replace the block with sand ...
			}
			
			// Move the corrupt soil
			if(w.getBlock(x,y,z) == CorruptSoil.instance)
				CorruptSoil.instance.updateTick(w, x, y, z, random);
		}
*/
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
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if (!e.player.worldObj.isRemote && e.phase == e.phase.END) {
			
			String name = e.player.getCommandSenderName();

			if (!extraPlayerState.containsKey(name)) {
				extraPlayerState.put(name, new ExtraPlayerState());
			}
			
			ExtraPlayerState extra = extraPlayerState.get(name);
			
			updateBlocksNearby(e, extra);
			
//			updateFrostBoots(e, extra);
		}
	}

	private void updateBlocksNearby(PlayerTickEvent e, ExtraPlayerState s) {
		
		// Approximately 12.5% of nearby blocks should be non-air.
		// If fewer, then the count will drift to zero.
		// If more, then the count will drift to 300.
		
		World w = e.player.worldObj;
		
		int x = random.nextInt(9) - 4 + (int) e.player.posX;
		int y = random.nextInt(9) - 4 + (int) e.player.posY;
		int z = random.nextInt(9) - 4 + (int) e.player.posZ;
		if(w.getBlock(x, y, z) == Blocks.air)
			s.blocksNearby = Math.max(0, s.blocksNearby - 1);
		else
			s.blocksNearby = Math.min(300, s.blocksNearby + 7);

		// for debugging
//		if(random.nextInt(30) == 0)
//			System.out.println(s.blocksNearby);
	}
	
	private void updateFrostBoots(PlayerTickEvent e, ExtraPlayerState extra) {

		if (Math.abs(e.player.motionX) < 0.1 && Math.abs(e.player.motionY) < 0.1 && Math.abs(e.player.motionZ) < 0.1)
			++extra.standingTime;
		else
			extra.standingTime = 0;
		
		ItemStack boots = e.player.getCurrentArmor(0);
		// TEMP FIX -- Change boots to Frost Boots!
		if (boots != null && boots.getItem() == Items.chainmail_boots) {
			int time = extra.standingTime;
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

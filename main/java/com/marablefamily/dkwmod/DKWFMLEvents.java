package com.marablefamily.dkwmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
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
	
	class ExtraPlayerState {
		public int standingTime;
		public int blocksNearby;
	}
	
	HashMap<String, ExtraPlayerState> extraPlayerState = new HashMap<String, ExtraPlayerState>();
	
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
		// If more, then the count will drift to 100.
		
		World w = e.player.worldObj;
		
		int x = random.nextInt(9) - 4 + (int) e.player.posX;
		int y = random.nextInt(9) - 4 + (int) e.player.posY;
		int z = random.nextInt(9) - 4 + (int) e.player.posZ;
		if(w.getBlock(x, y, z) == Blocks.air)
			--s.blocksNearby;
		else
			s.blocksNearby += 7;
		
		if(random.nextInt(40) == 0)
			System.out.println(s.blocksNearby);
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

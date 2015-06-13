package dkwmod;

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
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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

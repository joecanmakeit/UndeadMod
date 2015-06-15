package com.marablefamily.dkwmod;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DKWForgeEvents {
	
	HashMap<String, Double> lastClimb = new HashMap<String, Double>();
	ArrayList<String> toRemoveFallDist = new ArrayList<String>();

	@SubscribeEvent
	public void onPlayerClick(PlayerInteractEvent e) {
		
	}
	
	@SubscribeEvent
	public void onLivingEntityDeath(LivingDeathEvent e) {
		//System.out.println("death");
		if (e.entityLiving instanceof EntityPlayer) {
			System.out.println("player death");
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (e.entityLiving instanceof EntityPlayer) {
			String name = ((EntityPlayer)e.entityLiving).getCommandSenderName();
			
			if (!e.entityLiving.worldObj.isRemote && toRemoveFallDist.contains(name) ) {
				e.entityLiving.fallDistance = 0.0F;
				toRemoveFallDist.remove(name);
			}
			
			if (e.entityLiving.isCollidedHorizontally) {
				ItemStack held = ((EntityPlayer)e.entityLiving).getCurrentEquippedItem();
				if (held != null && held.getItem() == DKWMod.climbingClaws) {
					if (e.entityLiving.motionY < -0.05) {
						e.entityLiving.motionY = -0.05;
						e.entityLiving.motionX *= 0.3F;
						e.entityLiving.motionZ *= 0.3F;
						e.entityLiving.onGround = true;
						toRemoveFallDist.add(name);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onFog(FogDensity e) {
		if (e.entity.dimension == DKWMod.deathWorldId) {
			e.setCanceled(true);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			e.density = 0.03F;
		}
	}
}

package com.marablefamily.dkwmod;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DKWForgeEvents {

	@SubscribeEvent
	public void onPlayerClick(PlayerInteractEvent e) {
		
	}
	
	@SubscribeEvent
	public void onFog(FogDensity e) {
		if (e.entity.dimension == DKWMod.deathWorldId) {
			e.setCanceled(true);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			e.density = 0.3F;
		}
	}
}

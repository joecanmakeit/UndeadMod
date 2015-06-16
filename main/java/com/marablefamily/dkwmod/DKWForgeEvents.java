package com.marablefamily.dkwmod;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DKWForgeEvents {
	
	Random rand = new Random();

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
	
	public static class StopFallMessage implements IMessage {
		private String name;
		private float posY;

		public StopFallMessage() {}
		public StopFallMessage(String name, float y) {
			this.name = name;
			this.posY = y;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			this.posY = buf.readFloat();
			this.name = ByteBufUtils.readUTF8String(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeFloat(this.posY);
			ByteBufUtils.writeUTF8String(buf, this.name);
		}
	}

	public static class StopFallHandler implements IMessageHandler<StopFallMessage, IMessage> {
		public static HashMap<String, Float> lastClimb = new HashMap<String, Float>();

		@Override
		public IMessage onMessage(StopFallMessage message, MessageContext ctx) {
			lastClimb.put(message.name, message.posY);
			return null; // no response in this case
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (e.entityLiving instanceof EntityPlayer) {
			String name = ((EntityPlayer)e.entityLiving).getCommandSenderName();
			
			if (!e.entityLiving.worldObj.isRemote && StopFallHandler.lastClimb.containsKey(name) ) {
				float climbPos = StopFallHandler.lastClimb.get(name);
				float dist = climbPos - (float) e.entity.posY;
				if(dist < e.entityLiving.fallDistance)
					e.entityLiving.fallDistance = dist;
				StopFallHandler.lastClimb.remove(name);
			}
			
			if (e.entityLiving.isCollidedHorizontally) {
				ItemStack held = ((EntityPlayer)e.entityLiving).getCurrentEquippedItem();
				if (held != null && held.getItem() == DKWMod.climbingClaws) {
					if (e.entityLiving.motionY < -0.05) {
						e.entityLiving.motionY = -0.05;
						e.entityLiving.motionX *= 0.3F;
						e.entityLiving.motionZ *= 0.3F;
						e.entityLiving.onGround = true;
						DKWMod.network.sendToServer(new StopFallMessage(name, (float) e.entityLiving.posY));
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFog(FogDensity e) {
		if (e.entity.dimension == DKWMod.deathWorldId) {
			e.setCanceled(true);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			e.density = 0.03F;
		}
	}
	
	class UrnDropItem extends WeightedRandom.Item {

		public Item item;
		public Block block;
		public int min;
		public int max;
		public UrnDropItem(int weight, Item item, int min, int max) {
			super(weight);
			this.item = item;
			this.min = min;
			this.max = max;
		}
		public UrnDropItem(int weight, Block block, int min, int max) {
			super(weight);
			this.block = block;
			this.min = min;
			this.max = max;
		}
	}
	
	public List urnDropOptions;
	//public static int urnDropWeightTotal ;
	
	public DKWForgeEvents() {
		// Setup urn drop chances
		urnDropOptions = new ArrayList();
		//urnDropWeightTotal = 0;
		urnDrop(Items.gold_nugget, 1, 4, 20);
		urnDrop(Items.emerald, 1, 2, 20);
		urnDrop(Items.golden_pickaxe, 1, 1, 5);
		urnDrop(Items.golden_shovel, 1, 1, 5);
		urnDrop(Items.golden_sword, 1, 1, 5);
		urnDrop(Items.golden_axe, 1, 1, 5);
		urnDrop(Items.diamond_pickaxe, 1, 1, 3);
		urnDrop(Items.diamond_shovel, 1, 1, 5);
		urnDrop(Items.diamond_sword, 1, 1, 3);
		urnDrop(Items.diamond_axe, 1, 1, 3);
		urnDrop(DKWMod.climbingClaws, 1, 1, 15);
		urnDrop(Items.apple, 1, 5, 20);
		urnDrop(Items.cooked_beef, 1, 2, 20);
		urnDrop(Items.cooked_fished, 1, 3, 15);
		urnDrop(Items.boat, 1, 1, 1);
		urnDrop(Items.cake, 1, 1, 1);
		urnDrop(Items.golden_horse_armor, 1, 1, 1);
	}
	
	public void urnDrop(Item item, int min, int max, int weight) {
		urnDropOptions.add(new UrnDropItem(weight, item, min, max));
		//urnDropWeightTotal += weight;
	}
	
	public void urnDrop(Block block, int min, int max, int weight) {
		urnDropOptions.add(new UrnDropItem(weight, block, min, max));
		//urnDropWeightTotal += weight;
	}
	
	@SubscribeEvent
	public void onBlockBreak(HarvestDropsEvent e) {
		if (!e.world.isRemote && e.block == DKWMod.urn) {
			e.drops.clear();
			UrnDropItem chosen = (UrnDropItem)WeightedRandom.getRandomItem(rand, urnDropOptions);
			if (chosen == null) {
				System.out.println("No chosen!");
				return;
			}
			ItemStack stack;
			int count;
			if (chosen.min == chosen.max) count = chosen.min;
			else count = rand.nextInt(chosen.max-chosen.min)+chosen.min;
			if (chosen.item != null) stack = new ItemStack(chosen.item, count);
			else stack = new ItemStack(chosen.block, count);
			e.drops.add(stack);
		}
	}
}

package com.marablefamily.dkwmod.client;

import com.marablefamily.dkwmod.CommonProxy;
import com.marablefamily.dkwmod.DKWMod;
import com.marablefamily.dkwmod.mobs.EntitySoul;
import com.marablefamily.dkwmod.mobs.ModelSoul;
import com.marablefamily.dkwmod.mobs.RenderSoul;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySoul.class, new RenderSoul(new ModelSoul(), 1.0F));
	}
}

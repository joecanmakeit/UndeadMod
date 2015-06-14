package com.marablefamily.dkwmod.mobs;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSoul extends RenderLiving {
	
    private static final ResourceLocation soulTextures = new ResourceLocation("dkwmod:textures/entity/soul.png");
    private static float maxBob = 0.2F;
    private static float bobSpeed = 0.06F;

	public RenderSoul(ModelBase model, float size) {
		super(model, size);
	}

	protected ResourceLocation getEntityTexture(EntitySoul p_110775_1_) {
		return soulTextures;
	}
    
	@Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntitySoul)p_110775_1_);
    }
	
	protected void fall(float p_70069_1_) {}

}

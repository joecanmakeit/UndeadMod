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
	
	@Override
	public void doRender(EntityLiving entityLiving, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		float bob = (float) (Math.sin((float)entityLiving.getAge()*bobSpeed)*maxBob);
		GL11.glTranslatef(0.0F, bob, 0.0F);
		super.doRender(entityLiving, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		GL11.glTranslatef(0.0F, -bob, 0.0F);
	}
	
	protected void fall(float p_70069_1_) {}

}

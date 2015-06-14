package com.marablefamily.dkwmod.mobs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSoul extends RenderLiving {
	
    private static final ResourceLocation soulTextures = new ResourceLocation("dkwmod:textures/entity/soul.png");

	public RenderSoul(ModelBase model, float size) {
		super(model, size);
	}

	protected ResourceLocation getEntityTexture(EntitySoul p_110775_1_) {
		return soulTextures;
	}

    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
    {
        return this.shouldRenderPass((EntitySpider)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntitySoul)p_110775_1_);
    }
}

package com.marablefamily.dkwmod.mobs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSoul extends ModelBase {
	
	public ModelRenderer soulBody;
	
	public ModelSoul() {
		float f = 0.0F;
		
        this.soulBody = new ModelRenderer(this, 0, 0);
        this.soulBody.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, f);
        this.soulBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);
        this.soulBody.render(p_78088_7_);
    }
	
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
    {
		
    }

}

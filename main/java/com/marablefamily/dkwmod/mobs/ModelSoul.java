package com.marablefamily.dkwmod.mobs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSoul extends ModelBase {
	
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
	
	public ModelSoul() {
		float f = 0.0F;
		
		textureWidth = 64;
	    textureHeight = 32;
	    
	      head = new ModelRenderer(this, 0, 0);
	      head.addBox(-4F, -8F, -4F, 8, 8, 8);
	      head.setRotationPoint(0F, 0F, 0F);
	      head.setTextureSize(64, 32);
	      head.mirror = true;
	      setRotation(head, 0.7807508F, 0F, 0F);
	      body = new ModelRenderer(this, 32, 0);
	      body.addBox(-4F, 0F, -2F, 8, 17, 4);
	      body.setRotationPoint(0F, 0F, 0F);
	      body.setTextureSize(64, 32);
	      body.mirror = true;
	      setRotation(body, 0.2974289F, 0F, 0F);
	      rightarm = new ModelRenderer(this, 0, 16);
	      rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
	      rightarm.setRotationPoint(-5F, 2F, 0F);
	      rightarm.setTextureSize(64, 32);
	      rightarm.mirror = true;
	      setRotation(rightarm, 0F, 0F, 0F);
	      leftarm = new ModelRenderer(this, 0, 16);
	      leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
	      leftarm.setRotationPoint(5F, 2F, 0F);
	      leftarm.setTextureSize(64, 32);
	      leftarm.mirror = true;
	      setRotation(leftarm, 0F, 0F, 0F);
	}
	
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	
	  public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(e, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, e);
	    head.render(f5);
	    body.render(f5);
	    rightarm.render(f5);
	    leftarm.render(f5);
	  }
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

}

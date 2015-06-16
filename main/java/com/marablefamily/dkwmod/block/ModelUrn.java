package com.marablefamily.dkwmod.block;

import com.marablefamily.dkwmod.mobs.EntitySoul;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelUrn extends ModelBase {
	
	ModelRenderer Shape1;
    ModelRenderer bottom;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
	
	public ModelUrn()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		  Shape1 = new ModelRenderer(this, 4, 2);
	      Shape1.addBox(2F, -7F, 2F, 10, 6, 10);
	      Shape1.setRotationPoint(1F, 18F, 1F);
	      Shape1.setTextureSize(64, 32);
	      Shape1.mirror = true;
	      setRotation(Shape1, 0F, 0F, 0F);
	      bottom = new ModelRenderer(this, 8, 1);
	      bottom.addBox(0F, -3F, 0F, 8, 2, 8);
	      bottom.setRotationPoint(4F, 20F, 4F);
	      bottom.setTextureSize(64, 32);
	      bottom.mirror = true;
	      setRotation(bottom, 0F, 0F, 0F);
	      Shape2 = new ModelRenderer(this, 32, 22);
	      Shape2.addBox(0F, 2F, 0F, 8, 2, 8);
	      Shape2.setRotationPoint(4F, 7F, 4F);
	      Shape2.setTextureSize(64, 32);
	      Shape2.mirror = true;
	      setRotation(Shape2, 0F, 0F, 0F);
	      Shape3 = new ModelRenderer(this, 0, 22);
	      Shape3.addBox(0F, 2F, 0F, 6, 4, 6);
	      Shape3.setRotationPoint(5F, 3F, 5F);
	      Shape3.setTextureSize(64, 32);
	      Shape3.mirror = true;
	      setRotation(Shape3, 0F, 0F, 0F);
	      Shape4 = new ModelRenderer(this, 32, 22);
	      Shape4.addBox(0F, 0F, 0F, 8, 2, 8);
	      Shape4.setRotationPoint(4F, 3F, 4F);
	      Shape4.setTextureSize(64, 32);
	      Shape4.mirror = true;
	      setRotation(Shape4, 0F, 0F, 0F);
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
		    Shape1.render(f5);
		    bottom.render(f5);
		    Shape2.render(f5);
		    Shape3.render(f5);
		    Shape4.render(f5);
	  }
	  
	  public void renderModel(float f) {
		    Shape1.render(f);
		    bottom.render(f);
		    Shape2.render(f);
		    Shape3.render(f);
		    Shape4.render(f);
	  }
	
	  private float range = 0.2F;
		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
		{
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);		
		}

}

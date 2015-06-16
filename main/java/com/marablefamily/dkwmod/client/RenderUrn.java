package com.marablefamily.dkwmod.client;

import org.lwjgl.opengl.GL11;

import com.marablefamily.dkwmod.DKWMod;
import com.marablefamily.dkwmod.block.ModelUrn;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderUrn extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation urnTexture = new ResourceLocation(DKWMod.modID+":textures/model/urn.png");
	
	private ModelUrn model;
	
	public RenderUrn() {
		this.model = new ModelUrn();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float)x+1.0F, (float)y+1.1875F, (float)z);
			GL11.glRotatef(180F, 0, 0, 1);
			this.bindTexture(urnTexture);
			GL11.glPushMatrix();
				this.model.renderModel(0.0625F);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
		
	}

}

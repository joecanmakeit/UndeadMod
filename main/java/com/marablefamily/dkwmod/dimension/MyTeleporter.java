package com.marablefamily.dkwmod.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MyTeleporter extends Teleporter {
	
	private final WorldServer worldServerInstance;

	public MyTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		this.worldServerInstance = par1WorldServer;
	}

	@Override
	public void placeInPortal(Entity pEntity, double p2, double p3, double p4, float p5) {
		int i = MathHelper.floor_double(pEntity.posX);
		int j = MathHelper.floor_double(pEntity.posY);
		int k = MathHelper.floor_double(pEntity.posZ);
		this.worldServerInstance.getBlock(i, j, k);    //dummy load to maybe gen chunk
		int height = this.worldServerInstance.getHeightValue(i, k);

		pEntity.setPosition( i, height, k );

		return;
	}

}

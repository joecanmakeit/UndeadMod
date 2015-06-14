package com.marablefamily.dkwmod.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntitySoul extends EntityMob {
	
	public static double sightRange = 16.0;

	public EntitySoul(World w) {
		super(w);
		this.setSize(0.5F, 1.0F);
	}
	
	protected Entity findPlayerToAttack()
    {
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, sightRange);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    }
    
    protected Item getDropItem()
    {
        return Items.bone;
    }
}

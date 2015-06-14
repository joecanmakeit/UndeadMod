package com.marablefamily.dkwmod.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySoul extends EntityMob {
	
	public static double sightRange = 16.0;
    private boolean rising = true;
    private int risingTimer = 50;
    
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
    
    protected void fall(float p_70069_1_) {}
    
	
	public void onLivingUpdate() {
		if (!this.worldObj.isRemote)
        {
            --this.risingTimer;

            if (this.risingTimer <= 0)
            {
                this.risingTimer = this.rand.nextInt(40)+20;
                this.rising = !this.rising;
            }

            if (this.getEntityToAttack() == null && this.rising)
            {
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
            else if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight())
            {
            	this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
        }
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }
		super.onLivingUpdate();
	}
}

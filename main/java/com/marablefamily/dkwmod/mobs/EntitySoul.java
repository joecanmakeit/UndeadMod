package com.marablefamily.dkwmod.mobs;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySoul extends EntityFlying implements IMob {
	
	public static double sightRange = 8.0;
    private boolean rising = true;
    private int risingTimer = 50;
    private Entity targetedEntity;
    private int aggroCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    public int courseChangeCooldown;
    private double speed = 0.02;
    
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
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.05D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }
    
    protected Item getDropItem()
    {
        return Items.bone;
    }
    
    protected void fall(float p_70069_1_) {}
    
    protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {}
    
	/*
	public void onLivingUpdate() {
		if (!this.worldObj.isRemote)
        {
			
            --this.risingTimer;

            if (this.risingTimer <= 0)
            {
                this.risingTimer = this.rand.nextInt(40)+20;
                this.rising = !this.rising;
            }

            
            if (this.getEntityToAttack() != null) {
            	if (this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight()) {
            		this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            	}
            	
            }
            else if (this.rising){
            	this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
            
        }
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }
		super.onLivingUpdate();
	}
	*/
	
	protected void updateEntityActionState() {
		
		// LOCATE TARGET
		if (this.targetedEntity != null && this.targetedEntity.isDead)
        {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null || this.aggroCooldown-- <= 0)
        {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, sightRange);

            if (this.targetedEntity != null)
            {
                this.aggroCooldown = 20;
            }
        }
        
        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < sightRange * sightRange)
        {
        	double distToTarget = this.targetedEntity.getDistanceToEntity(this);
            double d5 = this.targetedEntity.posX - this.posX;
            double d6 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double d7 = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(d5, d7)) * 180.0F / (float)Math.PI;

            if (this.canEntityBeSeen(this.targetedEntity))
            {
            	//this.waypointX = this.targetedEntity.posX;
            	//this.waypointY = this.targetedEntity.posY;
            	//this.waypointZ = this.targetedEntity.posZ;
            	this.attackEntity(targetedEntity, (float) distToTarget);
            }
        }
        else
        {
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
        }
		
		// PICK A WAYPOINT

        // Remove entity in certain situations (far from player, peaceful, etc)
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
        this.despawnEntity();
        
        // Pick random place, go to it
        double d0 = this.waypointX - this.posX;
        double d1 = this.waypointY - this.posY;
        double d2 = this.waypointZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

        if (d3 < 1.0D || d3 > 3600.0D)
        {
            this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.courseChangeCooldown-- <= 0)
        {
        	
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            d3 = (double)MathHelper.sqrt_double(d3);
            
            if (this.targetedEntity != null) {
            	this.waypointX = this.targetedEntity.posX;
            	this.waypointY = this.targetedEntity.posY;
            	this.waypointZ = this.targetedEntity.posZ;
            }

            while (!this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
            {
                this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            }
            this.motionX += d0 / d3 * speed;
            this.motionY += d1 / d3 * speed;
            this.motionZ += d2 / d3 * speed;
        }
	}
	
	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
    {
        double d4 = (this.waypointX - this.posX) / p_70790_7_;
        double d5 = (this.waypointY - this.posY) / p_70790_7_;
        double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

        for (int i = 1; (double)i < p_70790_7_; ++i)
        {
            axisalignedbb.offset(d4, d5, d6);

            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
            {
                return false;
            }
        }

        return true;
    }
	
	public boolean isOnLadder()
    {
        return false;
    }
	
	public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (p_70652_1_ instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
        }

        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                p_70652_1_.setFire(j * 4);
            }

            if (p_70652_1_ instanceof EntityLivingBase)
            {
                EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
            }

            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }

        return flag;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
    {
        if (this.attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(p_70785_1_);
        }
    }
}

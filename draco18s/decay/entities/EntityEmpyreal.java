package draco18s.decay.entities;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEmpyreal extends EntityMob
{
    /** Random offset used in floating behaviour */
    private float heightOffset = 0.5F;

    /** ticks until heightOffset is randomized */
    private int heightOffsetUpdateTime;
    private int attackPattern;
    private boolean selfHeal = true;

    public EntityEmpyreal(World par1World)
    {
        super(par1World);
        this.texture = "/mods/DecayingWorld/textures/mob/empyreal.png";
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.blaze.breathe";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.blaze.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.blaze.death";
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.isWet())
            {
                this.attackEntityFrom(DamageSource.drown, 1);
            }

            --this.heightOffsetUpdateTime;

            if (this.heightOffsetUpdateTime <= 0)
            {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
            }

            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset)
            {
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
        }

        if (this.rand.nextInt(24) == 0)
        {
            this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
        }

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }
        if(!isInvisible()) {
	        for (int i = 0; i < 2; ++i)
	        {
	            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
	        }
        }
        
        if(!this.isInvisible()) {
        	NBTTagCompound nbt = this.getEntityData();
        	int timer = nbt.getInteger("InvisTimer");
        	if(timer == 0) {
        		timer = 2400;
        	}
        	if(this.worldObj.getClosestPlayerToEntity(this, 24) != null)
        		timer++;
        	if(timer > 4801 && this.worldObj.rand.nextDouble() < 0.005) {
        		this.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 600, 0));
        		timer = 1;
        	}
        	nbt.setInteger("InvisTimer", timer);
        }
        
        if(getHealth() == getMaxHealth()) {
        	NBTTagCompound nbt = this.getEntityData();
        	nbt.setInteger("AuraTimer",Math.min(nbt.getInteger("AuraTimer"),0));
        	if(!selfHeal && nbt.getInteger("HealthOverflow") > getMaxHealth()) {
        		selfHeal = true;
        		nbt.setInteger("HealthOverflow",0);
        	}
        }

        super.onLivingUpdate();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
        else if (par2 < 30.0F)
        {
            double d0 = par1Entity.posX - this.posX;
            double d1 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double d2 = par1Entity.posZ - this.posZ;

            if (this.attackTime == 0)
            {
                ++this.attackPattern;

                if (this.attackPattern == 1)
                {
                    if(selfHeal && this.getHealth() < this.getMaxHealth()/3) {
                		this.attackPattern = 0;
                        this.attackTime = 40;
                        this.addPotionEffect(new PotionEffect(Potion.heal.getId(), 1, 0));
                	}
                    else {
                    	NBTTagCompound nbt = this.getEntityData();
                    	int timer = nbt.getInteger("AuraTimer");
                    	timer--;
                    	if(timer < -3) {
                    		timer = 13;
                    		this.attackTime = 100;
                    		AxisAlignedBB par2AxisAlignedBB = AxisAlignedBB.getBoundingBox(this.posX-16,this.posY-16,this.posZ-16,this.posX+16,this.posY+16,this.posZ+16);
                    		//par2AxisAlignedBB.expand(16, 16, 16);
                    		List<Entity> ents = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, par2AxisAlignedBB);
                    		if(ents.size() > 0) {
                    			for (int entind = ents.size() - 1; entind >= 0; entind--) {
            						Entity e = (Entity)ents.get(entind);
            						if (e instanceof EntityLiving) {
            							e.setFire(2);
            							((EntityLiving) e).addPotionEffect(new PotionEffect(Potion.blindness.getId(), 400, 0));
            						}
            					}
                    		}
                    	}
                    	else {
	                        this.attackTime = 60;
	                        this.func_70844_e(true);
                    	}
                    	nbt.setInteger("AuraTimer", timer);
                    }
                }
                else if (this.attackPattern <= 3)
                {
                    this.attackTime = 6;
                }
                else
                {
                    this.attackTime = 100;
                    this.attackPattern = 0;
                    this.func_70844_e(false);
                }
                
                if (this.attackPattern > 1)
                {
                	
                	if(true) {
	                    float f1 = MathHelper.sqrt_float(par2) * 0.5F;
	                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
	
	                    for (int i = 0; i < 1; ++i)
	                    {
	                        EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
	                        entitysmallfireball.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
	                        this.worldObj.spawnEntityInWorld(entitysmallfireball);
	                    }
                	}
                }
            }

            this.rotationYaw = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.hasAttacked = true;
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1) {}

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.blazeRod.itemID;
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return this.func_70845_n();
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        if (par1)
        {
            int j = this.rand.nextInt(2 + par2);

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(Item.blazeRod.itemID, 1);
            }
        }
    }

    public boolean func_70845_n()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_70844_e(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return 4;
    }
}

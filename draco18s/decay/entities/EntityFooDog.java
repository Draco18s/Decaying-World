package draco18s.decay.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFooDog extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;

    /** true is the wolf is wet else false */
    private boolean isShaking;
    private boolean field_70928_h;

    private boolean isSitting = false;
    private EntityAIBase wanderTaskA = new EntityAIWander(this, 0.28F);
    private EntityAIBase attackTaskA = new EntityAIAttackOnCollide(this, 0.28F, true);
    private EntityAIBase wanderTaskB = new EntityAIWander(this, 0.19F);
    private EntityAIBase attackTaskB = new EntityAIAttackOnCollide(this, 0.19F, true);
    private EntityAIBase leapTask = new EntityAILeapAtTarget(this, 0.4F);

    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public EntityFooDog(World par1World)
    {
        super(par1World);
        this.texture = "/mods/DecayingWorld/textures/mob/foodog.png";
        this.setSize(0.6F, 0.8F);
        this.moveSpeed = 0.28F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        //this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        //this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        setAngry(false);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLiving par1EntityLiving)
    {
    	if(isStone() == 3) {
    		System.out.println("Lost stone form");
    		setStone(1);
    		this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
    		this.moveSpeed = 0.28F;
            this.tasks.addTask(2, leapTask);
            this.tasks.addTask(3, attackTaskA);
            this.tasks.addTask(4, wanderTaskA);
    	}
    	setAngry(true);
    	setSitting(false);
    	super.setAttackTarget(par1EntityLiving);
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
    }

    public int getMaxHealth()
    {
        return 15;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
        this.dataWatcher.addObject(20, new Byte((byte)3));
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
    	int s = isStone();
    	if(s >= 3) {
    		return "/mods/DecayingWorld/textures/mob/statuedog.png";
    	}
    	else if(s == 2) {
    		return "/mods/DecayingWorld/textures/mob/stonedog.png";
    	}
    	else {
    		return super.getTexture(); //foodog.png
    	}
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
        par1NBTTagCompound.setInteger("Stoneform", isStone());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
        setStone(par1NBTTagCompound.getInteger("Stoneform"));
        switch(isStone()) {
        	case 0:
        		setStone(3);
        	case 1:
	            this.tasks.addTask(2, leapTask);
	            this.tasks.addTask(3, attackTaskA);
	            this.tasks.addTask(4, wanderTaskA);
        		break;
        	case 2:
	            this.tasks.addTask(2, leapTask);
	            this.tasks.addTask(3, attackTaskB);
	            this.tasks.addTask(4, wanderTaskB);
        		break;
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.wolf.growl";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return (isStone() > 2) ? 0.0F : 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return -1;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround)
        {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }
        if(this.isPotionActive(Potion.poison)) {
        	this.curePotionEffects(new ItemStack(Item.bucketMilk));
        }
        if(this.entityToAttack == null && this.findPlayerToAttack() == null) {
        	setAngry(false);
        }
        switch(isStone()) {
        	case 1:
	        	this.moveSpeed = 0.28F;
	        	//this.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 10, 0));
	        	if(getHealth() <= getMaxHealth() / 2) {
	        		setStone(2);
		    		this.tasks.removeTask(wanderTaskA);
		    		this.tasks.removeTask(attackTaskA);
		            this.tasks.addTask(3, attackTaskB);
		            this.tasks.addTask(4, wanderTaskB);
	            }
	        	break;
        	case 2:
            	this.moveSpeed = 0.19F;
            	this.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 10, 3));
            	if(getHealth() == getMaxHealth()) {
            		setStone(1);
		    		this.tasks.removeTask(wanderTaskB);
		    		this.tasks.removeTask(attackTaskB);
		            this.tasks.addTask(3, attackTaskA);
		            this.tasks.addTask(4, wanderTaskA);
	            }
            	break;
        	case 3:
            	this.moveSpeed = 0.0F;
            	break;
        }
        if(!isAngry() && isStone() < 3) {
	        NBTTagCompound nbt = this.getEntityData();
	    	if(nbt.getInteger("HealthOverflow") >= getMaxHealth()*2-1) {
	    		System.out.println("Gained stone form");
	    		setStone(3);
	    		this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
	            this.tasks.removeTask(leapTask);
	    		this.tasks.removeTask(wanderTaskA);
	    		this.tasks.removeTask(attackTaskA);
	    		this.tasks.removeTask(wanderTaskB);
	    		this.tasks.removeTask(attackTaskB);
	    		this.setPathToEntity((PathEntity)null);
	    	}
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.isWet() && isStone() == 0)
        {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if (isStone() == 0 && (this.isShaking || this.field_70928_h) && this.field_70928_h)
        {
            if (this.timeWolfIsShaking == 0.0F)
            {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F)
            {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F)
            {
                float f = (float)this.boundingBox.minY;
                int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle("splash", this.posX + (double)f1, (double)(f + 0.8F), this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean getWolfShaking()
    {
        return this.isShaking;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Used when calculating the amount of shading to apply while the wolf is shaking.
     */
    public float getShadingWhileShaking(float par1)
    {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1) / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float par1, float par2)
    {
        float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1 + par2) / 1.8F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        else if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float)Math.PI) * MathHelper.sin(f2 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float par1)
    {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
        	if(isStone() == 3) {
        		int n = par2 / 2;
        		if(n == 0) {
        			n = this.rand.nextInt(2);
        		}
        		return super.attackEntityFrom(par1DamageSource, n);
        	}
        	return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
    	int i = 3;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 8)
        {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation()
    {
        return 1.5393804F;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry()
    {
    	//return isAngry;
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    public int isStone() {
    	return this.dataWatcher.getWatchableObjectByte(20);
    }
    
    public void setStone(int s) {
    	this.dataWatcher.updateObject(20, Byte.valueOf((byte)s));
    	if(s==3) {
    		setSitting(true);
    	}
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        setSitting(false);
    }

    public void func_70918_i(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(19);

        if (par1)
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
        }
    }

	public boolean isSitting() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 0 : super.getVerticalFaceSpeed();
    }

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
}

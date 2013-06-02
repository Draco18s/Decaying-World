package draco18s.decay.entities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IThrowableEntity;

public class EntityLifeBomb extends EntityThrowable implements IThrowableEntity {
	public EntityLifeBomb(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntityLifeBomb(World par1World) {
		super(par1World);
	}

	@Override
	public void setThrower(Entity entity) {
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
        byte b0 = 10;
		if (movingobjectposition.entityHit != null)
        {
            if(movingobjectposition.entityHit instanceof EntityLiving) {
                EntityLiving el = (EntityLiving) movingobjectposition.entityHit;
                el.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), b0);
            	el.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 180, 1));
            }
        }
		else {
			AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
	        List list1 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, axisalignedbb);

	        if (list1 != null && !list1.isEmpty())
	        {
	            Iterator iterator = list1.iterator();

	            while (iterator.hasNext())
	            {
	                EntityLiving entityliving = (EntityLiving)iterator.next();
	                double d0 = this.getDistanceSqToEntity(entityliving);

	                if (d0 < 16.0D)
	                {
	                    double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
	                    entityliving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), (int)(b0*d1));
	                    entityliving.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 180, 1));
	                }
	            }
	        }
		}
		this.worldObj.newExplosion(this.getThrower(), this.posX, this.posY, this.posZ, 0, false, true);

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
	}
}

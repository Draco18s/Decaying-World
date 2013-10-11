package draco18s.decay.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IThrowableEntity;

public class EntitySolidifier extends EntityThrowable implements IThrowableEntity {
	public EntitySolidifier(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntitySolidifier(World par1World) {
		super(par1World);
	}

	public EntitySolidifier(World par2World, EntityPlayer par3EntityPlayer) {
		super(par2World, par3EntityPlayer);
	}

	@Override
	public void setThrower(Entity entity) {
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (movingobjectposition.entityHit != null)
        {
            byte b0 = 5;
            if(movingobjectposition.entityHit instanceof EntityLiving) {
            	EntityLiving el = (EntityLiving)movingobjectposition.entityHit;
	            el.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
	            el.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 300, 1));
            }
        }
		else if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			int x = movingobjectposition.blockX;
			int y = movingobjectposition.blockY;
			int z = movingobjectposition.blockZ;
			int wID, meta;
			for(int i=-4; i<=4; i++) {
				for(int j=-4; j<=4; j++) {
					for(int k=-4; k<=4; k++) {
						int d = i*i + j*j + k*k;
						if(d <= 16) {
							wID = worldObj.getBlockId(x+i, y+j, z+k);
							meta = worldObj.getBlockMetadata(x+i, y+j, z+k);
							if((wID == Block.waterStill.blockID || wID == Block.waterMoving.blockID) && (meta|8) == 8) {
								worldObj.setBlock(x+i, y+j, z+k, Block.ice.blockID, 0, 3);
							}
							else if(wID == Block.lavaStill.blockID || wID == Block.lavaMoving.blockID) {
								if(meta == 0)
									worldObj.setBlock(x+i, y+j, z+k, Block.obsidian.blockID, 0, 3);
								else
									worldObj.setBlock(x+i, y+j, z+k, Block.cobblestone.blockID, 0, 3);
							}
						}
					}
				}
			}
		}

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

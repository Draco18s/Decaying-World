package draco18s.decay.blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;
import draco18s.decay.NegativeDamage;
import draco18s.decay.PositiveDamage;
import draco18s.decay.entities.DeathCryEnt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DeathCrystal extends BlockContainer
{
    public DeathCrystal(int par1)
    {
        super(par1, Material.rock);
        setHardness(2F);
        setUnlocalizedName("Death Crystal");
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(2.0F);
        setResistance(5.0F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float f = 0.01F;
        return AxisAlignedBB.getAABBPool().getAABB((double)(par2 + f), (double)(par3 + f), (double)(par4 + f), (double)((float)(par2 + 1)-f), (double)((float)(par3 + 1) - f), (double)((float)(par4 + 1)-f));
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:deathcrystal");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return DecayingWorld.deathShard.itemID;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldObj, int x, int y, int z, Entity e)
    {
        if (!worldObj.isRemote)
        {
            if (e instanceof EntityLiving)
            {
            	NBTTagCompound nbt = e.getEntityData();
                int timer = Math.max(nbt.getInteger("ExpDrainTimer"), 0) + 2;
                if (timer > 180) {
                	timer = new Random().nextInt(60);
                	EntityLiving var5 = (EntityLiving)e;
                	if((DecayingWorld.evilmobs(var5))) {
                		int hpo = nbt.getInteger("HealthOverflow");
                		int mhp = var5.getMaxHealth();
                		if(hpo > mhp*2)
                			hpo = mhp*2;
                		nbt.setInteger("HealthOverflow", hpo);
                	}
                	else {
                		e.attackEntityFrom(NegativeDamage.type, 1);
		                TileEntity te = worldObj.getBlockTileEntity(x, y, z);
		                if(te instanceof DeathCryEnt) {
		                	DeathCryEnt cry = (DeathCryEnt)te;
		                	cry.expCount++;
		                }

		                if(e instanceof EntityPlayer) {
		            		EntityPlayer player = (EntityPlayer)e;
		            		System.out.println("Total exp: " + player.experienceTotal);
		            	}
                	}
                }
                nbt.setInteger("ExpDrainTimer", timer);
            }
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new DeathCryEnt();
	}

	@Override
	public void breakBlock(World worldObj, int x, int y, int z, int par5, int par6)
    {
        TileEntity te = worldObj.getBlockTileEntity(x, y, z);
        if(te instanceof DeathCryEnt) {
        	DeathCryEnt cry = (DeathCryEnt)te;
        	if(cry.expCount >= 8) {
                dropXpOnBlockBreak(worldObj, x, y, z, cry.expCount/8);
        	}
        }
        super.breakBlock(worldObj, x, y, z, par5, par6);
    }

	@Override
	public boolean onBlockActivated(World worldObj, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if(!worldObj.isRemote) {
			TileEntity te = worldObj.getBlockTileEntity(x, y, z);
	        if(te instanceof DeathCryEnt) {
	        	DeathCryEnt cry = (DeathCryEnt)te;
	        	if(cry.delay <= 0 && cry.expCount >= 8) {
	        		int e = cry.expCount/8;
	        		e = Math.max(e/4,1);
	        		e = worldObj.rand.nextInt(e) + worldObj.rand.nextInt(e);
					if(e < 1) {
						e = 1;
					}
	        		if(e*8 > cry.expCount) {
	        			e = cry.expCount/8;
	        		}
	        		cry.expCount -= e * 8;
	        		cry.delay = 1200;
	                dropXpOnBlockBreak(worldObj, x, y, z, e);
	    	        return true;
	        	}
	        }
		}
		return false;
    }
}

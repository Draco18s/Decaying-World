package draco18s.decay.blocks.decays;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class NitroDecay extends Block
{
    public NitroDecay(int par1)
    {
        super(par1, Material.rock);
        setHardness(0.75F);
        setUnlocalizedName("Nitro Decay");
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabBlock);
        setResistance(0.5F);
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:nitro");
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	int i,j,k,s;
    	if(world.getBlockMetadata(x, y, z) == 0) {
    		if(rand.nextInt(3) == 0) {
	    		int p = 0;
	    		for (int i1 = x - 6; i1 <= x + 6; ++i1)
	            {
	                for (int j1 = y - 6; j1 <= y + 6; ++j1)
	                {
	                    for (int k1 = z - 3; k1 <= z + 3; ++k1)
	                    {
	                    	s = world.getBlockId(i1, j1, k1);
	                        if (s == DecayingWorld.nitroDecay.blockID || s == DecayingWorld.nitroDecayGlowing.blockID)
	                        {
	                        	++p;
	                        }
	                    }
	                }
	            }
	    		if (p < 5)
                {
                    //spread
                	for(int l = 0; l < 4; l++) {
    			    	i = rand.nextInt(3) - 1;
    			    	j = rand.nextInt(3) - 1;
    			    	k = rand.nextInt(3) - 1;
    			    	s = world.getBlockId(x+i, y+j, z+k);
    			    	if(s == Block.glass.blockID) {
    			    		world.setBlock(x+i, y+j, z+k, DecayingWorld.nitroDecay.blockID, 0, 3);
    			    	}
    		    	}
                }
                else {
                	i = (rand.nextInt(9) - 4) * 2;
			    	j = rand.nextInt(17) - 8;
			    	k = (rand.nextInt(9) - 4) * 2;
			    	if(i != 0)
				    	i += 8 * i%Math.abs(i);
			    	if(k != 0)
				    	k += 8 * k%Math.abs(k);
			    	s = world.getBlockId(x+i, y+j, z+k);
			    	if(s == Block.glass.blockID) {
			    		world.setBlock(x+i, y+j, z+k, DecayingWorld.nitroDecay.blockID, 0, 3);
			    	}
                }
    	    	world.setBlock(x, y, z, blockID, 1, 3);
    		}
    	}
    	if (blockID == DecayingWorld.nitroDecayGlowing.blockID)
        {
        	world.setBlock(x, y, z, DecayingWorld.nitroDecay.blockID, world.getBlockMetadata(x, y, z), 3);
        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer par2EntityPlayer, int x, int y, int z, int meta)
    {
    	if (!EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
        {
    		world.newExplosion(null, x, y, z, 3F, true, true);
    	}
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion par5Explosion)
    {
        if (new Random().nextDouble() < 0.05)
        {
            world.newExplosion(null, x, y, z, 4F, true, true);
        }
    }
    
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.glow(par1World, par2, par3, par4);
        super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }
    
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        this.glow(par1World, par2, par3, par4);
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }
    
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        this.glow(par1World, par2, par3, par4);
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    private void glow(World world, int x, int y, int z)
    {
        this.sparkle(world, x, y, z);
        
        if (blockID == DecayingWorld.nitroDecay.blockID)
        {
        	world.setBlock(x, y, z, DecayingWorld.nitroDecayGlowing.blockID, world.getBlockMetadata(x, y, z), 3);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random par5Random)
    {
        if (blockID == DecayingWorld.nitroDecayGlowing.blockID)
        {
            this.sparkle(world, x, y, z);
        }
    }
    
    private void sparkle(World par1World, int par2, int par3, int par4)
    {
        Random random = par1World.rand;
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double d1 = (double)((float)par2 + random.nextFloat());
            double d2 = (double)((float)par3 + random.nextFloat());
            double d3 = (double)((float)par4 + random.nextFloat());

            if (l == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
            {
                d2 = (double)(par3 + 1) + d0;
            }

            if (l == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4))
            {
                d2 = (double)(par3 + 0) - d0;
            }

            if (l == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1))
            {
                d3 = (double)(par4 + 1) + d0;
            }

            if (l == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1))
            {
                d3 = (double)(par4 + 0) - d0;
            }

            if (l == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4))
            {
                d1 = (double)(par2 + 1) + d0;
            }

            if (l == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4))
            {
                d1 = (double)(par2 + 0) - d0;
            }

            if (d1 < (double)par2 || d1 > (double)(par2 + 1) || d2 < 0.0D || d2 > (double)(par3 + 1) || d3 < (double)par4 || d3 > (double)(par4 + 1))
            {
                par1World.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}

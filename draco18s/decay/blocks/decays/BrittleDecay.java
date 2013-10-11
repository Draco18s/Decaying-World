package draco18s.decay.blocks.decays;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BrittleDecay extends Block
{
	Icon blockIcons[];
    public BrittleDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Brittle Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:brittle0");
        this.blockIcons = new Icon[15];

        for (int i = 0; i < this.blockIcons.length; ++i)
        {
            this.blockIcons[i] = iconRegister.registerIcon("DecayingWorld:brittle"+i);
        }
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
    	int r = (int)(par5Random.nextDouble() * 6.1);

        switch (r)
        {
            case 0:
                x++;
                break;

            case 1:
                x--;
                break;

            case 2:
                y++;
                break;

            case 3:
                y--;
                break;

            case 4:
                z++;
                break;

            case 5:
                z--;
                break;

            default:
                y = par5Random.nextInt(60) + 5;
                break;
        }

        int a = world.getBlockId(x, y, z);


        if (a == Block.stone.blockID) {
            world.setBlock(x, y, z, this.blockID,0, 3);
        }
        else if(a == Block.oreCoal.blockID) {
            world.setBlock(x, y, z, this.blockID,1, 3);
        }
        else if(a == Block.oreIron.blockID) {
            world.setBlock(x, y, z, this.blockID,2, 3);
        }
        else if(a == Block.oreGold.blockID) {
            world.setBlock(x, y, z, this.blockID,3, 3);
        }
        else if(a == Block.oreLapis.blockID) {
            world.setBlock(x, y, z, this.blockID,4, 3);
        }
        else if(a == Block.oreRedstone.blockID || a == Block.oreRedstoneGlowing.blockID) {
            world.setBlock(x, y, z, this.blockID,5, 3);
        }
        else if(a == Block.oreEmerald.blockID) {
            world.setBlock(x, y, z, this.blockID,6, 3);
        }
        else if(a == Block.oreDiamond.blockID) {
            world.setBlock(x, y, z, this.blockID,7, 3);
        }
        else if(a == Block.dirt.blockID) {
            world.setBlock(x, y, z, this.blockID,8, 3);
        }
        else if(a == Block.obsidian.blockID) {
            world.setBlock(x, y, z, this.blockID,8, 3);
        }
        else if(a == Block.netherrack.blockID) {
            world.setBlock(x, y, z, this.blockID,10, 3);
        }
        else if(a == Block.oreNetherQuartz.blockID) {
            world.setBlock(x, y, z, this.blockID,11, 3);
        }
        else if(a == Block.glowStone.blockID) {
            world.setBlock(x, y, z, this.blockID,12, 3);
        }
        else if(a == Block.cobblestone.blockID) {
            world.setBlock(x, y, z, this.blockID,13, 3);
        }
        else if(a == Block.bedrock.blockID) {
            world.setBlock(x, y, z, this.blockID,14, 3);
        }
        else if(a == Block.grass.blockID) {
            world.setBlock(x, y, z, this.blockID,15, 3);
        }
        else if(a == Block.sandStone.blockID) {
            world.setBlock(x, y, z, Block.sand.blockID,0, 3);
        }
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    @Override
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
        //this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
        //System.out.println("Walked on");
        if(!par1World.isRemote)
        	par1World.setBlockToAir(par2, par3, par4);
    }
    
    public Icon getIcon(int par1, int par2)
    {
    	if(par2 == 15) {
    		return Block.grass.getIcon(par1, par2);
    	}
    	else if (par2 < 0 || par2 >= this.blockIcons.length)
        {
            par2 = 0;
        }

        return this.blockIcons[par2];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	int m = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    	if(m == 15) {
	        int l = 0;
	        int i1 = 0;
	        int j1 = 0;
	
	        for (int k1 = -1; k1 <= 1; ++k1)
	        {
	            for (int l1 = -1; l1 <= 1; ++l1)
	            {
	                int i2 = par1IBlockAccess.getBiomeGenForCoords(par2 + l1, par4 + k1).getBiomeGrassColor();
	                l += (i2 & 16711680) >> 16;
	                i1 += (i2 & 65280) >> 8;
	                j1 += i2 & 255;
	            }
	        }
	
	        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    	}
    	else {
    		return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    	}
    }
}

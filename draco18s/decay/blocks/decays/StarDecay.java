package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class StarDecay extends Block
{
    public StarDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Star Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:starry");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int mymeta = world.getBlockMetadata(x, y, z);

        if (mymeta == 3)
        {
            int a = world.getBlockId(x + 1, y, z);

            if (a == this.blockID)
            {
                world.setBlock(x + 1, y, z, 3);
            }

            a = world.getBlockId(x - 1, y, z);

            if (a == this.blockID)
            {
                world.setBlock(x - 1, y, z, 3);
            }

            a = world.getBlockId(x, y, z + 1);

            if (a == this.blockID)
            {
                world.setBlock(x, y, z + 1, 3);
            }

            a = world.getBlockId(x, y, z - 1);

            if (a == this.blockID)
            {
                world.setBlock(x, y, z - 1, 3);
            }

            a = world.getBlockId(x, y + 1, z);

            if (a == this.blockID)
            {
                world.setBlock(x, y + 1, z, 3);
            }

            a = world.getBlockId(x, y - 1, z);

            if (a == this.blockID)
            {
                world.setBlock(x, y - 1, z, 3);
            }

            if (y < 1)
            {
                world.setBlock(x, y, z, DecayingWorld.starFissure.blockID);
            }
            else
            {
                world.setBlock(x, y, z, 0);
            }

            return;
        }

        if (mymeta < 2 && world.getBlockId(x, y + 1, z) != this.blockID && par5Random.nextDouble() < 0.05D)
        {
            world.setBlock(x + 1, y, z, this.blockID);
            world.setBlock(x - 1, y, z, this.blockID);
            world.setBlock(x, y, z + 1, this.blockID);
            world.setBlock(x, y, z - 1, this.blockID);
            world.setBlock(x, y, z, this.blockID, 2, 3);
            return;
        }
        else if (mymeta == 1)
        {
            return;
        }
        else if (y < 1)
        {
            world.setBlock(x + 1, y, z, this.blockID, 3, 3);
            world.setBlock(x - 1, y, z, this.blockID, 3, 3);
            world.setBlock(x, y, z + 1, this.blockID, 3, 3);
            world.setBlock(x, y, z - 1, this.blockID, 3, 3);
            world.setBlock(x, y + 1, z - 1, this.blockID, 3, 3);
            world.setBlock(x, y, z, DecayingWorld.starFissure.blockID);
            return;
        }
        else
        {
            int a1 = world.getBlockId(x - 1, y - 1, z);
            int a2 = world.getBlockId(x + 1, y - 1, z);
            int a3 = world.getBlockId(x, y - 1, z - 1);
            int a4 = world.getBlockId(x, y - 1, z + 1);

            if (a1 == this.blockID || a2 == this.blockID || a3 == this.blockID || a4 == this.blockID)
            {
                world.setBlock(x, y - 1, z, this.blockID);
                world.setBlock(x, y - 2, z, this.blockID);
            }
            else
            {
                world.setBlock(x, y - 1, z, this.blockID);
            }

            world.setBlock(x, y, z, this.blockID, 1, 3);
            return;
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int ID)
    {
        if (ID == 0 || (ID == blockID && world.getBlockId(x, y, z) == 3))
        {
            world.scheduleBlockUpdate(x, y, z, ID, 1);
        }
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

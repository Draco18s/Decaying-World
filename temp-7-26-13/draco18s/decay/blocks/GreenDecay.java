package draco18s.decay.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GreenDecay extends Block
{
    public static boolean fallInstantly = false;
    private static final double spreadrate = 0.5D;

    public GreenDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Green Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:green");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int a;

        if (par5Random.nextDouble() < 0.5D)  //tick rate
        {
            if (par5Random.nextDouble() < spreadrate)  //spread rate
            {
                a = world.getBlockId(x + 1, y, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x + 1, y, z, this.blockID);
                }

                a = world.getBlockId(x - 1, y, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x - 1, y, z, this.blockID);
                }

                a = world.getBlockId(x, y, z + 1);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y, z + 1, this.blockID);
                }

                a = world.getBlockId(x, y, z - 1);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y, z - 1, this.blockID);
                }

                a = world.getBlockId(x, y + 1, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y + 1, z, this.blockID);
                }

                a = world.getBlockId(x, y - 1, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y - 1, z, this.blockID);
                }
            }

            world.setBlock(x, y, z, 0);
            tryToFall(world, x, y, z, par5Random);
        }
    }

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        tryToFall(par1World, par2, par3, par4, new Random());
    }

    private void tryToFall(World world, int x, int y, int z, Random rand)
    {
        if (canFallBelow(world, x, y - 1, z) && y >= 0)
        {
            byte var8 = 32;
            int a;

            if (rand.nextDouble() < spreadrate)
            {
                a = world.getBlockId(x + 1, y, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x + 1, y, z, this.blockID);
                }

                a = world.getBlockId(x - 1, y, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x - 1, y, z, this.blockID);
                }

                a = world.getBlockId(x, y, z + 1);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y, z + 1, this.blockID);
                }

                a = world.getBlockId(x, y, z - 1);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y, z - 1, this.blockID);
                }

                a = world.getBlockId(x, y + 1, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y + 1, z, this.blockID);
                }

                a = world.getBlockId(x, y - 1, z);

                if (a != 0 && a != this.blockID && a != 7)
                {
                    world.setBlock(x, y - 1, z, this.blockID);
                }
            }

            world.setBlock(x, y, z, 0);
        }
    }

    public static boolean canFallBelow(World par0World, int par1, int par2, int par3)
    {
        int var4 = par0World.getBlockId(par1, par2, par3);

        if (var4 == 0)
        {
            return true;
        }
        else if (var4 == Block.fire.blockID)
        {
            return true;
        }
        else
        {
            Material var5 = Block.blocksList[var4].blockMaterial;
            return var5 == Material.water ? true : var5 == Material.lava;
        }
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

package draco18s.decay.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class VolcanoBuilder extends Block
{
    public VolcanoBuilder(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setHardness(50.0F);
        setResistance(2000.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Volcano Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = Block.obsidian.getBlockTextureFromSide(0);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int ID)
    {
        world.scheduleBlockUpdate(x, y, z, this.blockID, 6 + new Random().nextInt(6));
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int itter = 0;
        int m = world.getBlockMetadata(x, y, z);

        if (m == 0)
        {
            world.setBlock(x, y, z, 0);
            return;
        }

        if (!world.isBlockOpaqueCube(x, y - 1, z))
        {
            world.setBlock(x, y, z, 0);
            int b = 0;

            while (!world.isBlockOpaqueCube(x, y - b - 1, z))
            {
                ++b;
            }

            world.setBlock(x, y - b, z, this.blockID, m, 3);
        }

        int wID = world.getBlockId(x, y + 1, z);

        while (par5Random.nextDouble() < (1 - 0.5 * itter))
        {
            ++itter;
        }

        for (++itter; itter > 0; itter--)
        {
            if (wID == this.blockID || wID == 49)
            {
                int i = x;
                int j = y;
                int k = z;

                switch (m)
                {
                    case 1:
                        x++;
                        break;

                    case 2:
                        z++;
                        break;

                    case 3:
                        x--;
                        break;

                    case 4:
                        z--;
                        break;
                }

                if (world.isBlockOpaqueCube(x, y, z))
                {
                    world.setBlock(i, j, k, 49);
                    //System.out.println("Encountered solid block, turning into obsidian");
                    return;
                }

                //System.out.println("itterations: " + itter);
                world.setBlock(i, j, k, 49);

                if (itter > 1)
                {
                    //System.out.println("first several itterations: " + m);
                    switch (m)
                    {
                        case 1:
                            world.setBlock(i, j, k + 1, this.blockID, 2, 3);
                            world.setBlock(i, j, k - 1, this.blockID, 4, 3);
                            break;

                        case 2:
                            world.setBlock(i + 1, j, k, this.blockID, 1, 3);
                            world.setBlock(i - 1, j, k, this.blockID, 3, 3);
                            break;

                        case 3:
                            world.setBlock(i, j, k + 1, this.blockID, 2, 3);
                            world.setBlock(i, j, k - 1, this.blockID, 4, 3);
                            break;

                        case 4:
                            world.setBlock(i + 1, j, k, this.blockID, 1, 3);
                            world.setBlock(i - 1, j, k, this.blockID, 3, 3);
                            break;
                    }
                }
                else
                {
                    switch (m)
                    {
                        case 1:
                            world.setBlock(i, y, z + 1, this.blockID, m, 3);
                            world.setBlock(i, y, z - 1, this.blockID, m, 3);
                            break;

                        case 2:
                            world.setBlock(x + 1, y, k, this.blockID, m, 3);
                            world.setBlock(x - 1, y, k, this.blockID, m, 3);
                            break;

                        case 3:
                            world.setBlock(i, y, z + 1, this.blockID, m, 3);
                            world.setBlock(i, y, z - 1, this.blockID, m, 3);
                            break;

                        case 4:
                            world.setBlock(x + 1, y, k, this.blockID, m, 3);
                            world.setBlock(x - 1, y, k, this.blockID, m, 3);
                            break;
                    }
                }

                world.setBlock(x, y, z, this.blockID, m, 3);

                if (!world.isBlockOpaqueCube(x, y - 1, z))
                {
                    world.setBlock(x, y, z, 0);
                    int b = 0;

                    while (!world.isBlockOpaqueCube(x, y - b - 1, z))
                    {
                        ++b;
                    }

                    world.setBlock(x, y - b, z, this.blockID, m, 3);

                    switch (m)
                    {
                        case 1:
                            world.setBlock(i, j, k + 1, this.blockID, 2, 3);
                            world.setBlock(i, j, k - 1, this.blockID, 4, 3);
                            break;

                        case 2:
                            world.setBlock(i + 1, j, k, this.blockID, 1, 3);
                            world.setBlock(i - 1, j, k, this.blockID, 3, 3);
                            break;

                        case 3:
                            world.setBlock(i, j, k + 1, this.blockID, 2, 3);
                            world.setBlock(i, j, k - 1, this.blockID, 4, 3);
                            break;

                        case 4:
                            world.setBlock(i + 1, j, k, this.blockID, 1, 3);
                            world.setBlock(i - 1, j, k, this.blockID, 3, 3);
                            break;
                    }
                }
            }
            else if (wID != 0)
            {
                //System.out.println("Block above is not air, becoming obsidian");
                world.setBlock(x, y, z, 49);
            }
        }
    }

    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        dropBlockAsItem(par1World, par3, par4, par5, 49, 1);
        return;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        int n = par9;
        int w = world.getBlockId(x + 1, y, z);

        if (w == 10 || w == 11)
        {
            n = 3;
        }
        else if (w == this.blockID)
        {
            n = world.getBlockMetadata(x + 1, y, z);
        }

        w = world.getBlockId(x - 1, y, z);

        if (w == 10 || w == 11)
        {
            n = 1;
        }
        else if (w == this.blockID)
        {
            n = world.getBlockMetadata(x - 1, y, z);
        }

        w = world.getBlockId(x, y, z + 1);

        if (w == 10 || w == 11)
        {
            n = 4;
        }
        else if (w == this.blockID)
        {
            n = world.getBlockMetadata(x, y, z + 1);
        }

        w = world.getBlockId(x, y, z - 1);

        if (w == 10 || w == 11)
        {
            n = 2;
        }
        else if (w == this.blockID)
        {
            n = world.getBlockMetadata(x, y, z - 1);
        }

        if (n == 0)
        {
            world.scheduleBlockUpdate(x, y, z, this.blockID, 1);
        }

        return n;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 49;
    }
}

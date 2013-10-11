package draco18s.decay.blocks.decays;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class YellowDecay extends Block
{
    public static boolean fallInstantly = false;

    public YellowDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Yellow Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:yellow");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int i = x;
        int j = y;
        int k = z;
        int r = 0;

        for (int f = 15 + par5Random.nextInt(6); f >= 0; f--)
        {
            r = par5Random.nextInt(24);
            x = i;
            y = j;
            z = k;

            switch (r)
            {
                case 0:
                    x++;
                    break;

                case 1:
                    x--;
                    break;

                case 2:
                    x++;
                    z--;
                    break;

                case 3:
                    x++;
                    z++;
                    break;

                case 4:
                    z++;
                    break;

                case 5:
                    z--;
                    break;

                case 6:
                    x--;
                    z++;
                    break;

                case 7:
                    x--;
                    z--;
                    break;

                case 8:
                    x += 2;
                    break;

                case 9:
                    x += 2;
                    z--;
                    break;

                case 10:
                    x += 2;
                    z -= 2;
                    break;

                case 11:
                    x++;
                    z -= 2;
                    break;

                case 12:
                    z -= 2;
                    break;

                case 13:
                    x--;
                    z -= 2;
                    break;

                case 14:
                    x -= 2;
                    z -= 2;
                    break;

                case 15:
                    x -= 2;
                    z--;
                    break;

                case 16:
                    x -= 2;
                    break;

                case 17:
                    x -= 2;
                    z++;
                    break;

                case 18:
                    z += 2;
                    x -= 2;
                    break;

                case 19:
                    x--;
                    z += 2;
                    break;

                case 20:
                    z += 2;
                    break;

                case 21:
                    z += 2;
                    x++;
                    break;

                case 22:
                    z += 2;
                    x += 2;
                    break;

                case 23:
                    z++;
                    x += 2;
                    break;
            }

            y--;
            int a = world.getBlockId(x, y, z);

            if (a != 7)
            {
                world.setBlock(x, y, z, 0);
            }
            else
            {
                world.setBlock(i, j, k, 0);
            }
        }

        world.setBlock(i, j - 1, k, 0);
        tryToFall(world, i, j, k);

        if (par5Random.nextDouble() < 0.5)
        {
            updateTick(world, x, y, z, par5Random);
        }
    }

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        tryToFall(par1World, par2, par3, par4);
    }

    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        tryToFall(par1World, par2, par3, par4);
    }

    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
        if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte var8 = 32;

            if (!fallInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
            {
                if (!par1World.isRemote)
                {
                    EntityFallingSand var9 = new EntityFallingSand(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.blockID, par1World.getBlockMetadata(par2, par3, par4));
                    this.onStartFalling(var9);
                    par1World.spawnEntityInWorld(var9);
                }
            }
            else
            {
                par1World.setBlock(par2, par3, par4, 0);

                while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this.blockID);
                }
            }
        }
    }

    protected void onStartFalling(EntityFallingSand par1EntityFallingSand) {}

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

package draco18s.decay.blocks.decays;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SilverDecay extends Block
{
    public SilverDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Silver Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:silver");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int r = (int)(par5Random.nextDouble() * 6);
        int i = x;
        int j = y;
        int k = z;

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
                x++;
                y++;
                break;
        }

        int a = world.getBlockId(x, y, z);

        if (a == Block.stone.blockID)
        {
            world.setBlock(x, y, z, this.blockID);
            world.setBlock(i, j, k, 97);
        }
        else if (a == 97 || a == 7 || par5Random.nextDouble() < 0.02)
        {
            if (world.getBlockId(i - 1, j, k) != 1 && world.getBlockId(i + 1, j, k) != 1 && world.getBlockId(i, j - 1, k) != 1 && world.getBlockId(i, j + 1, k) != 1 && world.getBlockId(i, j, k - 1) != 1 && world.getBlockId(i, j, k + 1) != 1)
            {
                world.setBlock(i, j, k, 97);
            }
        }
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            EntitySilverfish var6 = new EntitySilverfish(par1World);
            var6.setLocationAndAngles((double)par2 + 0.5D, (double)par3, (double)par4 + 0.5D, 0.0F, 0.0F);
            par1World.spawnEntityInWorld(var6);
            var6.spawnExplosionParticle();
        }

        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }
}

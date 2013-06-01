package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MoltenDecay extends Block
{
    public MoltenDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        //setTickRandomly(true);
        setHardness(2.0F);
        setLightValue(1.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Molten Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:molten");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int wID = world.getBlockId(x, y + 1, z);
        int m = world.getBlockMetadata(x, y, z);

        //System.out.println(x + "," + y + "," + z);
        //System.out.println(" ");
        if (m == 0)
        {
            if (par5Random.nextDouble() < 0.75D)
            {
                world.setBlock(x + 1, y, z, 49);
            }

            if (par5Random.nextDouble() < 0.75D)
            {
                world.setBlock(x - 1, y, z, 49);
            }

            if (par5Random.nextDouble() < 0.75D)
            {
                world.setBlock(x, y, z + 1, 49);
            }

            if (par5Random.nextDouble() < 0.75D)
            {
                world.setBlock(x, y, z - 1, 49);
            }
        }
        else if (wID == 0 || wID == 8 || wID == 9)
        {
            world.setBlock(x + 1, y, z, DecayingWorld.volcanoBuilder.blockID, 1, 3);
            world.setBlock(x - 1, y, z, DecayingWorld.volcanoBuilder.blockID, 3, 3);
            world.setBlock(x, y, z + 1, DecayingWorld.volcanoBuilder.blockID, 2, 3);
            world.setBlock(x, y, z - 1, DecayingWorld.volcanoBuilder.blockID, 4, 3);
            world.setBlock(x, y + 1, z, this.blockID, m - 1, 3);
        }
        else
        {
            world.setBlock(x + 1, y, z, DecayingWorld.volcanoBuilder.blockID, 1, 3);
            world.setBlock(x - 1, y, z, DecayingWorld.volcanoBuilder.blockID, 3, 3);
            world.setBlock(x, y, z + 1, DecayingWorld.volcanoBuilder.blockID, 2, 3);
            world.setBlock(x, y, z - 1, DecayingWorld.volcanoBuilder.blockID, 4, 3);

            if (m < 6)
            {
                if (y < 60)
                {
                    world.setBlock(x, y + 1, z, this.blockID, 10 + par5Random.nextInt(4), 3);
                }
                else
                {
                    world.setBlock(x, y + 1, z, this.blockID, 6 + par5Random.nextInt(4), 3);
                }
            }
        }

        world.scheduleBlockUpdate(x, y + 1, z, blockID, 100 + par5Random.nextInt(100));
        world.setBlock(x, y, z, 11);
        world.setBlock(x, y - 1, z, 11);
    }

    @Override
    public int onBlockPlaced(World par1World, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        par1World.scheduleBlockUpdate(x, y, z, blockID, 100);
        return 8 + new Random().nextInt(4);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

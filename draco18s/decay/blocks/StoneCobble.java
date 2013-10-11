package draco18s.decay.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class StoneCobble extends BlockSand
{
    public StoneCobble(int par1)
    {
        super(par1);
        setTickRandomly(true);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(soundStoneFootstep);
        fallInstantly = false;
        setUnlocalizedName("Stone (Cobble)");
    }
    
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = Block.cobblestone.getIcon(0, 0);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	int c = canFall(world, x, y - 1, z);

        if (c > 0)
        {
            if (c % 2 == 1)
            {
            	super.updateTick(world, x, y, z, random);
            	return;
            }
            else
            {
                world.setBlockToAir(x, y, z);
                c >>= 1;
                List<Long> a = new ArrayList<Long>();

                if (c % 2 == 1)
                {
                    a.add((long) 1);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 2);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 3);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 4);
                }

                int r = random.nextInt(a.size());
                r = (int)(a.get(r) & 15);

                switch (r)
                {
                    case 1:
                        world.setBlock(x + 1, y - 1, z, blockID, 0, 2);
                        break;

                    case 2:
                        world.setBlock(x - 1, y - 1, z, blockID, 0, 2);
                        break;

                    case 3:
                        world.setBlock(x, y - 1, z + 1, blockID, 0, 2);
                        break;

                    case 4:
                        world.setBlock(x, y - 1, z - 1, blockID, 0, 2);
                        break;
                }
            }
        }
        else if (world.getBlockMetadata(x, y, z) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 0);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
    	return Block.cobblestone.blockID;
    }
    
    public static int canFall(World par0World, int par1, int par2, int par3)
    {
        int[] l = {par0World.getBlockId(par1, par2, par3), par0World.getBlockId(par1 + 1, par2, par3), par0World.getBlockId(par1 - 1, par2, par3), par0World.getBlockId(par1, par2, par3 + 1), par0World.getBlockId(par1, par2, par3 - 1)};
        int[] l2 = {0, par0World.isBlockNormalCubeDefault(par1 + 1, par2 + 1, par3, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1 - 1, par2 + 1, par3, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1, par2 + 1, par3 + 1, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1, par2 + 1, par3 - 1, false) ? 1 : 0};
        int r = 0;

        for (int i = 0; i < 5; i++)
        {
            if (l[i] == 0 || l[i] == Block.fire.blockID)
            {
                if (l2[i] == 0)
                {
                    r += (1 << i);
                }
            }
        }

        return r;
    }
}

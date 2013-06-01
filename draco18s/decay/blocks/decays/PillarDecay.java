package draco18s.decay.blocks.decays;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PillarDecay extends Block
{
    public PillarDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Bedrock Pillars");
        // TODO Auto-generated constructor stub
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:pillar");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int r = (int)(par5Random.nextDouble() * 15);
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

            default:
                break;
        }

        y++;
        //set new block to decay
        world.setBlock(x, y, z, this.blockID);
        //set old block to bedrock
        world.setBlock(i, j, k, 7);
        //set blocks adjacent to new block to bedrock
        world.setBlock(x + 1, y, z, 7);
        world.setBlock(x - 1, y, z, 7);
        world.setBlock(x, y, z + 1, 7);
        world.setBlock(x, y, z - 1, 7);
        world.setBlock(x, y + 1, z, 7);
        world.setBlock(x, y - 1, z, 7);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

package draco18s.decay.blocks.decays;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GlowDecay extends Block
{
    Icon deadTexture;

    public GlowDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setHardness(2.0F);
        setTickRandomly(true);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Glowing Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        deadTexture = iconRegister.registerIcon("DecayingWorld:glowdead");
        blockIcon = iconRegister.registerIcon("DecayingWorld:glowalive");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        if (par2 >= 5)
        {
            return blockIcon;
        }
        else
        {
            return deadTexture;
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) >= 5)
        {
            return 15;
        }
        else
        {
            return 0;
        }
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};
        int meta = world.getBlockMetadata(x, y, z);

        if (wID[0] != blockID && wID[1] != blockID && wID[2] != blockID && wID[3] != blockID && wID[4] != blockID && wID[5] != blockID)
        {
            //world.setBlockMetadataWithNotify(x, y, z, 5);
            //world.markBlockForUpdate(x, y, z);
            world.setBlockMetadataWithNotify(x, y, z, 5, 3);
            meta = 5;
        }

        if (meta >= 5)
        {
            if (wID[0] == Block.grass.blockID || wID[0] == Block.dirt.blockID)
            {
                world.setBlock(x + 1, y, z, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);
            }

            if (wID[1] == Block.grass.blockID || wID[1] == Block.dirt.blockID)
            {
                world.setBlock(x - 1, y, z, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);
            }

            if (wID[2] == Block.grass.blockID || wID[2] == Block.dirt.blockID)
            {
                world.setBlock(x, y + 1, z, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);
            }

            if (wID[3] == Block.grass.blockID || wID[3] == Block.dirt.blockID)
            {
                world.setBlock(x, y - 1, z, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);
            }

            if (wID[4] == Block.grass.blockID || wID[4] == Block.dirt.blockID)
            {
                world.setBlock(x, y, z + 1, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);
            }

            if (wID[5] == Block.grass.blockID || wID[5] == Block.dirt.blockID)
            {
                world.setBlock(x, y, z - 1, blockID, par5Random.nextDouble() > 0.3 ? 5 : 0, 3);    /**/
            }
        }

        int[] wMD = {world.getBlockMetadata(x + 1, y, z), world.getBlockMetadata(x - 1, y, z), world.getBlockMetadata(x, y + 1, z), world.getBlockMetadata(x, y - 1, z), world.getBlockMetadata(x, y, z + 1), world.getBlockMetadata(x, y, z - 1)};
        int a;

        switch (meta)
        {
            case 0:
                a = (wID[0] == blockID ? wMD[0] == 5 ? 1 : 0 : 0) + (wID[1] == blockID ? wMD[1] == 5 ? 1 : 0 : 0) + (wID[2] == blockID ? wMD[2] == 5 ? 1 : 0 : 0) + (wID[3] == blockID ? wMD[3] == 5 ? 1 : 0 : 0) + (wID[4] == blockID ? wMD[4] == 5 ? 1 : 0 : 0) + (wID[5] == blockID ? wMD[5] == 5 ? 1 : 0 : 0);

                if (a == 3 || a == 2)
                {
                    world.setBlockMetadataWithNotify(x, y, z, 5, 3);
                    world.markBlockForUpdate(x, y, z);
                }

                //if(a == 0 && par5Random.nextDouble()>0.5) {
                //world.setBlockMetadataWithNotify(x, y, z, 1);
                //world.markBlockForUpdate(x, y, z);
                //}
                //else {
                //world.setBlockMetadataWithNotify(x, y, z, meta%5);
                //}
                //world.markBlockForUpdate(x, y, z);
                break;

            case 5:
                a = (wID[0] == blockID ? wMD[0] == 5 ? 1 : 0 : 0) + (wID[1] == blockID ? wMD[1] == 5 ? 1 : 0 : 0) + (wID[2] == blockID ? wMD[2] == 5 ? 1 : 0 : 0) + (wID[3] == blockID ? wMD[3] == 5 ? 1 : 0 : 0) + (wID[4] == blockID ? wMD[4] == 5 ? 1 : 0 : 0) + (wID[5] == blockID ? wMD[5] == 5 ? 1 : 0 : 0);

                //System.out.println(a);
                if (a != 3 && a != 4)
                {
                    world.setBlockMetadataWithNotify(x, y, z, 0, 3);
                    world.markBlockForUpdate(x, y, z);
                }

                //else {
                //world.setBlockMetadataWithNotify(x, y, z, meta%5 + 5);
                //}
                //world.markBlockForUpdate(x, y, z);
                break;
                /*default:
                	world.setBlockWithNotify(x, y, z, Block.grass.blockID);
                	world.markBlockForUpdate(x, y, z);
                	break;*/
        }

        getLightValue(world, x, y, z);
        //world.scheduleBlockUpdate(x, y, z, blockID, 300);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 300);
        return 0;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        //world.scheduleBlockUpdate(x, y, z, blockID, 300);
        //world.setBlockMetadata(x, y, z, new Random().nextInt(2));
    }
}

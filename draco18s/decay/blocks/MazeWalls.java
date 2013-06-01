package draco18s.decay.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import draco18s.decay.CommonProxy;
import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class MazeWalls extends Block
{
    public MazeWalls(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Maze Walls");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:mazegrow");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        MaterialEntity mme = (MaterialEntity)world.getBlockTileEntity(world.getSpawnPoint().posX, 0, world.getSpawnPoint().posZ);
        int bID = 7;
        int bMD = 0;
        int mmeCount = 0;

        if (mme != null)
        {
            bID = mme.materialBlockID;
            bMD = mme.materialBlockMeta;
            mmeCount = mme.extraCount;

            if (bID == 0)
            {
                bID = 7;
            }
        }

        if (par5Random.nextDouble() < 0.25)
        {
            //world.setBlock(x, y, z, bID, bMD, 3);
            if (mmeCount > 0)
            {
                int t = 8;

                switch (mmeCount)
                {
                    case 1:
                        t = 12;
                        break;

                    case 2:
                        t = 14;
                        break;

                    case 3:
                        t = 15;
                        break;
                }

                //System.out.println("Count: " + mme.extraCount + "," + t);
                t = par5Random.nextInt(t);

                //System.out.println("Rand: " + t);
                switch (t)
                {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        break;

                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        bID = mme.matID[0];
                        bMD = mme.matMD[0];
                        break;

                    case 12:
                    case 13:
                        bID = mme.matID[1];
                        bMD = mme.matMD[1];
                        break;

                    case 14:
                        bID = mme.matID[2];
                        bMD = mme.matMD[2];
                        break;
                }
            }

            world.setBlock(x, y, z, bID, bMD, 3);
        }
        else
        {
            world.setBlock(x, y, z, 7);
        }

        int w;

        if (meta == 0)
        {
            int n = 1;
            w = world.getBlockId(x, y - n, z);

            if (w != 0 || par5Random.nextDouble() > 0.15D)
            {
                while (n > 0 && n <= y && (w == Block.gravel.blockID || w == Block.lavaStill.blockID || w == 0))
                {
                    n++;
                    w = world.getBlockId(x, y - n, z);
                }
            }

            if (w == DecayingWorld.mazeWallsMat.blockID || y - n == 0)
            {
                return;
            }

            world.setBlock(x, y - n, z, blockID, 0, 3);
            world.scheduleBlockUpdate(x, y - n, z, blockID, 10);
        }
        else if (meta > 1)
        {
            w = world.getBlockId(x, y + 1, z);

            if (meta <= 3 && (w == Block.dirt.blockID || w == Block.grass.blockID))
            {
                meta++;
            }

            if (w != blockID)
            {
                world.setBlock(x, y + 1, z, blockID, meta - 1, 3);
                world.scheduleBlockUpdate(x, y + 1, z, blockID, 10);
            }
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 100 + new Random().nextInt(100));
    }
}

package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WormDecay extends BlockContainer
{
    public static int tubeBlock;

    public WormDecay(int par1, Material par3Material, int tubeBlockID)
    {
        super(par1, par3Material);
        //setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Wormy Decay");
        tubeBlock = tubeBlockID;
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:wormflesh");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        if (par5Random.nextDouble() > 1.0D) //odds of actually updating
        {
            return;
        }

        int i = x;
        int j = y;
        int k = z;
        int meta = world.getBlockMetadata(x, y, z);
        MaterialEntity ent = (MaterialEntity)world.getBlockTileEntity(x, y, z);
        int meID = ent.materialBlockID;
        int meMD = ent.materialBlockMeta;
        //System.out.println("Entity info - " + meID + ":" + meMD);
        world.setBlock(x + 1, y,   z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x + 1, y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y,   z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x - 1, y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        world.setBlock(x,   y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        //System.out.println("Direction: " + meta);
        //System.out.println(x + "," + y + "," + z);
        int tempmeta = meta;

        if (par5Random.nextDouble() < 0.3D)  //odds of changing direction for one update
        {
            int a = 0;

            if (meta < 8)
            {
                a = ((par5Random.nextDouble() > 0.5) ? 0 : 1);

                if (meta % 2 == 0)
                {
                    a += ((par5Random.nextDouble() > 0.333) ? 0 : 8);
                }

                if (a == 0)
                {
                    a = -1;
                }
            }
            else
            {
                if (meta % 2 == 0)
                {
                    a = -8;
                }
                else
                {
                    a = -9;
                }
            }

            if (meta + a == -1)
            {
                tempmeta = 7;
            }
            else if (meta + a == 16)
            {
                tempmeta = 8;
            }
            else if (meta == 7 && a == 1)
            {
                tempmeta = 0;
            }
            else if (meta == 8 && a == -1)
            {
                tempmeta = 15;
            }
            else
            {
                tempmeta = (meta + a) % 16;
            }

            //world.setBlockMetadata(x, y, z, meta);
        }

        switch (tempmeta)
        {
            case 0:
                x++;
                break;

            case 1:
                x++;
                z++;
                break;

            case 2:
                z++;
                break;

            case 3:
                x--;
                z++;
                break;

            case 4:
                x--;
                break;

            case 5:
                x--;
                z--;
                break;

            case 6:
                z--;
                break;

            case 7:
                x++;
                z--;
                break;

            case 8:
                x++;
                y++;
                break;

            case 9:
                x++;
                y--;
                break;

            case 10:
                z++;
                y++;
                break;

            case 11:
                z++;
                y--;
                break;

            case 12:
                x--;
                y++;
                break;

            case 13:
                x--;
                y--;
                break;

            case 14:
                z--;
                y++;
                break;

            case 15:
                z--;
                y--;
                break;
        }

        //System.out.println(x + "," + y + "," + z);
        world.setBlock(x,   y,   z,   this.blockID, meta, 3);
        ent = (MaterialEntity)world.getBlockTileEntity(x, y, z);

        if (ent != null)
        {
            ent.materialBlockID = meID;
            ent.materialBlockMeta = meMD;
        }

        //System.out.println(i + "," + j + "," + k);
        world.setBlock(i, j, k, DecayingWorld.wormInterior.blockID, 4, 3);

        //System.out.println(" ");
        if (par5Random.nextDouble() < 0.1D)  //odds of changing direction
        {
            int a = 0;

            if (meta < 8)
            {
                a = ((par5Random.nextDouble() > 0.5) ? 0 : 1);

                if (meta % 2 == 0)
                {
                    a += ((par5Random.nextDouble() > 0.333) ? 0 : 8);
                }

                if (a == 0)
                {
                    a = -1;
                }
            }
            else
            {
                if (meta % 2 == 0)
                {
                    a = -8;
                }
                else
                {
                    a = -9;
                }
            }

            if (meta + a == -1)
            {
                meta = 7;
            }
            else if (meta + a == 16)
            {
                meta = 8;
            }
            else if (meta == 7 && a == 1)
            {
                meta = 0;
            }
            else if (meta == 8 && a == -1)
            {
                meta = 15;
            }
            else
            {
                meta = (meta + a) % 16;
            }

            if (y < 5)
            {
                if (meta > 8 && meta % 2 == 1)
                {
                    if (par5Random.nextDouble() < 0.0625)
                    {
                        meta -= 9;
                    }
                }
            }

            world.setBlockMetadataWithNotify(x, y, z, meta, 3);
        }

        world.scheduleBlockUpdate(x, y, z, blockID, 100);
    }

    @Override
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        par1World.scheduleBlockUpdate(x, y, z, blockID, 100);
    }

    @Override
    public int onBlockPlaced(World par1World, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        int r = new Random().nextInt(16);
        //System.out.println("Randomizing direction: " + r);
        par1World.setBlock(x + 1, y,   z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x + 1, y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y,   z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x - 1, y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y + 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y - 1, z,   DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y + 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y + 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y,   z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y - 1, z + 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y - 1, z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        par1World.setBlock(x,   y,   z - 1, DecayingWorld.wormInterior.blockID, 8, 3);
        //par1World.setBlock(x, y, z, this.blockID, r);
        par1World.scheduleBlockUpdate(x, y, z, blockID, 100);
        /*MaterialEntity ent = (MaterialEntity)par1World.getBlockTileEntity(x, y, z);
        ent.materialBlockID = Block.cobblestone.blockID;
        ent.materialBlockMeta = 0;*/
        return r;
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new MaterialEntity();
    }
}

package draco18s.decay.blocks;

import java.util.Random;

import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;

public class HealthFence extends BlockFence {

	public HealthFence(int par1, String par2Str, Material par3Material) {
		super(par1, par2Str, par3Material);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
		setHardness(2.5F);
		setResistance(5.5F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("Growth Fence");
	}
	
	public static boolean isIdAFence(int par0)
    {
        return true;
    }
	
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = DecayingWorld.healCrystal.getBlockTextureFromSide(0);
    }
	
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}
	
	public void updateTick(World world, int x, int y, int z, Random par5Random) {
		int w;
		for(int _x=-1; _x <= 1; _x++) {
			for(int _z=-1; _z <= 1; _z++) {
				w = world.getBlockId(x+_x, y, z+_z);
				if(w == Block.crops.blockID) {
					//System.out.println("Updating crop");
					//world.scheduleBlockUpdate(x+_x, y, z+_z, blockID, 1);
					if (world.getBlockLightValue(x+_x, y + 1, z+_z) >= 9)
			        {
			            int l = world.getBlockMetadata(x+_x, y, z+_z);

			            if (l < 7)
			            {
			                float f = this.getGrowthRate(world, x+_x, y, z+_z);
			                int nn = par5Random.nextInt((int)(25.0F / f) + 1);
			                //System.out.println("Rand: " + nn);
			                if (nn == 0)
			                {
			                    ++l;
			                    world.setBlockMetadataWithNotify(x+_x, y, z+_z, l, 2);
			                }
			            }
			        }
				}
				else if(w == Block.sapling.blockID) {
					if (world.getBlockLightValue(x+_x, y+1, z+_z) >= 9 && par5Random.nextInt(7) == 0)
		            {
						int l = world.getBlockMetadata(x+_x, y, z+_z);

				        if ((l & 8) == 0)
				        {
				        	world.setBlockMetadataWithNotify(x+_x, y, z+_z, l | 8, 4);
				        }
				        else
				        {
				            growTree(world, x+_x, y, z+_z, par5Random);
				        }
		            }
				}
			}
		}
	}
	
	private float getGrowthRate(World par1World, int x, int y, int z)
    {
        float f = 1.0F;
        int l = par1World.getBlockId(x, y, z - 1);
        int i1 = par1World.getBlockId(x, y, z + 1);
        int j1 = par1World.getBlockId(x - 1, y, z);
        int k1 = par1World.getBlockId(x + 1, y, z);
        int l1 = par1World.getBlockId(x - 1, y, z - 1);
        int i2 = par1World.getBlockId(x + 1, y, z - 1);
        int j2 = par1World.getBlockId(x + 1, y, z + 1);
        int k2 = par1World.getBlockId(x - 1, y, z + 1);
        boolean flag = j1 == Block.crops.blockID || k1 == Block.crops.blockID;
        boolean flag1 = l == Block.crops.blockID || i1 == Block.crops.blockID;
        boolean flag2 = l1 == Block.crops.blockID || i2 == Block.crops.blockID || j2 == Block.crops.blockID || k2 == Block.crops.blockID;

        for (int l2 = x - 1; l2 <= x + 1; ++l2)
        {
            for (int i3 = z - 1; i3 <= z + 1; ++i3)
            {
                int j3 = par1World.getBlockId(l2, y - 1, i3);
                float f1 = 0.0F;

                if (blocksList[j3] != null && blocksList[j3].canSustainPlant(par1World, l2, y - 1, i3, ForgeDirection.UP, (BlockCrops)Block.crops))
                {
                    f1 = 1.0F;

                    if (blocksList[j3].isFertile(par1World, l2, y - 1, i3))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l2 != x || i3 != z)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }
	
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4)) return;

        int l = par1World.getBlockMetadata(par2, par3, par4) & 3;
        Object object = null;
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;

        if (l == 1)
        {
            object = new WorldGenTaiga2(true);
        }
        else if (l == 2)
        {
            object = new WorldGenForest(true);
        }
        else if (l == 3)
        {
            for (i1 = 0; i1 >= -1; --i1)
            {
                for (j1 = 0; j1 >= -1; --j1)
                {
                    if (this.isSameSapling(par1World, par2 + i1, par3, par4 + j1, 3) && this.isSameSapling(par1World, par2 + i1 + 1, par3, par4 + j1, 3) && this.isSameSapling(par1World, par2 + i1, par3, par4 + j1 + 1, 3) && this.isSameSapling(par1World, par2 + i1 + 1, par3, par4 + j1 + 1, 3))
                    {
                        object = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
                        flag = true;
                        break;
                    }
                }

                if (object != null)
                {
                    break;
                }
            }

            if (object == null)
            {
                j1 = 0;
                i1 = 0;
                object = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
            }
        }
        else
        {
            object = new WorldGenTrees(true);

            if (par5Random.nextInt(10) == 0)
            {
                object = new WorldGenBigTree(true);
            }
        }

        if (flag)
        {
            par1World.setBlock(par2 + i1, par3, par4 + j1, 0, 0, 4);
            par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, 0, 0, 4);
            par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, 0, 0, 4);
            par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, 0, 0, 4);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, 0, 0, 4);
        }

        if (!((WorldGenerator)object).generate(par1World, par5Random, par2 + i1, par3, par4 + j1))
        {
            if (flag)
            {
                par1World.setBlock(par2 + i1, par3, par4 + j1, Block.sapling.blockID, l, 4);
                par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, Block.sapling.blockID, l, 4);
                par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, Block.sapling.blockID, l, 4);
                par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, Block.sapling.blockID, l, 4);
            }
            else
            {
                par1World.setBlock(par2, par3, par4, Block.sapling.blockID, l, 4);
            }
        }
    }

    public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
    {
        return par1World.getBlockId(par2, par3, par4) == Block.sapling.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }
}

package draco18s.decay.blocks.decays;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class DecayAir extends Block
{
    public DecayAir(int par1, Material par3Material)
    {
        super(par1, par3Material);
        //setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Worm Flesh");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:wormflesh");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        if (!world.isRemote)
        {
            int mymeta = world.getBlockMetadata(x, y, z);
            int[] a = new int[6];
            MaterialEntity mme = findMatEnt(world, x, y, z);
            int mblockID = 4;
            int mblockMD = 0;

            if (mme != null)
            {
                mblockID = mme.materialBlockID;
                mblockMD = mme.materialBlockMeta;
            }

            if (world.getClosestPlayer(x, y, z, 5) != null)
            {
                mblockID = 4;
                mblockMD = 0;
            }

            if (mymeta == 4 || mymeta == 5)
            {
                a[0] = world.getBlockId(x + 1, y, z);
                a[1] = world.getBlockId(x, y + 1, z);
                a[2] = world.getBlockId(x, y, z + 1);
                a[3] = world.getBlockId(x - 1, y, z);
                a[4] = world.getBlockId(x, y - 1, z);
                a[5] = world.getBlockId(x, y, z - 1);

                if (a[0] != mblockID && a[0] != this.blockID && a[0] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x + 1, y, z, mblockID, mblockMD, 3);
                }

                if (a[1] != mblockID && a[1] != this.blockID && a[1] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x, y + 1, z, mblockID, mblockMD, 3);
                }

                if (a[2] != mblockID && a[2] != this.blockID && a[2] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x, y, z + 1, mblockID, mblockMD, 3);
                }

                if (a[3] != mblockID && a[3] != this.blockID && a[3] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x - 1, y, z, mblockID, mblockMD, 3);
                }

                if (a[4] != mblockID && a[4] != this.blockID && a[4] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x, y - 1, z, mblockID, mblockMD, 3);
                }

                if (a[5] != mblockID && a[5] != this.blockID && a[5] != DecayingWorld.wormDecay.blockID)
                {
                    world.setBlock(x, y, z - 1, mblockID, mblockMD, 3);
                }
            }
            else if (mymeta == 0)
            {
                double n = par5Random.nextDouble();
                int tblockID = Block.cobblestone.blockID;
                int tblockMD = 0;

                if (n < 0.025)
                {
                    n *= 40;

                    if (n < 0.005)
                    {
                        tblockID = 56;
                    }
                    //world.setBlock(x, y, z, 56);
                    else if (n < 0.03)
                    {
                        tblockID = 89;
                    }
                    //world.setBlock(x, y, z, 89);
                    else if (n < 0.1)
                    {
                        tblockID = 15;
                    }
                    //world.setBlock(x, y, z, 15);
                    else if (n < 0.15)
                    {
                        tblockID = 73;
                    }
                    //world.setBlock(x, y, z, 73);
                    else if (n < 0.25)
                    {
                        tblockID = 15;
                    }
                    //world.setBlock(x, y, z, 15);
                    else if (n < 0.5)
                    {
                        tblockID = 14;
                    }
                    //world.setBlock(x, y, z, 14);
                    else
                    {
                        tblockID = 16;
                    }

                    //world.setBlock(x, y, z, 16);
                    n /= 40;
                }

                if (n < 0.05)
                {
                    if (canFallBelow(world, x, y - 1, z, this.blockID) && canFallBelow(world, x, y + 1, z, this.blockID) && y >= 0)
                    {
                        byte var8 = 32;
                        world.setBlock(x, y, z, 0);

                        while (canFallBelow(world, x, y - 1, z, this.blockID) && y > 0)
                        {
                            --y;
                        }
                    }

                    world.setBlock(x, y, z, tblockID, tblockMD, 3);
                }
                else
                {
                    world.setBlock(x, y, z, 0);
                }

                return;
            }

            int endMeta = 0;
            int meta;
            boolean var10 = this.isBlockGettingPowered(world, x, y, z);

            if (var10)
            {
                endMeta = 8;
            }
            else
            {
                int b = world.getBlockId(x - 1, y, z);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x - 1, y, z);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }

                b = world.getBlockId(x + 1, y, z);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x + 1, y, z);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }

                b = world.getBlockId(x, y, z - 1);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x, y, z - 1);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }

                b = world.getBlockId(x, y, z + 1);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x, y, z + 1);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }

                b = world.getBlockId(x, y - 1, z);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x, y - 1, z);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }

                b = world.getBlockId(x, y + 1, z);

                if (b == this.blockID)
                {
                    meta = world.getBlockMetadata(x, y + 1, z);

                    if (endMeta < meta)
                    {
                        endMeta = meta;
                    }
                }
            }

            //System.out.println("End meta: " + endMeta);
            if (endMeta > 0)
            {
                endMeta--;
            }

            if (endMeta == 0)
            {
                double n = par5Random.nextDouble();
                int tblockID = Block.cobblestone.blockID;
                int tblockMD = 0;

                if (n < 0.025)
                {
                    n *= 40;

                    if (n < 0.005)
                    {
                        tblockID = 56;
                    }
                    //world.setBlock(x, y, z, 56);
                    else if (n < 0.03)
                    {
                        tblockID = 89;
                    }
                    //world.setBlock(x, y, z, 89);
                    else if (n < 0.1)
                    {
                        tblockID = 15;
                    }
                    //world.setBlock(x, y, z, 15);
                    else if (n < 0.15)
                    {
                        tblockID = 73;
                    }
                    //world.setBlock(x, y, z, 73);
                    else if (n < 0.25)
                    {
                        tblockID = 15;
                    }
                    //world.setBlock(x, y, z, 15);
                    else if (n < 0.5)
                    {
                        tblockID = 14;
                    }
                    //world.setBlock(x, y, z, 14);
                    else
                    {
                        tblockID = 16;
                    }

                    //world.setBlock(x, y, z, 16);
                    n /= 40;
                }

                if (n < 0.05)
                {
                    if (canFallBelow(world, x, y - 1, z, this.blockID) && canFallBelow(world, x, y + 1, z, this.blockID) && y >= 0)
                    {
                        byte var8 = 32;
                        world.setBlock(x, y, z, 0);

                        while (canFallBelow(world, x, y - 1, z, this.blockID) && y > 0)
                        {
                            --y;
                        }
                    }

                    world.setBlock(x, y, z, tblockID, tblockMD, 3);
                }
                else
                {
                    world.setBlock(x, y, z, 0);
                }

                return;
            }
            else
            {
                world.markBlockForUpdate(x, y, z);
                world.setBlock(x, y, z, this.blockID, endMeta, 3);
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int ID)
    {
        if (ID == blockID || ID == DecayingWorld.wormDecay.blockID)
        {
            //if(world.getClosestPlayer(x, y, z, 5) == null)
            world.scheduleBlockUpdate(x, y, z, this.blockID, 0);
        }
    }

    public boolean isBlockIndirectlyGettingPowered(World world, int par1, int par2, int par3)
    {
        return this.isBlockIndirectlyProvidingPowerTo(world, par1, par2 - 1, par3, 0) ? true : (this.isBlockIndirectlyProvidingPowerTo(world, par1, par2 + 1, par3, 1) ? true : (this.isBlockIndirectlyProvidingPowerTo(world, par1, par2, par3 - 1, 2) ? true : (this.isBlockIndirectlyProvidingPowerTo(world, par1, par2, par3 + 1, 3) ? true : (this.isBlockIndirectlyProvidingPowerTo(world, par1 - 1, par2, par3, 4) ? true : this.isBlockIndirectlyProvidingPowerTo(world, par1 + 1, par2, par3, 5)))));
    }

    public boolean isBlockIndirectlyProvidingPowerTo(World world, int par1, int par2, int par3, int par4)
    {
        if (world.isBlockNormalCube(par1, par2, par3))
        {
            return this.isBlockGettingPowered(world, par1, par2, par3);
        }
        else
        {
            int var5 = world.getBlockId(par1, par2, par3);
            return var5 == DecayingWorld.wormDecay.blockID ? false : true;
        }
    }

    public boolean isBlockGettingPowered(World world, int par1, int par2, int par3)
    {
        return this.isBlockProvidingPowerTo(world, par1, par2 - 1, par3, 0) ? true : (this.isBlockProvidingPowerTo(world, par1, par2 + 1, par3, 1) ? true : (this.isBlockProvidingPowerTo(world, par1, par2, par3 - 1, 2) ? true : (this.isBlockProvidingPowerTo(world, par1, par2, par3 + 1, 3) ? true : (this.isBlockProvidingPowerTo(world, par1 - 1, par2, par3, 4) ? true : this.isBlockProvidingPowerTo(world, par1 + 1, par2, par3, 5)))));
    }

    public boolean isBlockProvidingPowerTo(World world, int par1, int par2, int par3, int par4)
    {
        int var5 = world.getBlockId(par1, par2, par3);
        return var5 == DecayingWorld.wormDecay.blockID ? true : false;
    }

    public static boolean canFallBelow(World par0World, int par1, int par2, int par3, int par5)
    {
        int var4 = par0World.getBlockId(par1, par2, par3);

        if (var4 == 0)
        {
            return true;
        }
        else if (var4 == Block.fire.blockID || var4 == par5)
        {
            return true;
        }
        else
        {
            Material var5 = Block.blocksList[var4].blockMaterial;
            return var5 == Material.water ? true : var5 == Material.lava;
        }
    }

    private MaterialEntity findMatEnt(World world, int x, int y, int z)
    {
        int dist = 999;
        MaterialEntity m = null;
        MaterialEntity n = null;
        TileEntity te;

        for (int i = x - 5; i <= x + 5; i++)
        {
            for (int j = y - 5; j <= y + 5; j++)
            {
                for (int k = z - 5; k <= z + 5; k++)
                {
                    int s = (i - x) * (i - x) + (j - y) * (j - y) + (k - z) * (k - z);

                    if (dist > s)
                    {
                        te = world.getBlockTileEntity(i, j, k);

                        if (te != null && te instanceof MaterialEntity)
                        {
                            m = (MaterialEntity)te;
                            //if(m != null) {
                            dist = s;
                            n = m;
                            //}
                        }
                    }
                }
            }
        }

        return n;
    }
}

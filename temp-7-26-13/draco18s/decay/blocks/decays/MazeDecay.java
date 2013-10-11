package draco18s.decay.blocks.decays;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.DungeonHooks.DungeonMob;

public class MazeDecay extends Block
{
    static Icon hiddenIcon;
    static Icon sideIcon;

    public MazeDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Conway Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        hiddenIcon = iconRegister.registerIcon("grass_top");
        blockIcon = iconRegister.registerIcon("DecayingWorld:mazegrow");
        sideIcon = Block.grass.getBlockTextureFromSide(2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        /*if(par2 > 9)
        	return this.blockIndexInTexture+1;
        else
        	return this.blockIndexInTexture;
        return 16+par2;
        return this.blockIcon;*/
        if (par2 > 9)
        {
            return blockIcon;
        }
        else
        {
            if (par1 == 1)
            {
                return hiddenIcon;
            }
            else if (par1 == 0)
            {
                return Block.dirt.getBlockTextureFromSide(0);
            }
            else
            {
                return sideIcon;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1)
    {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                int i2 = par1IBlockAccess.getBiomeGenForCoords(par2 + l1, par4 + k1).getBiomeGrassColor();
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }

    @SideOnly(Side.CLIENT)
    public static Icon getIconSideOverlay()
    {
        return hiddenIcon;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        /*if(world.getBlockMetadata(x, y, z) >= 5)
        	return 15;
        else*/
        return 0;
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        int[] wID = {world.getBlockId(x + 3, y, z + 3), world.getBlockId(x + 3, y, z), world.getBlockId(x + 3, y, z - 3), world.getBlockId(x, y, z + 3), world.getBlockId(x, y, z - 3), world.getBlockId(x - 3, y, z + 3), world.getBlockId(x - 3, y, z), world.getBlockId(x - 3, y, z - 3)};
        int meta = world.getBlockMetadata(x, y, z);
        int uID = world.getBlockId(x, y + 1, z);

        if (uID == blockID || uID == Block.grass.blockID || uID == Block.dirt.blockID || uID == Block.gravel.blockID)
        {
            world.setBlock(x, y + 1, z, blockID, meta, 3);
        }
        else
        {
            int n = 2;

            do
            {
                uID = world.getBlockId(x, y + n, z);
                n++;
            }
            while (n < 21 && uID != Block.grass.blockID);

            if (n != 21)
            {
                //n--;
                do
                {
                    n--;
                    world.setBlock(x, y + n, z, blockID, meta, 3);
                }
                while (n >= 1);
            }
        }

        if (meta == 4 || meta == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            world.markBlockForUpdate(x, y, z);
            world.scheduleBlockUpdate(x, y, z, blockID, 250);
            return;
        }
        else if (meta == 2 || meta == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 3);
            world.markBlockForUpdate(x, y, z);
            world.scheduleBlockUpdate(x, y, z, blockID, 250);
            return;
        }

        uID = world.getBlockId(x, y - 1, z);

        if (uID != blockID)
        {
            //if block below is not this

            //if(meta >= 5) {
            if (wID[1] == Block.grass.blockID || wID[1] == Block.dirt.blockID || wID[1] == Block.stone.blockID || wID[1] == Block.gravel.blockID)
            {
                world.setBlock(x + 3, y, z, blockID, /*par5Random.nextDouble()>0.05?1:*/1, 3); //1 or 3
                //world.scheduleBlockUpdate(x+3, y, z, blockID, 10);
            }

            if (wID[3] == Block.grass.blockID || wID[3] == Block.dirt.blockID || wID[3] == Block.stone.blockID || wID[1] == Block.gravel.blockID)
            {
                world.setBlock(x, y, z + 3, blockID, /*par5Random.nextDouble()>0.05?1:*/1, 3);
                //world.scheduleBlockUpdate(x, y, z+3, blockID, 10);
            }

            if (wID[4] == Block.grass.blockID || wID[4] == Block.dirt.blockID || wID[4] == Block.stone.blockID || wID[1] == Block.gravel.blockID)
            {
                world.setBlock(x, y, z - 3, blockID, /*par5Random.nextDouble()>0.05?1:*/1, 3);
                //world.scheduleBlockUpdate(x, y, z-3, blockID, 10);
            }

            if (wID[6] == Block.grass.blockID || wID[6] == Block.dirt.blockID || wID[6] == Block.stone.blockID || wID[1] == Block.gravel.blockID)
            {
                world.setBlock(x - 3, y, z, blockID, /*par5Random.nextDouble()>0.05?1:*/1, 3);
                //world.scheduleBlockUpdate(x-3, y, z, blockID, 10);
            }/**/

            //}
        }

        int[] wMD = {world.getBlockMetadata(x + 3, y, z + 3), world.getBlockMetadata(x + 3, y, z), world.getBlockMetadata(x + 3, y, z - 3), world.getBlockMetadata(x, y, z + 3), world.getBlockMetadata(x, y, z - 3), world.getBlockMetadata(x - 3, y, z + 3), world.getBlockMetadata(x - 3, y, z), world.getBlockMetadata(x - 3, y, z - 3)};
        int a, b;

        switch (meta)
        {
            case 0:
                a = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                //int b = (wID[0]==blockID?(wMD[0]>=4?1:0):0) + (wID[2]==blockID?(wMD[2]>=4?1:0):0) + (wID[5]==blockID?(wMD[5]>=4?1:0):0) + (wID[7]==blockID?(wMD[7]>=4?1:0):0);
                if (a == 3)
                {
                    world.setBlockMetadataWithNotify(x, y, z, 2, 3);
                    world.markBlockForUpdate(x, y, z);
                    world.scheduleBlockUpdate(x, y, z, blockID, 50);
                }
                else
                {
                    world.scheduleBlockUpdate(x, y, z, blockID, 300);
                }

                break;

            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                a = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);
                b = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                if (a == 0 || a >= 5)
                {
                    world.setBlockMetadataWithNotify(x, y, z, 4, 3);
                    world.markBlockForUpdate(x, y, z);
                    world.scheduleBlockUpdate(x, y, z, blockID, 50);
                }
                else if (b == 2 && (a - b) == 1)
                {
                    boolean kill = false;
                    b = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0);

                    if (b == 3)
                    {
                        kill = true;
                    }

                    b = (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                    if (b == 3)
                    {
                        kill = true;
                    }

                    b = (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                    if (b == 3)
                    {
                        kill = true;
                    }

                    b = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0);

                    if (b == 3)
                    {
                        kill = true;
                    }

                    if (kill && par5Random.nextDouble() < 0.25)
                    {
                        world.setBlockMetadataWithNotify(x, y, z, 4, 3);
                        world.markBlockForUpdate(x, y, z);
                        world.scheduleBlockUpdate(x, y, z, blockID, 50);
                    }
                    else
                    {
                        world.scheduleBlockUpdate(x, y, z, blockID, 300);
                    }
                }
                else
                {
                    world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
                    world.markBlockForUpdate(x, y, z);
                    world.scheduleBlockUpdate(x, y, z, blockID, 300);
                }

                break;

            case 15:
                int w = world.getBlockId(x, y + 1, z);

                if (w == 0 || w == 8 || w == 9 || w == 10 || w == 11)
                {
                    a = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);
                    b = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);
                    int r = par5Random.nextInt(20);

                    if (a == 1 && b == 0 && r < 5)
                    {
                        switch (r)
                        {
                            case 0:
                                int ind = (wID[1] == blockID ? (wMD[1] >= 4 ? 4 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 2 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 6 : 0) : 0);
                                world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3 + (2 + ind) % 8, 3);
                                world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3 + (3 + ind) % 8, 3);
                                world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3 + (4 + ind) % 8, 3);
                                world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3 + (1 + ind) % 8, 3);
                                world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 11, 3);
                                world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3 + (5 + ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3 + (ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3 + (7 + ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3 + (6 + ind) % 8, 3);
                                break;

                            case 1:
                                world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 6, 3);
                                world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 6, 3);
                                world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 5, 3);
                                world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 6, 3);
                                world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 6, 3);
                                world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                break;

                            case 2:
                                world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, (wID[1] == blockID ? (wMD[1] >= 4 ? 3 : 0) : 0), 3);
                                world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, (wID[4] == blockID ? (wMD[4] >= 4 ? 3 : 0) : 0), 3);
                                world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 2, 3);
                                world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, (wID[3] == blockID ? (wMD[3] >= 4 ? 3 : 0) : 0), 3);
                                world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, (wID[6] == blockID ? (wMD[6] >= 4 ? 3 : 0) : 0), 3);
                                world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                break;

                            case 3:
                                world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x + 2, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x + 2, y + 1, z, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x + 2, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x + 1, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x,   y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x - 2, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x - 2, y + 1, z, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 2, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x + 1, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                world.setBlock(x,   y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 7, 3);
                                world.setBlock(x - 1, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                break;

                            case 4:
                                ind = (wID[1] == blockID ? (wMD[1] >= 4 ? 4 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 2 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 6 : 0) : 0);
                                world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 4 + (2 + ind) % 8, 3);
                                world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 4 + (3 + ind) % 8, 3);
                                world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 4 + (4 + ind) % 8, 3);
                                world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 4 + (1 + ind) % 8, 3);
                                world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 4 + (5 + ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 4 + (ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 4 + (7 + ind) % 8, 3);
                                world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 4 + (6 + ind) % 8, 3);
                                world.setBlock(x + 2, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x + 2, y + 1, z, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x + 2, y + (wID[1] == blockID ? (wMD[1] >= 4 ? 7 : 1) : 1), z - 1, DecayingWorld.mazeWalls.blockID, (wID[1] == blockID ? (wMD[1] >= 4 ? 6 : 12) : 12), 3);
                                world.setBlock(x + 1, y + (wID[3] == blockID ? (wMD[3] >= 4 ? 7 : 1) : 1), z + 2, DecayingWorld.mazeWalls.blockID, (wID[3] == blockID ? (wMD[3] >= 4 ? 6 : 12) : 12), 3);
                                world.setBlock(x,   y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x - 1, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x - 2, y + (wID[6] == blockID ? (wMD[6] >= 4 ? 7 : 1) : 1), z + 1, DecayingWorld.mazeWalls.blockID, (wID[6] == blockID ? (wMD[6] >= 4 ? 6 : 12) : 12), 3);
                                world.setBlock(x - 2, y + 1, z, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x - 2, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x - 1, y + (wID[4] == blockID ? (wMD[4] >= 4 ? 7 : 1) : 1), z - 2, DecayingWorld.mazeWalls.blockID, (wID[4] == blockID ? (wMD[4] >= 4 ? 6 : 12) : 12), 3);
                                world.setBlock(x,   y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                world.setBlock(x + 1, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                break;
                        }
                    }
                    else if (a == 4 && b == 2)
                    {
                        int t1;
                        boolean doit = false;
                        int which = -1;
                        t1 = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0);

                        if (t1 == 4)
                        {
                            doit = true;
                            which = 1;

                            if (world.getBlockId(x + 6, y, z) != blockID || world.getBlockMetadata(x + 6, y, z) != 0 || world.getBlockId(x + 9, y, z) != blockID || world.getBlockMetadata(x + 9, y, z) != 0)
                            {
                                doit = false;
                                which = -1;
                            }

                            if ((world.getBlockId(x + 9, y, z + 3) != blockID || world.getBlockMetadata(x + 9, y, z + 3) != 0) && (world.getBlockId(x + 9, y, z - 3) != blockID || world.getBlockMetadata(x + 9, y, z - 3) != 0))
                            {
                                doit = false;
                                which = -1;
                            }
                        }

                        t1 = (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[2] == blockID ? (wMD[2] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                        if (t1 == 4)
                        {
                            doit = true;
                            which = 4;

                            if (world.getBlockId(x, y, z - 6) != blockID || world.getBlockMetadata(x, y, z - 6) != 0 || world.getBlockId(x, y, z - 9) != blockID || world.getBlockMetadata(x, y, z - 9) != 0)
                            {
                                doit = false;
                                which = -1;
                            }

                            if ((world.getBlockId(x + 3, y, z - 9) != blockID || world.getBlockMetadata(x + 3, y, z - 9) != 0) && (world.getBlockId(x - 3, y, z - 9) != blockID || world.getBlockMetadata(x - 3, y, z - 9) != 0))
                            {
                                doit = false;
                                which = -1;
                            }
                        }

                        t1 = (wID[3] == blockID ? (wMD[3] >= 4 ? 1 : 0) : 0) + (wID[4] == blockID ? (wMD[4] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[7] == blockID ? (wMD[7] >= 4 ? 1 : 0) : 0);

                        if (t1 == 4)
                        {
                            doit = true;
                            which = 6;

                            if (world.getBlockId(x - 6, y, z) != blockID || world.getBlockMetadata(x - 6, y, z) != 0 || world.getBlockId(x - 9, y, z) != blockID || world.getBlockMetadata(x - 9, y, z) != 0)
                            {
                                doit = false;
                                which = -1;
                            }

                            if ((world.getBlockId(x - 9, y, z + 3) != blockID || world.getBlockMetadata(x - 9, y, z + 3) != 0) && (world.getBlockId(x - 9, y, z - 3) != blockID || world.getBlockMetadata(x - 9, y, z - 3) != 0))
                            {
                                doit = false;
                                which = -1;
                            }
                        }

                        t1 = (wID[0] == blockID ? (wMD[0] >= 4 ? 1 : 0) : 0) + (wID[1] == blockID ? (wMD[1] >= 4 ? 1 : 0) : 0) + (wID[5] == blockID ? (wMD[5] >= 4 ? 1 : 0) : 0) + (wID[6] == blockID ? (wMD[6] >= 4 ? 1 : 0) : 0);

                        if (t1 == 4)
                        {
                            doit = true;
                            which = 3;

                            if (world.getBlockId(x, y, z + 6) != blockID || world.getBlockMetadata(x, y, z + 6) != 0 || world.getBlockId(x, y, z + 9) != blockID || world.getBlockMetadata(x, y, z + 9) != 0)
                            {
                                doit = false;
                                which = -1;
                            }

                            if ((world.getBlockId(x + 3, y, z + 9) != blockID || world.getBlockMetadata(x + 3, y, z + 9) != 0) && (world.getBlockId(x - 3, y, z + 9) != blockID || world.getBlockMetadata(x - 3, y, z + 9) != 0))
                            {
                                doit = false;
                                which = -1;
                            }
                        }

                        //doit = false;
                        if (doit && par5Random.nextDouble() < 0.1)
                        {
                            int up, dwn = 0, ff;
                            TileEntityChest var16;
                            ChestGenHooks info;
                            TileEntityMobSpawner var19;

                            switch (which)
                            {
                                case 1:
                                    for (up = 1; up < 10; up++)
                                    {
                                        world.setBlock(x + 2, y + up, z + 2, 0);
                                        world.setBlock(x + 2, y + up, z - 2, 0);
                                        world.setBlock(x + 3, y + up, z + 2, 0);
                                        world.setBlock(x + 3, y + up, z - 2, 0);
                                        world.setBlock(x + 4, y + up, z + 2, 0);
                                        world.setBlock(x + 4, y + up, z - 2, 0);
                                    }

                                    world.setBlock(x + 2, y,   z + 2, 7);
                                    world.setBlock(x + 2, y,   z - 2, 7);
                                    world.setBlock(x + 3, y,   z + 2, 7);
                                    world.setBlock(x + 3, y,   z - 2, 7);
                                    world.setBlock(x + 4, y,   z + 2, 7);
                                    world.setBlock(x + 4, y,   z - 2, 7);
                                    world.setBlock(x + 2, y + 6, z + 2, 7);
                                    world.setBlock(x + 2, y + 6, z - 2, 7);
                                    world.setBlock(x + 3, y + 6, z + 2, 7);
                                    world.setBlock(x + 3, y + 6, z - 2, 7);
                                    world.setBlock(x + 4, y + 6, z + 2, 7);
                                    world.setBlock(x + 4, y + 6, z - 2, 7);
                                    world.setBlock(x + 1, y + 6, z + 1, 7);
                                    world.setBlock(x + 1, y + 6, z,   7);
                                    world.setBlock(x + 1, y + 6, z - 1, 7);
                                    world.setBlock(x + 5, y + 6, z + 1, 7);
                                    world.setBlock(x + 5, y + 6, z,   7);
                                    world.setBlock(x + 5, y + 6, z - 1, 7);
                                    world.setBlock(x,   y + 4, z + 1, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x,   y + 4, z,   DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x,   y + 4, z - 1, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x + 6, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 6, y + 3, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x + 6, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 2, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 4, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 2, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 4, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 1, y + 4, z + 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x + 1, y + 4, z - 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x + 5, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 5, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 1, y + 10, z,   7);
                                    world.setBlock(x + 2, y + 10, z,   7);
                                    world.setBlock(x + 3, y + 10, z,   7);
                                    world.setBlock(x + 4, y + 10, z,   7);
                                    world.setBlock(x + 5, y + 10, z,   7);
                                    world.setBlock(x + 1, y + 10, z + 1, 7);
                                    world.setBlock(x + 2, y + 10, z + 1, 7);
                                    world.setBlock(x + 3, y + 10, z + 1, 7);
                                    world.setBlock(x + 4, y + 10, z + 1, 7);
                                    world.setBlock(x + 5, y + 10, z + 1, 7);
                                    world.setBlock(x + 1, y + 10, z - 1, 7);
                                    world.setBlock(x + 2, y + 10, z - 1, 7);
                                    world.setBlock(x + 3, y + 10, z - 1, 7);
                                    world.setBlock(x + 4, y + 10, z - 1, 7);
                                    world.setBlock(x + 5, y + 10, z - 1, 7);
                                    world.setBlock(x + 2, y + 10, z + 2, 7);
                                    world.setBlock(x + 3, y + 10, z + 2, 7);
                                    world.setBlock(x + 4, y + 10, z + 2, 7);
                                    world.setBlock(x + 2, y + 10, z - 2, 7);
                                    world.setBlock(x + 3, y + 10, z - 2, 7);
                                    world.setBlock(x + 4, y + 10, z - 2, 7);
                                    world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 4, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x + 4, y + 11, z,   DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 4, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x + 3, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 3, y + 11, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 3, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 2, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x + 2, y + 11, z,   DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 2, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 5, 3);

                                    do
                                    {
                                        ff = world.getBlockId(x + 6, y + dwn, z);
                                        dwn--;
                                    }
                                    while (ff == 0);

                                    world.setBlock(x + 6, y + dwn, z, Block.grass.blockID);
                                    dwn--;

                                    do
                                    {
                                        world.setBlock(x + 6, y + dwn, z, 7);
                                        dwn--;
                                        ff = world.getBlockId(x + 6, y + dwn, z);
                                    }
                                    while (ff == blockID);

                                    world.setBlock(x + 3, y + 14, z, Block.glowStone.blockID);
                                    //System.out.println("Dungeon created at (" + x + "," + y + "," + z +")");
                                    world.setBlock(x + 3, y + 6, z, Block.mobSpawner.blockID);
                                    var19 = (TileEntityMobSpawner)world.getBlockTileEntity(x + 3, y + 6, z);

                                    if (var19 != null)
                                    {
                                        var19.func_98049_a().setMobID("Skeleton");
                                    }

                                    world.setBlock(x + 1, y + 1, z, Block.chest.blockID);
                                    var16 = (TileEntityChest)world.getBlockTileEntity(x + 1, y + 1, z);
                                    info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
                                    WeightedRandomChestContent.generateChestContents(par5Random, info.getItems(par5Random), var16, info.getCount(par5Random));/**/
                                    break;

                                case 3:
                                    for (up = 1; up < 10; up++)
                                    {
                                        world.setBlock(x + 2, y + up, z + 2, 0);
                                        world.setBlock(x - 2, y + up, z + 2, 0);
                                        world.setBlock(x + 2, y + up, z + 3, 0);
                                        world.setBlock(x - 2, y + up, z + 3, 0);
                                        world.setBlock(x + 2, y + up, z + 4, 0);
                                        world.setBlock(x - 2, y + up, z + 4, 0);
                                    }

                                    world.setBlock(x + 2, y,   z + 2, 7);
                                    world.setBlock(x - 2, y,   z + 2, 7);
                                    world.setBlock(x + 2, y,   z + 3, 7);
                                    world.setBlock(x - 2, y,   z + 3, 7);
                                    world.setBlock(x + 2, y,   z + 4, 7);
                                    world.setBlock(x - 2, y,   z + 4, 7);
                                    world.setBlock(x + 2, y + 6, z + 2, 7);
                                    world.setBlock(x - 2, y + 6, z + 2, 7);
                                    world.setBlock(x + 2, y + 6, z + 3, 7);
                                    world.setBlock(x - 2, y + 6, z + 3, 7);
                                    world.setBlock(x + 2, y + 6, z + 4, 7);
                                    world.setBlock(x - 2, y + 6, z + 4, 7);
                                    world.setBlock(x + 1, y + 6, z + 1, 7);
                                    world.setBlock(x,   y + 6, z + 1, 7);
                                    world.setBlock(x - 1, y + 6, z + 1, 7);
                                    world.setBlock(x + 1, y + 6, z + 5, 7);
                                    world.setBlock(x,   y + 6, z + 5, 7);
                                    world.setBlock(x - 1, y + 6, z + 5, 7);
                                    world.setBlock(x + 1, y + 4, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x,   y + 4, z,   DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 1, y + 4, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x + 1, y + 1, z + 6, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x,   y + 3, z + 6, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x - 1, y + 1, z + 6, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 3, y + 1, z + 4, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 3, y + 1, z + 4, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 2, y + 4, z + 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 2, y + 4, z + 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x + 2, y + 1, z + 5, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 2, y + 1, z + 5, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x, y + 10, z + 1,   7);
                                    world.setBlock(x, y + 10, z + 2,   7);
                                    world.setBlock(x, y + 10, z + 3,   7);
                                    world.setBlock(x, y + 10, z + 4,   7);
                                    world.setBlock(x, y + 10, z + 5,   7);
                                    world.setBlock(x + 1, y + 10, z + 1, 7);
                                    world.setBlock(x + 1, y + 10, z + 2, 7);
                                    world.setBlock(x + 1, y + 10, z + 3, 7);
                                    world.setBlock(x + 1, y + 10, z + 4, 7);
                                    world.setBlock(x + 1, y + 10, z + 5, 7);
                                    world.setBlock(x - 1, y + 10, z + 1, 7);
                                    world.setBlock(x - 1, y + 10, z + 2, 7);
                                    world.setBlock(x - 1, y + 10, z + 3, 7);
                                    world.setBlock(x - 1, y + 10, z + 4, 7);
                                    world.setBlock(x - 1, y + 10, z + 5, 7);
                                    world.setBlock(x + 2, y + 10, z + 2, 7);
                                    world.setBlock(x - 2, y + 10, z + 3, 7);
                                    world.setBlock(x + 2, y + 10, z + 4, 7);
                                    world.setBlock(x - 2, y + 10, z + 2, 7);
                                    world.setBlock(x + 2, y + 10, z + 3, 7);
                                    world.setBlock(x - 2, y + 10, z + 4, 7);
                                    world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x,   y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x,   y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 11, z + 4, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x,   y + 11, z + 4, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 1, y + 11, z + 4, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x + 1, y + 11, z + 3, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x,   y + 11, z + 3, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 11, z + 3, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 1, y + 11, z + 2, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x,   y + 11, z + 2, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 1, y + 11, z + 2, DecayingWorld.mazeWalls.blockID, 5, 3);

                                    do
                                    {
                                        ff = world.getBlockId(x, y + dwn, z + 6);
                                        dwn--;
                                    }
                                    while (ff == 0);

                                    world.setBlock(x, y + dwn, z + 6, Block.grass.blockID);
                                    dwn--;

                                    do
                                    {
                                        world.setBlock(x, y + dwn, z, 7 + 6);
                                        dwn--;
                                        ff = world.getBlockId(x, y + dwn, z + 6);
                                    }
                                    while (ff == blockID);

                                    world.setBlock(x, y + 14, z + 3, Block.glowStone.blockID);
                                    //System.out.println("Dungeon created at (" + x + "," + y + "," + z +")");
                                    world.setBlock(x, y + 6, z + 3, Block.mobSpawner.blockID);
                                    var19 = (TileEntityMobSpawner)world.getBlockTileEntity(x, y + 6, z + 3);

                                    if (var19 != null)
                                    {
                                        var19.func_98049_a().setMobID("Skeleton");
                                    }

                                    world.setBlock(x, y + 1, z + 1, Block.chest.blockID);
                                    var16 = (TileEntityChest)world.getBlockTileEntity(x, y + 1, z + 1);
                                    info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
                                    WeightedRandomChestContent.generateChestContents(par5Random, info.getItems(par5Random), var16, info.getCount(par5Random));/**/
                                    break;

                                case 4:
                                    for (up = 1; up < 10; up++)
                                    {
                                        world.setBlock(x + 2, y + up, z - 2, 0);
                                        world.setBlock(x - 2, y + up, z - 2, 0);
                                        world.setBlock(x + 2, y + up, z - 3, 0);
                                        world.setBlock(x - 2, y + up, z - 3, 0);
                                        world.setBlock(x + 2, y + up, z - 4, 0);
                                        world.setBlock(x - 2, y + up, z - 4, 0);
                                    }

                                    world.setBlock(x + 2, y,   z - 2, 7);
                                    world.setBlock(x - 2, y,   z - 2, 7);
                                    world.setBlock(x + 2, y,   z - 3, 7);
                                    world.setBlock(x - 2, y,   z - 3, 7);
                                    world.setBlock(x + 2, y,   z - 4, 7);
                                    world.setBlock(x - 2, y,   z - 4, 7);
                                    world.setBlock(x + 2, y + 6, z - 2, 7);
                                    world.setBlock(x - 2, y + 6, z - 2, 7);
                                    world.setBlock(x + 2, y + 6, z - 3, 7);
                                    world.setBlock(x - 2, y + 6, z - 3, 7);
                                    world.setBlock(x + 2, y + 6, z - 4, 7);
                                    world.setBlock(x - 2, y + 6, z - 4, 7);
                                    world.setBlock(x + 1, y + 6, z - 1, 7);
                                    world.setBlock(x,   y + 6, z - 1, 7);
                                    world.setBlock(x - 1, y + 6, z - 1, 7);
                                    world.setBlock(x + 1, y + 6, z - 5, 7);
                                    world.setBlock(x,   y + 6, z - 5, 7);
                                    world.setBlock(x - 1, y + 6, z - 5, 7);
                                    world.setBlock(x + 1, y + 4, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x,   y + 4, z,   DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 1, y + 4, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x + 1, y + 1, z - 6, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x,   y + 3, z - 6, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x - 1, y + 1, z - 6, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 3, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x + 3, y + 1, z - 4, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 3, y + 1, z - 4, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x + 2, y + 4, z - 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 2, y + 4, z - 1, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x + 2, y + 1, z - 5, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 2, y + 1, z - 5, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x, y + 10, z - 1,   7);
                                    world.setBlock(x, y + 10, z - 2,   7);
                                    world.setBlock(x, y + 10, z - 3,   7);
                                    world.setBlock(x, y + 10, z - 4,   7);
                                    world.setBlock(x, y + 10, z - 5,   7);
                                    world.setBlock(x + 1, y + 10, z - 1, 7);
                                    world.setBlock(x + 1, y + 10, z - 2, 7);
                                    world.setBlock(x + 1, y + 10, z - 3, 7);
                                    world.setBlock(x + 1, y + 10, z - 4, 7);
                                    world.setBlock(x + 1, y + 10, z - 5, 7);
                                    world.setBlock(x - 1, y + 10, z - 1, 7);
                                    world.setBlock(x - 1, y + 10, z - 2, 7);
                                    world.setBlock(x - 1, y + 10, z - 3, 7);
                                    world.setBlock(x - 1, y + 10, z - 4, 7);
                                    world.setBlock(x - 1, y + 10, z - 5, 7);
                                    world.setBlock(x + 2, y + 10, z - 2, 7);
                                    world.setBlock(x - 2, y + 10, z - 3, 7);
                                    world.setBlock(x + 2, y + 10, z - 4, 7);
                                    world.setBlock(x - 2, y + 10, z - 2, 7);
                                    world.setBlock(x + 2, y + 10, z - 3, 7);
                                    world.setBlock(x - 2, y + 10, z - 4, 7);
                                    world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x,   y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x,   y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 11, z - 2, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x,   y + 11, z - 2, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 1, y + 11, z - 2, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x + 1, y + 11, z - 3, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x,   y + 11, z - 3, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 11, z - 3, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x + 1, y + 11, z - 4, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x,   y + 11, z - 4, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 1, y + 11, z - 4, DecayingWorld.mazeWalls.blockID, 5, 3);

                                    do
                                    {
                                        ff = world.getBlockId(x, y + dwn, z - 6);
                                        dwn--;
                                    }
                                    while (ff == 0);

                                    world.setBlock(x, y + dwn, z - 6, Block.grass.blockID);
                                    dwn--;

                                    do
                                    {
                                        world.setBlock(x, y + dwn, z - 6, 7);
                                        dwn--;
                                        ff = world.getBlockId(x, y + dwn, z - 6);
                                    }
                                    while (ff == blockID);

                                    world.setBlock(x, y + 14, z - 3, Block.glowStone.blockID);
                                    //System.out.println("Dungeon created at (" + x + "," + y + "," + z +")");
                                    world.setBlock(x, y + 6, z - 3, Block.mobSpawner.blockID);
                                    var19 = (TileEntityMobSpawner)world.getBlockTileEntity(x, y + 6, z - 3);

                                    if (var19 != null)
                                    {
                                        var19.func_98049_a().setMobID("Skeleton");
                                    }

                                    world.setBlock(x, y + 1, z - 1, Block.chest.blockID);
                                    var16 = (TileEntityChest)world.getBlockTileEntity(x, y + 1, z - 1);
                                    info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
                                    WeightedRandomChestContent.generateChestContents(par5Random, info.getItems(par5Random), var16, info.getCount(par5Random));/**/
                                    break;

                                case 6:
                                    for (up = 1; up < 10; up++)
                                    {
                                        world.setBlock(x - 2, y + up, z + 2, 0);
                                        world.setBlock(x - 2, y + up, z - 2, 0);
                                        world.setBlock(x - 3, y + up, z + 2, 0);
                                        world.setBlock(x - 3, y + up, z - 2, 0);
                                        world.setBlock(x - 4, y + up, z + 2, 0);
                                        world.setBlock(x - 4, y + up, z - 2, 0);
                                    }

                                    world.setBlock(x - 2, y,   z + 2, 7);
                                    world.setBlock(x - 2, y,   z - 2, 7);
                                    world.setBlock(x - 3, y,   z + 2, 7);
                                    world.setBlock(x - 3, y,   z - 2, 7);
                                    world.setBlock(x - 4, y,   z + 2, 7);
                                    world.setBlock(x - 4, y,   z - 2, 7);
                                    world.setBlock(x - 2, y + 6, z + 2, 7);
                                    world.setBlock(x - 2, y + 6, z - 2, 7);
                                    world.setBlock(x - 3, y + 6, z + 2, 7);
                                    world.setBlock(x - 3, y + 6, z - 2, 7);
                                    world.setBlock(x - 4, y + 6, z + 2, 7);
                                    world.setBlock(x - 4, y + 6, z - 2, 7);
                                    world.setBlock(x - 1, y + 6, z + 1, 7);
                                    world.setBlock(x - 1, y + 6, z,   7);
                                    world.setBlock(x - 1, y + 6, z - 1, 7);
                                    world.setBlock(x - 5, y + 6, z + 1, 7);
                                    world.setBlock(x - 5, y + 6, z,   7);
                                    world.setBlock(x - 5, y + 6, z - 1, 7);
                                    world.setBlock(x,   y + 4, z + 1, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x,   y + 4, z,   DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x,   y + 4, z - 1, DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x - 6, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 6, y + 3, z,   DecayingWorld.mazeWalls.blockID, 9, 3);
                                    world.setBlock(x - 6, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 2, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 4, y + 1, z + 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 2, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 3, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 4, y + 1, z - 3, DecayingWorld.mazeWalls.blockID, 12, 3);
                                    world.setBlock(x - 1, y + 4, z + 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 1, y + 4, z - 2, DecayingWorld.mazeWalls.blockID, 8, 3);
                                    world.setBlock(x - 5, y + 1, z + 2, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 5, y + 1, z - 2, DecayingWorld.mazeWalls.blockID, 11, 3);
                                    world.setBlock(x - 1, y + 10, z,   7);
                                    world.setBlock(x - 2, y + 10, z,   7);
                                    world.setBlock(x - 3, y + 10, z,   7);
                                    world.setBlock(x - 4, y + 10, z,   7);
                                    world.setBlock(x - 5, y + 10, z,   7);
                                    world.setBlock(x - 1, y + 10, z + 1, 7);
                                    world.setBlock(x - 2, y + 10, z + 1, 7);
                                    world.setBlock(x - 3, y + 10, z + 1, 7);
                                    world.setBlock(x - 4, y + 10, z + 1, 7);
                                    world.setBlock(x - 5, y + 10, z + 1, 7);
                                    world.setBlock(x - 1, y + 10, z - 1, 7);
                                    world.setBlock(x - 2, y + 10, z - 1, 7);
                                    world.setBlock(x - 3, y + 10, z - 1, 7);
                                    world.setBlock(x - 4, y + 10, z - 1, 7);
                                    world.setBlock(x - 5, y + 10, z - 1, 7);
                                    world.setBlock(x - 2, y + 10, z + 2, 7);
                                    world.setBlock(x - 3, y + 10, z + 2, 7);
                                    world.setBlock(x - 4, y + 10, z + 2, 7);
                                    world.setBlock(x - 2, y + 10, z - 2, 7);
                                    world.setBlock(x - 3, y + 10, z - 2, 7);
                                    world.setBlock(x - 4, y + 10, z - 2, 7);
                                    world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 2, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x - 2, y + 11, z,   DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 2, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x - 3, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 3, y + 11, z,   DecayingWorld.mazeWalls.blockID, 3, 3);
                                    world.setBlock(x - 3, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 4, y + 11, z + 1, DecayingWorld.mazeWalls.blockID, 5, 3);
                                    world.setBlock(x - 4, y + 11, z,   DecayingWorld.mazeWalls.blockID, 4, 3);
                                    world.setBlock(x - 4, y + 11, z - 1, DecayingWorld.mazeWalls.blockID, 5, 3);

                                    do
                                    {
                                        ff = world.getBlockId(x - 6, y + dwn, z);
                                        dwn--;
                                    }
                                    while (ff == 0);

                                    world.setBlock(x - 6, y + dwn, z, Block.grass.blockID);
                                    dwn--;

                                    do
                                    {
                                        world.setBlock(x - 6, y + dwn, z, 7);
                                        dwn--;
                                        ff = world.getBlockId(x - 6, y + dwn, z);
                                    }
                                    while (ff == blockID);

                                    world.setBlock(x - 3, y + 14, z, Block.glowStone.blockID);
                                    //System.out.println("Dungeon created at (" + x + "," + y + "," + z +")");
                                    world.setBlock(x - 3, y + 6, z, Block.mobSpawner.blockID);
                                    var19 = (TileEntityMobSpawner)world.getBlockTileEntity(x - 3, y + 6, z);

                                    if (var19 != null)
                                    {
                                        var19.func_98049_a().setMobID("Skeleton");
                                    }

                                    world.setBlock(x - 1, y + 1, z, Block.chest.blockID);
                                    var16 = (TileEntityChest)world.getBlockTileEntity(x - 1, y + 1, z);
                                    info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
                                    WeightedRandomChestContent.generateChestContents(par5Random, info.getItems(par5Random), var16, info.getCount(par5Random));/**/
                                    break;

                                default:
                                    break;
                            }
                        }
                        else
                        {
                            world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                            world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }
                    }
                    else
                    {
                        if (world.getBlockId(x + 1, y, z + 1) != 7)
                        {
                            world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x + 1, y, z) != 7)
                        {
                            world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x + 1, y, z - 1) != 7)
                        {
                            world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x, y, z + 1) != 7)
                        {
                            world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x, y, z) != 7)
                        {
                            world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x, y, z - 1) != 7)
                        {
                            world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x - 1, y, z + 1) != 7)
                        {
                            world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x - 1, y, z) != 7)
                        {
                            world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }

                        if (world.getBlockId(x - 1, y, z - 1) != 7)
                        {
                            world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                        }
                    }

                    world.setBlock(x + 1, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y - 1, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y, z + 1, 7);
                    world.setBlock(x + 1, y, z, 7);
                    world.setBlock(x + 1, y, z - 1, 7);
                    world.setBlock(x,   y, z + 1, 7);
                    world.setBlock(x,   y, z - 1, 7);
                    world.setBlock(x - 1, y, z + 1, 7);
                    world.setBlock(x - 1, y, z, 7);
                    world.setBlock(x - 1, y, z - 1, 7);
                    int n = 1;
                    w = world.getBlockId(x, y - n, z);

                    while (w == blockID || w == Block.gravel.blockID || w == Block.lavaStill.blockID)
                    {
                        n++;
                        w = world.getBlockId(x, y - n, z);
                    }

                    world.setBlock(x, y - n, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                    //world.setBlock(x, y, z, 7);
                }
                else if (w == 1)
                {
                    world.setBlock(x + 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x + 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x + 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x - 1, y + 1, z + 1, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x - 1, y + 1, z, DecayingWorld.mazeWalls.blockID, 3, 3);
                    world.setBlock(x - 1, y + 1, z - 1, DecayingWorld.mazeWalls.blockID, 3, 3); /**/
                    world.setBlock(x + 1, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y - 1, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z + 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x - 1, y - 1, z - 1, DecayingWorld.mazeWalls.blockID, 0, 3);
                    world.setBlock(x + 1, y, z + 1, 7);
                    world.setBlock(x + 1, y, z, 7);
                    world.setBlock(x + 1, y, z - 1, 7);
                    world.setBlock(x, y, z + 1, 7);
                    world.setBlock(x, y, z - 1, 7);
                    world.setBlock(x - 1, y, z + 1, 7);
                    world.setBlock(x - 1, y, z, 7);
                    world.setBlock(x - 1, y, z - 1, 7);
                    int n = 1;
                    w = world.getBlockId(x, y - n, z);

                    while (w == blockID || w == Block.gravel.blockID || w == Block.lavaStill.blockID)
                    {
                        n++;
                        w = world.getBlockId(x, y - n, z);
                    }

                    world.setBlock(x, y - n, z, DecayingWorld.mazeWalls.blockID, 0, 3);
                }

                break;

            default:
                world.scheduleBlockUpdate(x, y, z, blockID, 300);
                /*world.setBlock(x, y, z, Block.grass.blockID);
                world.markBlockForUpdate(x, y, z);
                break;*/
        }

        //getLightValue(world, x, y, z);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        Random r = new Random();

        for (int i = -5; i < 6; i++)
        {
            for (int j = -5; j < 6; j++)
            {
                //if(r.nextDouble() > 0.8) {
                int w = world.getBlockId(x + (i * 3), y, z + (j * 3));

                if (w == Block.grass.blockID || w == Block.dirt.blockID || w == Block.stone.blockID || w == Block.gravel.blockID)
                {
                    world.setBlock(x + (i * 3), y, z + (j * 3), blockID, r.nextDouble() > 0.5 ? 3 : 1, 0);
                }

                //}
            }
        }

        world.scheduleBlockUpdate(x, y, z, blockID, 300);
        return 5;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 50);
        //world.setBlockMetadata(x, y, z, new Random().nextInt(2));
    }
}

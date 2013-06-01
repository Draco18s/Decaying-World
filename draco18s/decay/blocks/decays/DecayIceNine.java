package draco18s.decay.blocks.decays;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecayIceNine extends Block
{
    protected int height;

    public DecayIceNine(int par1, Material par3)
    {
        super(par1, par3);
        this.slipperiness = 1.01F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Ice-Nine");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:icenine");
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        //return null;
        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + 0.125F * var5 - 0.005F), (double)par4 + this.maxZ);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float var6 = (float)(2 * (var5)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (DecayingWorld.hardicenine)
        {
            int iID = DecayingWorld.iceIXshard.itemID;
            this.dropBlockAsItem(par1World, par3, par4, par5, iID, 1);
            par1World.setBlock(par3, par4, par5, 0);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return this.height;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return DecayingWorld.iceIXshard.itemID;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        boolean raining = (world.isRaining() && world.canBlockSeeTheSky(x, y, z));
        int m;
        int wID = world.getBlockId(x + 1, y, z);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x + 1, y, z);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x + 1, y, z, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x + 1, y, z, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x + 1, y, z, this.blockID, m, 3);
        }

        wID = world.getBlockId(x, y + 1, z);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x, y + 1, z);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x, y + 1, z, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x, y + 1, z, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x, y + 1, z, this.blockID, m, 3);
        }

        wID = world.getBlockId(x, y, z + 1);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x, y, z + 1);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x, y, z + 1, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x, y, z + 1, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x, y, z + 1, this.blockID, m, 3);
        }

        wID = world.getBlockId(x - 1, y, z);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x - 1, y, z);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x - 1, y, z, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x - 1, y, z, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x - 1, y, z, this.blockID, m, 3);
        }

        wID = world.getBlockId(x, y - 1, z);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x, y - 1, z);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x, y - 1, z, this.blockID, m, 3);
        }

        wID = world.getBlockId(x, y, z - 1);

        if (wID == 8 || wID == 9 || (wID >= 78 && wID <= 80))
        {
            m = 8 - world.getBlockMetadata(x, y, z - 1);

            if (m <= 0)
            {
                m = 8;
                world.scheduleBlockUpdate(x, y, z - 1, blockID, 2);
            }
            else
            {
                world.scheduleBlockUpdate(x, y, z - 1, blockID, 2);
            }

            if (wID == 78)
            {
                m = 1;
            }

            world.setBlock(x, y, z - 1, this.blockID, m, 3);
        }

        m = world.getBlockMetadata(x, y, z);
        wID = world.getBlockId(x, y - 1, z);

        if (m <= 3 && wID != blockID && !world.isBlockOpaqueCube(x, y - 1, z))
        {
            if (m > 1)
            {
                m--;
            }

            world.setBlock(x, y - 1, z, blockID, m, 3);
            world.setBlock(x, y, z, 0);
        }
        else if (wID == blockID)
        {
            m = m + world.getBlockMetadata(x, y - 1, z);

            if (m <= 8)
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, m, 0);
                world.setBlock(x, y, z, 0);
            }
        }

        if (raining)
        {
            m = world.getBlockMetadata(x, y, z);
            m++;

            if (m <= 8)
            {
                world.setBlockMetadataWithNotify(x, y, z, m, 0);
                world.markBlockForUpdate(x, y, z);
            }
            else
            {
                int wIDt = world.getBlockId(x, y + 1, z);
                wID = world.getBlockId(x + 1, y, z);

                if (wID == 0)
                {
                    int wIDl = world.getBlockId(x + 1, y - 1, z);
                    m = world.getBlockMetadata(x + 1, y - 1, z);

                    if (wIDl == this.blockID && m < 8)
                    {
                        world.setBlock(x + 1, y - 1, z, blockID, m + 1, 0);
                        world.markBlockForUpdate(x + 1, y - 1, z);
                    }
                    else if (wIDl != 0)
                    {
                        world.setBlock(x + 1, y, z, this.blockID, 1, 0);
                        world.markBlockForUpdate(x + 1, y, z);
                    }
                    else if (wIDt != blockID)
                    {
                        world.setBlock(x + 1, y - 1, z, this.blockID, 1, 0);
                        world.markBlockForUpdate(x + 1, y - 1, z);
                        world.scheduleBlockUpdate(x + 1, y - 1, z, blockID, 0);
                    }
                }

                wID = world.getBlockId(x - 1, y, z);

                if (wID == 0)
                {
                    int wIDl = world.getBlockId(x - 1, y - 1, z);
                    m = world.getBlockMetadata(x - 1, y - 1, z);

                    if (wIDl == this.blockID && m < 8)
                    {
                        world.setBlock(x - 1, y - 1, z, blockID, m + 1, 0);
                        world.markBlockForUpdate(x - 1, y - 1, z);
                    }
                    else if (wIDl != 0)
                    {
                        world.setBlock(x - 1, y, z, this.blockID, 1, 0);
                        world.markBlockForUpdate(x - 1, y, z);
                    }
                    else if (wIDt != blockID)
                    {
                        world.setBlock(x - 1, y - 1, z, this.blockID, 1, 0);
                        world.markBlockForUpdate(x - 1, y - 1, z);
                        world.scheduleBlockUpdate(x - 1, y - 1, z, blockID, 0);
                    }
                }

                wID = world.getBlockId(x, y, z + 1);

                if (wID == 0)
                {
                    int wIDl = world.getBlockId(x, y - 1, z + 1);
                    m = world.getBlockMetadata(x, y - 1, z + 1);

                    if (wIDl == this.blockID && m < 8)
                    {
                        world.setBlock(x, y - 1, z + 1, blockID, m + 1, 0);
                        world.markBlockForUpdate(x, y - 1, z + 1);
                    }
                    else if (wIDl != 0)
                    {
                        world.setBlock(x, y, z + 1, this.blockID, 1, 0);
                        world.markBlockForUpdate(x, y, z + 1);
                    }
                    else if (wIDt != blockID)
                    {
                        world.setBlock(x, y - 1, z + 1, this.blockID, 1, 0);
                        world.markBlockForUpdate(x, y - 1, z + 1);
                        world.scheduleBlockUpdate(x, y - 1, z + 1, blockID, 0);
                    }
                }

                wID = world.getBlockId(x, y, z - 1);

                if (wID == 0)
                {
                    int wIDl = world.getBlockId(x, y - 1, z - 1);
                    m = world.getBlockMetadata(x, y - 1, z - 1);

                    if (wIDl == this.blockID && m < 8)
                    {
                        world.setBlock(x, y - 1, z - 1, blockID, m + 1, 0);
                        world.markBlockForUpdate(x, y - 1, z - 1);
                    }
                    else if (wIDl != 0)
                    {
                        world.setBlock(x, y, z - 1, this.blockID, 1, 0);
                        world.markBlockForUpdate(x, y, z - 1);
                    }
                    else if (wIDt != blockID)
                    {
                        world.setBlock(x, y - 1, z - 1, this.blockID, 1, 0);
                        world.markBlockForUpdate(x, y - 1, z - 1);
                        world.scheduleBlockUpdate(x, y - 1, z - 1, blockID, 0);
                    }
                }

                if (wIDt == 0)
                {
                    world.setBlock(x, y + 1, z, this.blockID, 1, 0);
                    world.markBlockForUpdate(x, y + 1, z);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    @Override
    public int onBlockPlaced(World par1World, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        par1World.scheduleBlockUpdate(x, y, z, blockID, 6);
        return 8;
    }

    public void onNeighborBlockChange(World par1World, int x, int y, int z, int bID)
    {
        if (bID != 0)
        {
            par1World.scheduleBlockUpdate(x, y, z, blockID, 6);
        }
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        EntityItem i = new EntityItem(par1World);

        if (par5Entity.getClass() != i.getClass())
        {
            par5Entity.attackEntityFrom(new ColdDamage(), 1);
        }
    }

    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        this.height = par1World.getBlockMetadata(par2, par3, par4);
    }
}
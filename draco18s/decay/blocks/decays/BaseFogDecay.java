package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseFogDecay extends BlockSand
{
    public BaseFogDecay(int par1)
    {
        super(par1, Material.air);
        setHardness(-1F);
        setUnlocalizedName("Fog");
        setLightOpacity(1);
        setTickRandomly(true);
        disableStats();
        setCreativeTab(CreativeTabs.tabBlock);
        fallInstantly = true;
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:fog");
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int par2, int par3, int par4, int par5)
    {
        //Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        //return material == this.blockMaterial ? false : true;
        int b = world.getBlockId(par2, par3, par4);
        return (b == blockID) ? (par5%2==0) : !world.isBlockOpaqueCube(par2, par3, par4);
        //return !world.isBlockOpaqueCube(par2, par3, par4);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, 1, 3);

                if (world.getBlockId(x, y - 1, z) == 0)
                {
                    world.setBlock(x, y, z, 0, 0, 3);
                    world.setBlock(x, y - 1, z, blockID, 0, 3);
                    world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);

                    if (world.getBlockId(x, y + 1, z) == blockID)
                    {
                        world.setBlock(x, y + 1, z, blockID, 0, 3);
                        world.scheduleBlockUpdate(x, y + 1, z, blockID, 2);
                    }
                }

                //else
                //world.scheduleBlockUpdate(x, y, z, blockID, 300+random.nextInt(300));
            }
            else
            {
                int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};

                if (wID[0] == 0)
                {
                    world.setBlock(x + 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x + 1, y, z, blockID, 1);
                }

                if (wID[1] == 0)
                {
                    world.setBlock(x - 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x - 1, y, z, blockID, 1);
                }

                if (wID[2] == 0 && wID[3] != blockID)
                {
                    world.setBlock(x, y + 1, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                }

                if (wID[3] == blockID && (world.getBlockId(x, y - 2, z) == blockID))
                {
                    world.setBlock(x, y, z, 0, 0, 3);

                    if (wID[2] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y + 1, z, 0, 3);
                        world.scheduleBlockUpdate(x, y, z, blockID, 2);
                    }
                }

                if (wID[4] == 0)
                {
                    world.setBlock(x, y, z + 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x, y, z + 1, blockID, 1);
                }

                if (wID[5] == 0)
                {
                    world.setBlock(x, y, z - 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x, y, z - 1, blockID, 1);
                }

                if (wID[3] == 0)
                {
                    world.setBlock(x, y, z, 0, 0, 3);
                    world.setBlock(x, y - 1, z, blockID, 0, 3);
                    world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);

                    if (wID[0] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x + 1, y, z, 0, 3);
                    }

                    if (wID[1] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x - 1, y, z, 0, 3);
                    }

                    if (wID[2] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y + 1, z, 0, 3);
                    }

                    if (wID[4] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y, z + 1, 0, 3);
                    }

                    if (wID[0] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y, z - 1, 0, 3);
                    }
                }
                else if (wID[0] == 0 || wID[1] == 0 || wID[2] == 0 || wID[4] == 0 || wID[5] == 0)
                {
                    //world.scheduleBlockUpdate(x, y, z, blockID, 300+random.nextInt(300));
                }
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block)
    {
        if (this.canFallBelow(world, x, y - 1, z))
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            world.scheduleBlockUpdate(x, y, z, blockID, 0);
        }

        //else
        //world.scheduleBlockUpdate(x, y, z, blockID, 600);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 2);
        //this.tryToFall(world, x, y, z);
        return 0;
    }

    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
        fallInstantly = true;

        if (this.canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte b0 = 32;

            if (!fallInstantly && par1World.checkChunksExist(par2 - b0, par3 - b0, par4 - b0, par2 + b0, par3 + b0, par4 + b0))
            {
                if (!par1World.isRemote)
                {
                    EntityFallingSand entityfallingsand = new EntityFallingSand(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.blockID, par1World.getBlockMetadata(par2, par3, par4));
                    this.onStartFalling(entityfallingsand);
                    par1World.spawnEntityInWorld(entityfallingsand);
                }
            }
            else
            {
                par1World.setBlock(par2, par3, par4, 0, 0, 3);

                while (this.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this.blockID);
                }
            }
        }
    }

    public static boolean canFallBelow(World par0World, int par1, int par2, int par3)
    {
        int l = par0World.getBlockId(par1, par2, par3);

        if (l == 0 || l == Block.fire.blockID || l == Block.tallGrass.blockID || l == Block.plantRed.blockID || l == Block.plantYellow.blockID || l == Block.mushroomBrown.blockID || l == Block.mushroomRed.blockID || l == Block.crops.blockID || l == Block.vine.blockID)
        {
            return true;
        }

        return false;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        return null;
    }
}

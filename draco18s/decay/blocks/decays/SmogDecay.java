package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.ColdDamage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SmogDecay extends Block
{
    public SmogDecay(int par1)
    {
        super(par1, Material.air);
        setHardness(-1F);
        setUnlocalizedName("Smog");
        setLightOpacity(1);
        disableStats();
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:smog");
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int par2, int par3, int par4, int par5)
    {
        //Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        //return material == this.blockMaterial ? false : true;
        int b = world.getBlockId(par2, par3, par4);
        return (b == blockID) ? false : !world.isBlockOpaqueCube(par2, par3, par4);
        //return !world.isBlockOpaqueCube(par2, par3, par4);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};

        if (wID[0] == 0)
        {
            world.setBlock(x + 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[1] == 0)
        {
            world.setBlock(x - 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[2] == 0)
        {
            world.setBlock(x, y + 1, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[3] == 0)
        {
            world.setBlock(x, y - 1, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[4] == 0)
        {
            world.setBlock(x, y, z + 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[5] == 0)
        {
            world.setBlock(x, y, z - 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 3);
        }

        if (wID[0] == 0 || wID[1] == 0 || wID[2] == 0 || wID[3] == 0 || wID[4] == 0 || wID[5] == 0)
        {
            world.scheduleBlockUpdate(x, y, z, blockID, 300 + random.nextInt(300));
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 600);
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
        if (!par1World.isRemote && par5Entity instanceof EntityPlayer)
        {
            ((EntityPlayer)par5Entity).addExhaustion(0.002F);
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 300);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        return null;
    }
}

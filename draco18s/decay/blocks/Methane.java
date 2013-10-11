package draco18s.decay.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class Methane extends BlockSand
{
    public Methane(int par1)
    {
        super(par1, Material.air);
        setHardness(-1F);
        setUnlocalizedName("Methane");
        setLightOpacity(0);
        setTickRandomly(true);
        disableStats();
        //setCreativeTab(CreativeTabs.tabBlock);
        setResistance(-1.2F);
    }

    @Override
    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:methane_solid");
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
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int c = canFall(world, x, y - 1, z);

        if (c > 0)
        {
            world.setBlockToAir(x, y, z);
            if (c % 2 == 1)
            {
                world.setBlock(x, y - 1, z, blockID, 0, 2);
            }
            else
            {
                c >>= 1;
                List<Long> a = new ArrayList<Long>();

                if (c % 2 == 1)
                {
                    a.add((long) 1);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 2);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 3);
                }

                c >>= 1;

                if (c % 2 == 1)
                {
                    a.add((long) 4);
                }

                int r = random.nextInt(a.size());
                r = (int)(a.get(r) & 15);

                switch (r)
                {
                    case 1:
                        world.setBlock(x + 1, y - 1, z, blockID, 0, 2);
                        break;

                    case 2:
                        world.setBlock(x - 1, y - 1, z, blockID, 0, 2);
                        break;

                    case 3:
                        world.setBlock(x, y - 1, z + 1, blockID, 0, 2);
                        break;

                    case 4:
                        world.setBlock(x, y - 1, z - 1, blockID, 0, 2);
                        break;
                }
            }
        }
        else if (world.getBlockMetadata(x, y, z) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 0);
            //world.scheduleBlockUpdate(x, y, z, blockID, 300+random.nextInt(300));
        }
        else
        {
            //System.out.println("Spreading");
            int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};

            if (wID[0] == Block.torchWood.blockID || wID[1] == Block.torchWood.blockID || wID[2] == Block.torchWood.blockID || wID[3] == Block.torchWood.blockID || wID[4] == Block.torchWood.blockID || wID[5] == Block.torchWood.blockID)
            {
                world.setBlock(x, y, z, Block.fire.blockID, 0, 3);
                world.scheduleBlockUpdate(x + 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x - 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y + 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y - 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z + 1, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z - 1, Block.fire.blockID, 2);
                //if(random.nextDouble() < 0.1) {
                /*EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), null);
                entitytntprimed.fuse = 10;
                world.spawnEntityInWorld(entitytntprimed);*/
                world.newExplosion(null, x, y, z, 4F, true, true);
                //}
                return;
            }
            else if (wID[0] == Block.fire.blockID || wID[1] == Block.fire.blockID || wID[2] == Block.fire.blockID || wID[3] == Block.fire.blockID || wID[4] == Block.fire.blockID || wID[5] == Block.fire.blockID)
            {
                world.setBlock(x, y, z, Block.fire.blockID, 0, 3);
                world.scheduleBlockUpdate(x + 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x - 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y + 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y - 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z + 1, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z - 1, Block.fire.blockID, 2);

                if (random.nextDouble() < 0.005)
                {
                    world.newExplosion(null, x, y, z, 4F, true, true);
                }

                return;
            }

            int[] wID2 = {world.getBlockId(x + 1, y-1, z), world.getBlockId(x - 1, y-1, z), world.getBlockId(x, y-1, z + 1), world.getBlockId(x, y-1, z - 1), world.getBlockId(x + 1, y-1, z+1), world.getBlockId(x - 1, y-1, z-1), world.getBlockId(x-1, y-1, z + 1), world.getBlockId(x+1, y-1, z - 1)};
            if (wID2[0] == Block.fire.blockID || wID2[1] == Block.fire.blockID || wID2[2] == Block.fire.blockID || wID2[3] == Block.fire.blockID || wID2[4] == Block.fire.blockID || wID2[5] == Block.fire.blockID || wID2[6] == Block.fire.blockID || wID2[7] == Block.fire.blockID)
            {
            	world.setBlock(x, y, z, Block.fire.blockID, 0, 3);
                world.scheduleBlockUpdate(x + 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x - 1, y, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y + 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y - 1, z, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z + 1, Block.fire.blockID, 2);
                world.scheduleBlockUpdate(x, y, z - 1, Block.fire.blockID, 2);

                if (random.nextDouble() < 0.005)
                {
                    world.newExplosion(null, x, y, z, 4F, true, true);
                }

                return;
            }

            /*//world.canLightningStrikeAt(x, y, z);
            if (wID[2] == 0 && !world.canBlockSeeTheSky(x, y + 1, z))
            {
                double m = 0.4;

                if (y > 20)
                {
                    m -= 0.1;
                }

                if (y > 30)
                {
                    m -= 0.1;
                }

                if (y > 40)
                {
                    m -= 0.1;
                }

                if (wID[0] == 0)
                {
                    world.setBlock(x + 1, y, z, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }

                if (wID[1] == 0)
                {
                    world.setBlock(x - 1, y, z, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }

                if (wID[2] == 0)
                {
                    world.setBlock(x, y + 1, z, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }

                if (wID[3] == 0)
                {
                    world.setBlock(x, y - 1, z, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }

                if (wID[4] == 0)
                {
                    world.setBlock(x, y, z + 1, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }

                if (wID[5] == 0)
                {
                    world.setBlock(x, y, z - 1, random.nextDouble() > m ? 0 : blockID, 0, 2);
                }
            }*/
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block)
    {
        if (block == 0 || block == blockID || block == Block.fire.blockID)
        {
            int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};

            if (wID[0] == Block.fire.blockID || wID[1] == Block.fire.blockID || wID[2] == Block.fire.blockID || wID[3] == Block.fire.blockID || wID[4] == Block.fire.blockID || wID[5] == Block.fire.blockID)
            {
                world.scheduleBlockUpdate(x, y, z, blockID, 2);
            }
            else if (wID[0] == Block.torchWood.blockID || wID[1] == Block.torchWood.blockID || wID[2] == Block.torchWood.blockID || wID[3] == Block.torchWood.blockID || wID[4] == Block.torchWood.blockID || wID[5] == Block.torchWood.blockID)
            {
                world.scheduleBlockUpdate(x, y, z, blockID, 2);
            }
        }
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
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion par5Explosion)
    {
        world.setBlock(x, y, z, Block.fire.blockID);

        if (new Random().nextDouble() < 0.005)
        {
            world.newExplosion(null, x, y, z, 4F, true, true);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            if (par5Entity instanceof EntityPlayer)
            {
                ((EntityPlayer)par5Entity).addExhaustion(0.02F);
            }

            if ((par5Entity instanceof EntityChicken || par5Entity instanceof EntityBat) && (new Random().nextDouble() < 0.025))
            {
                par5Entity.attackEntityFrom(DamageSource.drown, 1);
            }
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, blockID, 2);
    }

    public static int canFall(World par0World, int par1, int par2, int par3)
    {
        int[] l = {par0World.getBlockId(par1, par2, par3), par0World.getBlockId(par1 + 1, par2, par3), par0World.getBlockId(par1 - 1, par2, par3), par0World.getBlockId(par1, par2, par3 + 1), par0World.getBlockId(par1, par2, par3 - 1)};
        int[] l2 = {0, par0World.isBlockNormalCubeDefault(par1 + 1, par2 + 1, par3, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1 - 1, par2 + 1, par3, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1, par2 + 1, par3 + 1, false) ? 1 : 0, par0World.isBlockNormalCubeDefault(par1, par2 + 1, par3 - 1, false) ? 1 : 0};
        int r = 0;

        for (int i = 0; i < 5; i++)
        {
            if (l[i] == 0 || l[i] == Block.fire.blockID)
            {
                if (l2[i] == 0)
                {
                    r += (1 << i);
                }
            }
        }

        return r;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        return null;
    }
}

package draco18s.decay.blocks.decays;

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
import net.minecraft.enchantment.EnchantmentHelper;
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

public class MethaneEmitter extends Block
{
    public MethaneEmitter(int par1)
    {
        super(par1, Material.rock);
        setHardness(2.5F);
        setUnlocalizedName("Methane Emitter");
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabBlock);
        setResistance(100F);
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:methaneemitter");
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};
    	if (!world.canBlockSeeTheSky(x, y + 1, z))
        {
            double m = 0.75;

            if (wID[0] == 0)
            {
                world.setBlock(x + 1, y, z, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }

            if (wID[1] == 0)
            {
                world.setBlock(x - 1, y, z, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }

            if (wID[2] == 0)
            {
                world.setBlock(x, y + 1, z, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }

            if (wID[3] == 0)
            {
                world.setBlock(x, y - 1, z, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }

            if (wID[4] == 0)
            {
                world.setBlock(x, y, z + 1, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }

            if (wID[5] == 0)
            {
                world.setBlock(x, y, z - 1, random.nextDouble() > m ? 0 : DecayingWorld.methanegas.blockID, 0, 2);
            }
        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer par2EntityPlayer, int x, int y, int z, int meta)
    {
    	if (!EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
        {
    		world.newExplosion(null, x, y, z, 4F, true, true);
    	}
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }
}

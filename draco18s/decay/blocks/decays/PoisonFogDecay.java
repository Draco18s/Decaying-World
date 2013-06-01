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

public class PoisonFogDecay extends BaseFogDecay
{
    public PoisonFogDecay(int par1)
    {
        super(par1);
        setUnlocalizedName("Poison Fog");
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:pfog");
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            if (par5Entity instanceof EntityLiving)
            {
                if (((EntityLiving)par5Entity).getActivePotionEffect(Potion.poison) == null)
                {
                    ((EntityLiving)par5Entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 25));
                }

                if (par5Entity instanceof EntityPlayer)
                {
                    ((EntityPlayer)par5Entity).addExhaustion(0.0005F);
                }
            }
        }
    }
}

package draco18s.decay.items;

import java.util.Random;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class IceNineShard extends Item
{
    public IceNineShard(int par1)
    {
        super(par1);
        //setIconIndex(0);
        setMaxStackSize(16);
        setUnlocalizedName("Shard of Ice-Nine");
        setCreativeTab(CreativeTabs.tabMisc);
    }

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par3Entity instanceof EntityPlayer && par2World.rand.nextDouble() < 0.005 * par1ItemStack.stackSize)
        {
            EntityPlayer p = (EntityPlayer)par3Entity;
            DamageSource par1DamageSource = new ColdDamage();
            p.attackEntityFrom(par1DamageSource, 1);
        }
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
    	itemIcon = iconRegister.registerIcon("DecayingWorld:iceshard");
    }

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (var4 == null || !DecayingWorld.hardicenine)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.typeOfHit == EnumMovingObjectType.TILE)
            {
                int var5 = var4.blockX;
                int var6 = var4.blockY;
                int var7 = var4.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlockMaterial(var5, var6, var7) == Material.water)
                {
                    int m = 8 - par2World.getBlockMetadata(var5, var6, var7);

                    if (m <= 0)
                    {
                        m = 8;
                    }

                    par2World.setBlock(var5, var6, var7, DecayingWorld.iceIX.blockID, m, 3);
                }
                else if (par2World.getBlockId(var5, var6, var7) == DecayingWorld.iceIX.blockID)
                {
                    int m = par2World.getBlockMetadata(var5, var6, var7) + 1;

                    if (m > 8)
                    {
                        m -= 8;
                        par2World.setBlock(var5, var6 + 1, var7, DecayingWorld.iceIX.blockID, m, 3);
                    }
                    else
                    {
                        par2World.setBlock(var5, var6, var7, DecayingWorld.iceIX.blockID, m, 3);
                    }
                }
                else
                {
                    par2World.setBlock(var5, var6 + 1, var7, DecayingWorld.iceIX.blockID, 1, 3);
                }

                if (!par3EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return par1ItemStack;
        }
    }
}

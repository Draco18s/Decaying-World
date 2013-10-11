package draco18s.decay.items;

import draco18s.decay.DecayingWorld;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class IceNineBottle extends Item
{
    public IceNineBottle(int par1)
    {
        super(par1);
        //setIconIndex(1);
        setUnlocalizedName("Bottle of Ice-Nine");
        setMaxStackSize(1);
        setMaxDamage(16);
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
    	itemIcon = iconRegister.registerIcon("DecayingWorld:icebottle");
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
                    par2World.scheduleBlockUpdate(var5, var6, var7, DecayingWorld.iceIX.blockID, 10);

                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        System.out.println(par1ItemStack.getItemDamage());

                        if (par1ItemStack.getItemDamage() < 16)
                        {
                            par1ItemStack.damageItem(1, par3EntityPlayer);
                        }
                        else
                        {
                            return new ItemStack(Item.glassBottle, 1);
                        }
                    }
                }
                else if (par2World.getBlockMaterial(var5, var6 + 1, var7) == Material.water)
                {
                    int m = 8 - par2World.getBlockMetadata(var5, var6 + 1, var7);

                    if (m <= 0)
                    {
                        m = 8;
                    }

                    par2World.setBlock(var5, var6 + 1, var7, DecayingWorld.iceIX.blockID, m, 3);
                    par2World.scheduleBlockUpdate(var5, var6 + 1, var7, DecayingWorld.iceIX.blockID, 10);

                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        System.out.println(par1ItemStack.getItemDamage());

                        if (par1ItemStack.getItemDamage() < 16)
                        {
                            par1ItemStack.damageItem(1, par3EntityPlayer);
                        }
                        else
                        {
                            return new ItemStack(Item.glassBottle, 1);
                        }
                    }
                }
            }

            return par1ItemStack;
        }
    }
}

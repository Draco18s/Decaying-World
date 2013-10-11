package draco18s.decay.items;

import draco18s.decay.entities.EntityLifeBomb;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLifeBomb extends Item {
	public ItemLifeBomb(int par1) {
		super(par1);
        maxStackSize = 16;
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("Life Bomb");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("DecayingWorld:lifebomb");
    }

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityLifeBomb(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
}

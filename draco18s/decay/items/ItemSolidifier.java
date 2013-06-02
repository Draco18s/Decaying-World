package draco18s.decay.items;

import draco18s.decay.entities.EntitySolidifier;
import draco18s.decay.entities.EntityTreant;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSolidifier extends Item {
	public ItemSolidifier(int par1) {
		super(par1);
        maxStackSize = 16;
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("Energy Absorber");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
    {
		itemIcon = par1IconRegister.registerIcon("DecayingWorld:solidifier");
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
            par2World.spawnEntityInWorld(new EntitySolidifier(par2World, par3EntityPlayer));
        	/*EntityTreant entitychicken = new EntityTreant(par2World);
            entitychicken.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
            par2World.spawnEntityInWorld(entitychicken);*/
        }

        return par1ItemStack;
    }
}

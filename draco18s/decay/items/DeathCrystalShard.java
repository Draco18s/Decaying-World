package draco18s.decay.items;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;
import draco18s.decay.NegativeDamage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class DeathCrystalShard extends Item
{
    public DeathCrystalShard(int par1)
    {
        super(par1);
        //setIconIndex(0);
        setMaxStackSize(16);
        setUnlocalizedName("Shard of Death Crystal");
        setCreativeTab(CreativeTabs.tabMisc);
        setPotionEffect(PotionHelper.spiderEyeEffect + PotionHelper.fermentedSpiderEyeEffect);
    }

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par3Entity instanceof EntityPlayer && par2World.rand.nextDouble() < 0.005 * par1ItemStack.stackSize)
        {
        	EntityPlayer player = (EntityPlayer)par3Entity;
            NBTTagCompound nbt = player.getEntityData();
            int timer = Math.max(nbt.getInteger("ExpDrainTimer"), 0) + 1;
            if (timer > 90) {
            	timer = new Random().nextInt(60);
            	player.experienceTotal--;
            	if(player.experience > 0 && player.experienceLevel > 0)
            		player.experience -= 1F/player.xpBarCap();
            	else if(player.experienceLevel > 0) {
            		player.experienceLevel--;
            		player.experience = 1F;
            	}
            	else {
            		player.attackEntityFrom(NegativeDamage.type, 1);
            	}
            }
        }
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
    	itemIcon = iconRegister.registerIcon("DecayingWorld:deathshard");
    }
}

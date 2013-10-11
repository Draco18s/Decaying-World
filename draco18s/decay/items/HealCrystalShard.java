package draco18s.decay.items;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;

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

public class HealCrystalShard extends Item
{
    public HealCrystalShard(int par1)
    {
        super(par1);
        //setIconIndex(0);
        setMaxStackSize(16);
        setUnlocalizedName("Shard of Healing Crystal");
        setCreativeTab(CreativeTabs.tabMisc);
        setPotionEffect(PotionHelper.speckledMelonEffect);
    }

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (par3Entity instanceof EntityPlayer && par2World.rand.nextDouble() < 0.0025 * par1ItemStack.stackSize)
        {
        	EntityPlayer var5 = (EntityPlayer)par3Entity;
            NBTTagCompound nbt = var5.getEntityData();
            float hpo = nbt.getFloat("HealthOverflow");
            int timer = Math.max(nbt.getInteger("HealthOverflowTimer"), 0) + 1;

            if (timer > 180)
            {
                hpo++;
                timer = new Random().nextInt(60);
            }

            float hp = var5.getHealth();
            float mhp = var5.getMaxHealth();
            float newhp = hp;
            float newhpo = hpo;

            if (hp < mhp)
            {
                newhp = Math.min(hp + hpo, mhp);
                newhpo = Math.max(hp + hpo - mhp, 0);
                var5.heal(newhp - hp);
            }

            nbt.setFloat("HealthOverflow", newhpo);
            nbt.setInteger("HealthOverflowTimer", timer);

            ByteArrayOutputStream bt = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bt);

            try
            {
                out.writeInt(1);
                out.writeFloat(newhpo);
                Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                PacketDispatcher.sendPacketToAllAround(var5.posX, var5.posY, var5.posZ, 0.01, var5.worldObj.provider.dimensionId, packet);
            }
            catch (IOException ex)
            {
                System.out.println("couldnt send packet!");
            }
        }
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
    	itemIcon = iconRegister.registerIcon("DecayingWorld:healshard");
    }
}

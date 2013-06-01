package draco18s.decay.instability.effects;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.ColdDamage;

public class EffectWildTemps implements IEnvironmentalEffect
{
    private int twilightTicks;

    public EffectWildTemps()
    {
        twilightTicks = 0;
    }

    @Override
    public void onChunkPopulate(World worldObj, Random rand, int i, int j)
    {
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
        int s = worldObj.calculateSkylightSubtracted(0);

        if (s < 7)
        {
            twilightTicks = 0;

            if (!worldObj.isRemote && chunk.hasEntities)
            {
                for (int var3 = chunk.entityLists.length - 1; var3 >= 0; var3--)
                {
                    //int var3 = worldObj.rand.nextInt(chunk.entityLists.length);
                    List var4 = chunk.entityLists[var3];

                    if (var4.size() > 0)
                    {
                        for (int entind = var4.size() - 1; entind >= 0; entind--)
                        {
                            Entity e = (Entity)var4.get(entind);

                            if (e instanceof EntityLiving && worldObj.canBlockSeeTheSky(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ)))
                            {
                                EntityLiving var5 = (EntityLiving)e;
                                NBTTagCompound nbt = var5.getEntityData();
                                int timer = Math.max(nbt.getInteger("TemperatureTimer"), 0) + (7-s);

                                //System.out.println("Timer: " +timer);
                                if (timer > 490)
                                {
                                    timer = new Random().nextInt(20);
                                    e.setFire(7-s);
                                }
                                nbt.setInteger("TemperatureTimer", timer);
                            }
                        }
                    }
                }
            }
        }
        else if (s > 9)
        {
            twilightTicks = 0;

            if (!worldObj.isRemote && chunk.hasEntities)
            {
                for (int var3 = chunk.entityLists.length - 1; var3 >= 0; var3--)
                {
                    //int var3 = worldObj.rand.nextInt(chunk.entityLists.length);
                    List var4 = chunk.entityLists[var3];

                    if (var4.size() > 0)
                    {
                        for (int entind = var4.size() - 1; entind >= 0; entind--)
                        {
                            Entity e = (Entity)var4.get(entind);

                            if (e instanceof EntityLiving && worldObj.canBlockSeeTheSky(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ)))
                            {
                                EntityLiving var5 = (EntityLiving)e;
                                NBTTagCompound nbt = var5.getEntityData();
                                int timer = Math.max(nbt.getInteger("TemperatureTimer"), 0) + (s-9);

                                //System.out.println("Timer: " +timer);
                                if (timer > 490)
                                {
                                    timer = new Random().nextInt(20);
                                    e.attackEntityFrom(new ColdDamage(), 1 + (worldObj.isRaining() ? 1 : 0));
                                }
                                nbt.setInteger("TemperatureTimer", timer);
                                if (e instanceof EntityPlayer)
                                {
                                    ByteArrayOutputStream bt = new ByteArrayOutputStream();
                                    DataOutputStream out = new DataOutputStream(bt);

                                    try
                                    {
                                        out.writeInt(3);
                                        out.writeInt(timer);
                                        Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                                        Player player = (Player)e;
                                        PacketDispatcher.sendPacketToPlayer(packet, player);
                                    }
                                    catch (IOException ex)
                                    {
                                        System.out.println("couldnt send packet!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
        	if (!worldObj.isRemote && chunk.hasEntities)
            {
                for (int var3 = chunk.entityLists.length - 1; var3 >= 0; var3--)
                {
                    //int var3 = worldObj.rand.nextInt(chunk.entityLists.length);
                    List var4 = chunk.entityLists[var3];

                    if (var4.size() > 0)
                    {
                        for (int entind = var4.size() - 1; entind >= 0; entind--)
                        {
                            Entity e = (Entity)var4.get(entind);

                            if (e instanceof EntityLiving && worldObj.canBlockSeeTheSky(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ)))
                            {
                                EntityLiving var5 = (EntityLiving)e;
                                NBTTagCompound nbt = var5.getEntityData();
                                int timer = Math.max(nbt.getInteger("TemperatureTimer"), 0) + 2;

                                //System.out.println("Timer: " +timer);
                                if (timer > 490)
                                {
                                    timer = new Random().nextInt(20);
                                    if (worldObj.rand.nextBoolean())
                                    {
                                        var5.setFire(4);
                                    }
                                    else
                                    {
                                        var5.attackEntityFrom(new ColdDamage(), 4);
                                    }
                                }
                                nbt.setInteger("TemperatureTimer", timer);
                                if (e instanceof EntityPlayer)
                                {
                                    ByteArrayOutputStream bt = new ByteArrayOutputStream();
                                    DataOutputStream out = new DataOutputStream(bt);

                                    try
                                    {
                                        out.writeInt(3);
                                        out.writeInt(timer);
                                        Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                                        Player player = (Player)e;
                                        PacketDispatcher.sendPacketToPlayer(packet, player);
                                    }
                                    catch (IOException ex)
                                    {
                                        System.out.println("couldnt send packet!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

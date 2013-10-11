package draco18s.decay;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import draco18s.decay.blocks.decays.ChaosDecay;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EventHandlers
{
    public EventHandlers()
    {
    }

    @ForgeSubscribe
    public void EntityDamaged(LivingHurtEvent event)
    {
        EntityLiving ent = event.entityLiving;
        NBTTagCompound nbt = ent.getEntityData();
        int hpo = nbt.getInteger("HealthOverflow");
        int hp = ent.getHealth();
        int mhp = ent.getMaxHealth();
        int newhp = hp;
        int newhpo = hpo;

        if (newhpo > mhp * 2)
        {
        }
        else if (hp < mhp)
        {
            newhp = Math.min(hp + hpo, mhp);
            newhpo = Math.max(hp + hpo - mhp, 0);
            int hrt = ent.hurtResistantTime;
            ent.heal(newhp - hp);
            if(ent.isDead && (newhp - hp) > 0)
            	ent.isDead = false;
            ent.hurtResistantTime = hrt;
        }

        nbt.setInteger("HealthOverflow", newhpo);
        nbt.setBoolean("TookDamageFlag", true);

        if (ent instanceof EntityPlayer)
        {
            ByteArrayOutputStream bt = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bt);

            try
            {
                out.writeInt(2);
                out.writeBoolean(true);
                Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                Player player = (Player)ent;
                PacketDispatcher.sendPacketToPlayer(packet, player);
            }
            catch (IOException ex)
            {
                System.out.println("couldnt send packet!");
            }
        }
    }

    @ForgeSubscribe
    public void EntityUpdate(LivingEvent event){
    	EntityLiving ent = event.entityLiving;
        NBTTagCompound nbt = ent.getEntityData();
        int hpo = nbt.getInteger("HealthOverflow");
        boolean flag = nbt.getBoolean("TempDecay");
        if(!flag && hpo > 0) {
            int timer = nbt.getInteger("HealthOverflowTimer");
            timer--;
            if (timer < -180)
            {
                hpo--;
                timer = 0;
                if (ent instanceof EntityPlayer)
                {
                    ByteArrayOutputStream bt = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bt);
                    try
                    {
                        out.writeInt(1);
                        out.writeInt(hpo);
                        Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                        Player player = (Player)ent;
                        PacketDispatcher.sendPacketToPlayer(packet, player);
                    }
                    catch (IOException ex)
                    {
                        System.out.println("couldnt send packet!");
                    }
                }
            }
            nbt.setInteger("HealthOverflow", hpo);
            nbt.setInteger("HealthOverflowTimer", timer);
        }
        flag = false;
        nbt.setBoolean("TempDecay", flag);
        if(!ent.worldObj.canBlockSeeTheSky(MathHelper.floor_double(ent.posX), MathHelper.floor_double(ent.posY), MathHelper.floor_double(ent.posZ))) {
            int tempTimer = nbt.getInteger("TemperatureTimer");
            tempTimer--;
            nbt.setInteger("TemperatureTimer", tempTimer);
            if (ent instanceof EntityPlayer)
            {
                ByteArrayOutputStream bt = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bt);

                try
                {
                    out.writeInt(3);
                    out.writeInt(tempTimer);
                    Packet250CustomPayload packet = new Packet250CustomPayload("MoreDecay", bt.toByteArray());
                    Player player = (Player)ent;
                    PacketDispatcher.sendPacketToPlayer(packet, player);
                }
                catch (IOException ex)
                {
                    System.out.println("couldnt send packet!");
                }
            }
        }
    }

    /*@ForgeSubscribe
    public void biomevent(LivingEvent event){
    	//if biome name equals...
    	//forest, wood, jungle, taiga, grove, lush, timber
    	// -> treant
    	
    	//hill, land, grass, field
    	// -> blink dog
    	
    	//desert, waste, sands, dead, inferno, fire, hot, volcano, magma, lava
    	// -> empyreal
    }*/
    
    @ForgeSubscribe
    public void textureEvent(TextureStitchEvent.Post event) {
    	((ChaosDecay)(DecayingWorld.rawChaos)).setupIDs();
    }
}

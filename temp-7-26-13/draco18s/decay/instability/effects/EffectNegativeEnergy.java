package draco18s.decay.instability.effects;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
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
import draco18s.decay.DecayingWorld;
import draco18s.decay.NegativeDamage;
import draco18s.decay.PositiveDamage;
import draco18s.decay.entities.EntitySolidifier;
import draco18s.decay.entities.EntityTreant;
import draco18s.decay.world.WorldGenCrystalFormation;

public class EffectNegativeEnergy implements IEnvironmentalEffect
{
    int level;
    WorldGenCrystalFormation worldGen;

    public EffectNegativeEnergy(int var1)
    {
        level = var1;
        worldGen = new WorldGenCrystalFormation(DecayingWorld.deathCrystal.blockID);
    }

    @Override
    public void onChunkPopulate(World worldObj, Random rand, int i, int j)
    {
        int chunkX = i / 16;
        int chunkZ = j / 16;
        int ch = (chunkX * chunkX + chunkZ * chunkZ + worldObj.provider.dimensionId + (int)Math.pow(1 + chunkX * chunkZ, 3)) % (57 + chunkZ % 5);

        if (ch == 0)
        {
            int x = rand.nextInt(16) + i;
            int z = rand.nextInt(16) + j;
            //System.out.println("Adding death crystal formation (" + x + "," + z + ")");
            worldGen.generate(worldObj, rand, x, 0, z);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
        //int s = worldObj.calculateSkylightSubtracted(0);
        if (!worldObj.isRemote && chunk.hasEntities)
        {
            //if (worldObj.rand.nextInt(10) < level)
            //{
            for (int var3 = chunk.entityLists.length - 1; var3 >= 0; var3--)
            {
                //int var3 = worldObj.rand.nextInt(chunk.entityLists.length);
                List var4 = chunk.entityLists[var3];

                if (var4.size() > 0)
                {
                    for (int entind = var4.size() - 1; entind >= 0; entind--)
                    {
                        Entity e = (Entity)var4.get(entind);
                        if(e instanceof EntityLiving) {
                        	EntityLiving ent = (EntityLiving)e;
	                        if (DecayingWorld.evilmobs(ent))
	                        {
	                            NBTTagCompound nbt = e.getEntityData();
	                        	int hpo = nbt.getInteger("HealthOverflow");
	                            int timer = Math.max(nbt.getInteger("ExpDrainTimer"), 0);
	                            timer++;

	                            if (timer > 180)
	                            {
	                                hpo++;
	                                timer = new Random().nextInt(60);
	                            }

	                            int hp = ((EntityLiving)e).getHealth();
	                            int mhp = ((EntityLiving)e).getMaxHealth();
	                            int newhp = hp;
	                            int newhpo = hpo;

	                            if (hp < mhp)
	                            {
	                                newhp = Math.min(hp + hpo, mhp);
	                                newhpo = Math.max(hp + hpo - mhp, 0);
	                                ((EntityLiving)e).heal(newhp - hp);
	                            }

	                            nbt.setInteger("HealthOverflow", newhpo);
	                            nbt.setInteger("ExpDrainTimer", timer);
	                        }

	                        else if (e instanceof EntityPlayer)
	                        {
	                        	EntityPlayer player = (EntityPlayer)e;
	                            NBTTagCompound nbt = player.getEntityData();
	                            int timer = Math.max(nbt.getInteger("ExpDrainTimer"), 0) + level;
	                            if (timer > 180 - player.experienceLevel*5) {
	                            	timer = new Random().nextInt(60);
	                            	//System.out.println("Total exp: " + player.experienceTotal);
	                            	if(player.experience > 0 && player.experienceLevel > 0) {
	                                	player.experienceTotal--;
	                            		player.experience -= 1F/player.xpBarCap();
	                            	}
	                            	else if(player.experienceLevel > 0) {
	                                	player.experienceTotal--;
	                            		player.experienceLevel--;
	                            		player.experience = (float)(player.xpBarCap()-1)/player.xpBarCap();
	                            		if(player.experienceLevel == 0)
	                            			player.experience = 0;
	                            	}
	                            	else {
	                            		player.attackEntityFrom(NegativeDamage.type, 1);
	                            	}
	                            }
	                            nbt.setInteger("ExpDrainTimer", timer);
	                        }
                        }
                    }
                }
            }
        }
    }
}

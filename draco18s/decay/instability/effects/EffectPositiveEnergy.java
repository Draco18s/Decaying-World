package draco18s.decay.instability.effects;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.MystObjects;
import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.*;
import draco18s.decay.entities.*;
import draco18s.decay.world.WorldGenCrystalFormation;

public class EffectPositiveEnergy implements IEnvironmentalEffect
{
	int level;
	WorldGenCrystalFormation worldGen;

	public EffectPositiveEnergy(int var1)
	{
		level = var1;
		worldGen = new WorldGenCrystalFormation(DecayingWorld.healCrystal.blockID);
	}

	@Override
	public void onChunkPopulate(World worldObj, Random rand, int i, int j)
	{
		if (!worldObj.isRemote) {
			int chunkX = i / 16;
			int chunkZ = j / 16;
			int ch = (chunkX * chunkX + chunkZ * chunkZ + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (57 + chunkZ % 5);
			BiomeGenBase b = worldObj.getBiomeGenForCoords(i, j);
			//int bid = b.biomeID;
			if (ch == 0)
			{
				int x = rand.nextInt(16) + i;
				int z = rand.nextInt(16) + j;
				//System.out.println("Adding healing crystal formation (" + x + "," + z + ")");
				worldGen.generate(worldObj, rand, x, 0, z);
			}

			//if(b.biomeID > 22) {
				ch = (chunkX * chunkX + chunkZ * chunkZ + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (37 + chunkZ % 5);
				//System.out.println("Positive Energy Populate");
				if(b.rainfall < 0.35 || b.temperature >= 0.8) {
					ch++;
				}
				if(ch == 0) {
					int x = rand.nextInt(16) + i;
					int z = rand.nextInt(16) + j;
					int y = getFirstUncoveredBlock(worldObj, x, z);
					if(i > 0) {
						//System.out.println("Placed treant at (" + x + "," + y + "," + z + ")");
						EntityTreant entitychicken = new EntityTreant(worldObj);
						entitychicken.setLocationAndAngles((double)x, (double)y, (double)z, 0.0F, 0.0F);
						worldObj.spawnEntityInWorld(entitychicken);
					}
				}
				EntityPlayer p = worldObj.getClosestPlayer(i, 64, j, 64);
				if(p!=null) {
					ch = (chunkX * chunkX + chunkZ * chunkZ + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (17 + chunkX % 5);
					if(ch == 0) {
						int n = 1 + rand.nextInt(3);
						for(; n >= 0; n--) {
							int x = rand.nextInt(16) + i;
							int z = rand.nextInt(16) + j;
							int y = getFirstUncoveredBlock(worldObj, x, z);
							if(y > 0) {
								//System.out.println("Placed blink dog at (" + x + "," + y + "," + z + ")");
								EntityBlinkDog entitychicken = new EntityBlinkDog(worldObj);
								entitychicken.setLocationAndAngles((double)x, (double)y, (double)z, 0.0F, 0.0F);
								worldObj.spawnEntityInWorld(entitychicken);
							}
						}
					}
					ch = (chunkX * (chunkX+chunkZ) + chunkZ * (chunkZ+chunkX) + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (17 + chunkX % 5);
					if(b.getFloatTemperature() < 0.5) {
						ch++;
					}
					else if(b.getFloatTemperature() > 1.5) {
						ch--;
					}
					if(ch == 0) {
						int x = rand.nextInt(16) + i;
						int z = rand.nextInt(16) + j;
						int y = getFirstAirBlock(worldObj, x, z);
						if(y > 0) {
							//System.out.println("Placed blink dog at (" + x + "," + y + "," + z + ")");
							EntityEmpyreal entitychicken = new EntityEmpyreal(worldObj);
							entitychicken.setLocationAndAngles((double)x, (double)y, (double)z, 0.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
						}
					}
				}
			//}

			for(int vary=130;vary > 0; vary--) {
				int bID = worldObj.getBlockId(chunkX*16+8, vary, chunkZ*16+4);
				int tex = chunkX*16+8;
				int tez = chunkZ*16+4;
				if(MystObjects.book_lectern != null && bID == MystObjects.book_lectern.blockID) {
					//System.out.println("Spawning foo dogs at (" + tex + "," + vary + "," + tez + ")");
					EntityFooDog entitychicken;
					int l = worldObj.getBlockId(tex+1, vary, tez);
					int r = worldObj.getBlockId(tex-1, vary, tez);
					if(l == r) {
						l = worldObj.getBlockId(tex, vary, tez+1);
						r = worldObj.getBlockId(tex, vary, tez-1);
						if(r == Block.cobblestone.blockID) {
							//te.xCoord+2, vary, tez+6
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex+2, vary, tez+6, 0.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex-2, vary, tez+6, 0.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
						}
						else {
							//te.xCoord+2, vary, tez-6
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex+2, vary, tez-6, 180.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex-2, vary, tez-6, 180.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
						}
					}
					else {
						if(r == Block.cobblestone.blockID) {
							//te.xCoord-6, vary, tez+2
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex+6, vary, tez-2, 90.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex+6, vary, tez+2, 90.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
						}
						else {
							//te.xCoord+6, vary, tez+2
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex-6, vary, tez-2, 270.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
							entitychicken = new EntityFooDog(worldObj);
							entitychicken.setLocationAndAngles(tex-6, vary, tez+2, 270.0F, 0.0F);
							worldObj.spawnEntityInWorld(entitychicken);
						}
					}
				}
			}
		}
	}

	@Override
	public void tick(World worldObj, Chunk chunk)
	{
		//int s = worldObj.calculateSkylightSubtracted(0);
		if (!worldObj.isRemote && chunk.hasEntities)
		{
			int foundblinkdog = 0;
			boolean foundempyreal = false;
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

						if (e instanceof EntityLiving)
						{
							EntityLiving var5 = (EntityLiving)e;
							NBTTagCompound nbt = var5.getEntityData();
							float hpo = nbt.getFloat("HealthOverflow");
							int timer = nbt.getInteger("HealthOverflowTimer");
							if (timer < 0) {
								timer = 0;
							}
							timer+=level;
							if(DecayingWorld.goodmobs(var5)) {
								timer+=(5-level);
							}
							if(var5 instanceof EntityBlinkDog) {
								foundblinkdog += 1;
							}
							if(var5 instanceof EntityEmpyreal) {
								foundempyreal = true;
							}
							//System.out.println("Timer:  " +timer);
							//System.out.println("HPO:    " +hpo);
							float hp = var5.getHealth();
							if (timer > 180)
							{
								if(DecayingWorld.evilmobs(var5)) {
									if(hpo > 0)
										hpo--;
									else
										hp--;
								}
								else {
									hpo++;
									int r = worldObj.rand.nextInt(60);
									nbt.setInteger("HealthOverflowTimer", r);
									timer = r;
								}
							}

							float mhp = var5.getMaxHealth();
							float newhp = hp;
							float newhpo = hpo;

							if (hp < mhp && !DecayingWorld.evilmobs(var5))
							{
								newhp = Math.min(hp + hpo, mhp);
								newhpo = Math.max(hp + hpo - mhp, 0);
								var5.heal(newhp - hp);
							}
							//System.out.println("newHPO: " +newhpo);

							if (newhpo > mhp * 2)
							{
								newhpo = mhp * 2;
								if (!(DecayingWorld.goodmobs(var5) || var5 instanceof EntityCreeper))
									//if (!(var5 instanceof EntitySkeleton || var5 instanceof EntityZombie || var5 instanceof EntityCreeper || e instanceof EntityGhast || e instanceof EntityPigZombie))
								{
									worldObj.newExplosion(var5, var5.posX, var5.posY, var5.posZ, 0F, false, true);
									var5.attackEntityFrom(PositiveDamage.type, mhp*3);
								}
							}

							nbt.setFloat("HealthOverflow", newhpo);
							nbt.setInteger("HealthOverflowTimer", timer);
							nbt.setBoolean("TempDecay", true);

							if (e instanceof EntityPlayer)
							{
								ByteArrayOutputStream bt = new ByteArrayOutputStream();
								DataOutputStream out = new DataOutputStream(bt);
								try
								{
									out.writeInt(1);
									out.writeFloat(newhpo);
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
			int chunkX = chunk.xPosition;
			int chunkZ = chunk.zPosition;
			BiomeGenBase b = worldObj.getBiomeGenForCoords(chunkX*16, chunkZ*16);
			//if(b.biomeID > 22) {
				int x, y, z, n, ch;
				EntityPlayer p = worldObj.getClosestPlayer(chunkX*16, 96, chunkZ*16, 96);
				EntityPlayer p2 = worldObj.getClosestPlayer(chunkX*16, 24, chunkZ*16, 24);
				if(p!=null && p2==null) {
					if(foundblinkdog < 2) {
						ch = (chunkX * chunkX + chunkZ * chunkZ + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (17 + chunkX % 5);
						if(ch == 0 && worldObj.rand.nextInt(120) == 0) {
							AxisAlignedBB par2AxisAlignedBB = AxisAlignedBB.getBoundingBox((double)(chunkX*16)-32, (double)0, (double)(chunkZ*16)-32,(double)(chunkX*16)+32, (double)96, (double)(chunkZ*16)+32);
	                		//par2AxisAlignedBB.expand(16, 16, 16);
	                		List<Entity> ents = worldObj.getEntitiesWithinAABB(EntityBlinkDog.class, par2AxisAlignedBB);
	                		if(ents.size() < 4) {
								n = 1 + worldObj.rand.nextInt(2);
								for(; n >= 0; n--) {
									x = worldObj.rand.nextInt(16) + chunkX*16;
									z = worldObj.rand.nextInt(16) + chunkZ*16;
									y = getFirstUncoveredBlock(worldObj, x, z);
									if(y > 0) {
										//System.out.println("Placed blink dog at (" + x + "," + y + "," + z + ")");
										EntityBlinkDog entitychicken = new EntityBlinkDog(worldObj);
										entitychicken.setLocationAndAngles((double)x, (double)y, (double)z, 0.0F, 0.0F);
										worldObj.spawnEntityInWorld(entitychicken);
									}
								}
							}
						}
					}
					if(!foundempyreal) {
						ch = (chunkX * (chunkX+chunkZ) + chunkZ * (chunkZ+chunkX) + worldObj.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (17 + chunkX % 5);
						if(ch == 0 && worldObj.rand.nextInt(80) == 0) {
							x = worldObj.rand.nextInt(16) + chunkX*16;
							z = worldObj.rand.nextInt(16) + chunkZ*16;
							y = getFirstAirBlock(worldObj, x, z);
							if(y > 0) {
								//System.out.println("Placed blink dog at (" + x + "," + y + "," + z + ")");
								EntityEmpyreal entitychicken = new EntityEmpyreal(worldObj);
								entitychicken.setLocationAndAngles((double)x, (double)y, (double)z, 0.0F, 0.0F);
								worldObj.spawnEntityInWorld(entitychicken);
							}
						}
					}
				}
			//}
		}
	}

	private int getFirstUncoveredBlock(World world, int x, int z)
	{
		int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

		if (h > 1)
		{
			return h;
		}
		else
		{
			int var3;
			int max = 130;
			boolean solid = false;
			int ah = world.provider.getAverageGroundLevel() - 3;
			for (var3 = 0; var3 < max && !world.canBlockSeeTheSky(x, var3, z); ++var3)
			{
				if(!solid && world.getBlockId(x, var3, z) != 0) {
					solid = true;
				}
				if ((var3 > 8 && var3 < ah) && solid)
				{
					var3 = ah;

					if (var3 < 8)
					{
						var3 = 8;
					}
				}
			}

			if (var3 >= max || !solid)
			{
				return -1;
			}

			return var3;
		}
	}

	private int getFirstAirBlock(World world, int x, int z)
	{
		int var3;
		int max = 130;
		for (var3 = 0; var3 < max; var3++)
		{
			if(world.getBlockId(x, var3, z) == 0 && world.getBlockId(x, var3+1, z) == 0) {
				break;
			}
		}

		if (var3 >= max)
		{
			return -1;
		}

		return var3;
	}
}

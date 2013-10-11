package draco18s.decay.blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import draco18s.decay.ColdDamage;
import draco18s.decay.DecayingWorld;
import draco18s.decay.PositiveDamage;
import draco18s.decay.entities.EntityBlinkDog;
import draco18s.decay.entities.EntityTreant;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HealCrystal extends Block
{
    public HealCrystal(int par1)
    {
        super(par1, Material.rock);
        setHardness(2F);
        setUnlocalizedName("Healing Crystal");
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(2.0F);
        setResistance(5.0F);
        setTickRandomly(true);
        //this.setBlockBounds(0.01F, 0.01F, 0.01F, 0.99F, 0.99F, 0.99F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float f = 0.01F;
        return AxisAlignedBB.getAABBPool().getAABB((double)(par2 + f), (double)(par3 + f), (double)(par4 + f), (double)((float)(par2 + 1)-f), (double)((float)(par3 + 1) - f), (double)((float)(par4 + 1)-f));
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:healcrystal");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return DecayingWorld.healShard.itemID;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	int wid, meta,s;
    	for(int i=-2;i<=2;i++) {
    		for(int k=-2;k<=2;k++) {
				s = i*i + k*k;
				if(rand.nextInt(8) >= s && rand.nextDouble() < 0.1) {
	    			wid = world.getBlockId(x+i, y, z+k);
					if(wid == 0) {
						if(world.getBlockId(x+i, y-1, z+k) == Block.grass.blockID) {
        					initialRandomBlock(world, x+i, y, z+k, rand);
    					}
						else if(world.getBlockId(x+i, y-1, z+k) == Block.mycelium.blockID) {
							world.setBlock(x+i, y, z+k, (rand.nextBoolean()) ? Block.mushroomRed.blockID : Block.mushroomBrown.blockID, 0, 3);
						}
    				}
					else if(wid == Block.tallGrass.blockID && world.getBlockMetadata(x+i, y, z+k) == 0) {
						secondaryRandomBlock(world, x+i, y, z+k, rand);
					}
					else if(wid == Block.tallGrass.blockID && world.getBlockMetadata(x+i, y, z+k) == 1) {
						world.setBlock(x+i, y-1, z+k, Block.tilledField.blockID, 7, 0);
						world.setBlock(x+i, y, z+k, Block.crops.blockID, 3 + rand.nextInt(3), 3);
					}
					else if(wid == Block.plantYellow.blockID) {
						world.setBlock(x+i, y, z+k, Block.mushroomBrown.blockID, 0, 3);
					}
					else if(wid == Block.plantRed.blockID) {
						world.setBlock(x+i, y, z+k, Block.mushroomRed.blockID, 0, 3);
					}
					if(wid == Block.cobblestone.blockID) {
						world.setBlock(x+i, y, z+k, Block.cobblestoneMossy.blockID, 0, 3);
					}
					if(world.getBlockId(x+i, y-1, z+k) == Block.cobblestone.blockID) {
						world.setBlock(x+i, y-1, z+k, Block.cobblestoneMossy.blockID, 0, 3);
					}
					if(wid == Block.dirt.blockID) {
						world.setBlock(x+i, y, z+k, (rand.nextDouble() < 0.05) ? Block.mycelium.blockID : Block.grass.blockID, 0, 3);
					}
					if(world.getBlockId(x+i, y-1, z+k) == Block.dirt.blockID) {
						world.setBlock(x+i, y-1, z+k, (rand.nextDouble() < 0.05) ? Block.mycelium.blockID : Block.grass.blockID, 0, 3);
					}
    			}
    		}
    	}
    }

    @Override
    public void onEntityCollidedWithBlock(World worldObj, int par2, int par3, int par4, Entity e)
    {
        if (!worldObj.isRemote)
        {
            if (e instanceof EntityLiving)
            {
                EntityLiving var5 = (EntityLiving)e;
                NBTTagCompound nbt = var5.getEntityData();
                int hpo = nbt.getInteger("HealthOverflow");
                int timer = nbt.getInteger("PosEnergyTimer") + 2;

                int hp = var5.getHealth();

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

                int mhp = var5.getMaxHealth();
                int newhp = hp;
                int newhpo = hpo;

                if (hp < mhp && !DecayingWorld.evilmobs(var5))
                {
                    newhp = Math.min(hp + hpo, mhp);
                    newhpo = Math.max(hp + hpo - mhp, 0);
                    var5.heal(newhp - hp);
                }

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

                nbt.setInteger("HealthOverflow", newhpo);
                nbt.setInteger("PosEnergyTimer", timer);
                nbt.setBoolean("TempDecay", true);

                if (e instanceof EntityPlayer)
                {
                    ByteArrayOutputStream bt = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bt);

                    try
                    {
                        out.writeInt(1);
                        out.writeInt(newhpo);
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

    private void initialRandomBlock(World world, int x, int y, int z, Random rand) {
    	int r = rand.nextInt(2);
    	int id = 0;
    	switch(r) {
	    	case 0:
	    		id = Block.tallGrass.blockID;
	    		break;
	    	case 1:
	    		id = (rand.nextBoolean()) ? Block.plantYellow.blockID : Block.plantRed.blockID;
	    		break;
    	}
		world.setBlock(x, y, z, id, 0, 3);
    }

    private void secondaryRandomBlock(World world, int x, int y, int z, Random rand) {
    	int r = rand.nextInt(3);
    	int id = 0;
    	int meta = 0;
    	switch(r) {
	    	case 0:
	    		id = Block.sapling.blockID;
	    		break;
	    	case 1:
	    		id = Block.tallGrass.blockID;
	    		meta = 1;
	    		break;
	    	case 2:
	    		id = Block.tallGrass.blockID;
	    		meta = 2;
	    		break;
    	}
		world.setBlock(x, y, z, id, meta, 3);
    }
}

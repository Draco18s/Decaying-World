package draco18s.decay.blocks.decays;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import draco18s.decay.ColdDamage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ChaosDecay extends BaseFogDecay
{
	private List<Short> validIDs = new ArrayList<Short>();
	private List<Short> validPIDs = new ArrayList<Short>();
    public ChaosDecay(int par1)
    {
        super(par1);
        setHardness(-1F);
        setUnlocalizedName("Raw Chaos");
        setLightOpacity(1);
        disableStats();
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    public void setupIDs() {
    	for(int v=0; v < 5; v++){
			validIDs.add((short)1);
			validIDs.add((short)3);
    		validIDs.add((short)4);
    		validIDs.add((short)7);
    		validIDs.add((short)12);
    		validIDs.add((short)13);
    		validIDs.add((short)97);
		}
		for(int v=0; v < 10; v++){
    		validIDs.add((short)1);
			validIDs.add((short)3);
    		validIDs.add((short)4);
    		validIDs.add((short)17);
    		validIDs.add((short)18);
    		validIDs.add((short)24);
    		validIDs.add((short)35);
    		validIDs.add((short)43);
    		validIDs.add((short)45);
    		validIDs.add((short)48);
    		validIDs.add((short)49);
    		validIDs.add((short)72);
    		validIDs.add((short)79);
    		validIDs.add((short)80);
    		validIDs.add((short)86);
    		validIDs.add((short)98);
    		validIDs.add((short)110);
    		validIDs.add((short)153);
		}
		for(int b=Block.blocksList.length-1; b>= 0; b--) {
			if(Block.blocksList[b] != null) {
    			Icon ic = Block.blocksList[b].getIcon(0, 0);
    			if(ic != null)
    				validIDs.add((short) b);
			}
		}
		for(int v=0; v < 10; v++){
			validIDs.add((short)2);
			validIDs.add((short)3);
    		validIDs.add((short)4);
    		validIDs.add((short)5);
    		validIDs.add((short)7);
    		validIDs.add((short)12);
    		validIDs.add((short)13);
    		validIDs.add((short)30);
    		validIDs.add((short)82);
    		validIDs.add((short)97);
		}
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("DecayingWorld:chaos");
    }

    public void spread(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, 1, 3);

                /*if (world.getBlockId(x, y - 1, z) == 0)
                {
                    world.setBlock(x, y, z, 0, 0, 3);
                    world.setBlock(x, y - 1, z, blockID, 0, 3);
                    world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);

                    if (world.getBlockId(x, y + 1, z) == blockID)
                    {
                        world.setBlock(x, y + 1, z, blockID, 0, 3);
                        world.scheduleBlockUpdate(x, y + 1, z, blockID, 2);
                    }
                }*/

                //else
                //world.scheduleBlockUpdate(x, y, z, blockID, 300+random.nextInt(300));
            }
            else
            {
                int[] wID = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y + 1, z), world.getBlockId(x, y - 1, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};

                if (wID[0] != blockID)
                {
                    world.setBlock(x + 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x + 1, y, z, blockID, 1);
                }

                if (wID[1] != blockID)
                {
                    world.setBlock(x - 1, y, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x - 1, y, z, blockID, 1);
                }

                if (wID[2] != blockID)
                {
                    world.setBlock(x, y + 1, z, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                }

                if (wID[3] == blockID && (world.getBlockId(x, y - 2, z) == blockID))
                {
                    world.setBlock(x, y, z, 0, 0, 3);

                    if (wID[2] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y + 1, z, 0, 3);
                        world.scheduleBlockUpdate(x, y, z, blockID, 2);
                    }
                }

                if (wID[4] != blockID)
                {
                    world.setBlock(x, y, z + 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x, y, z + 1, blockID, 1);
                }

                if (wID[5] != blockID)
                {
                    world.setBlock(x, y, z - 1, random.nextDouble() > 0.3 ? 0 : blockID, 0, 0);
                    world.scheduleBlockUpdate(x, y, z - 1, blockID, 1);
                }

                if (wID[3] != blockID)
                {
                    world.setBlock(x, y, z, 0, 0, 3);
                    world.setBlock(x, y - 1, z, blockID, 0, 3);
                    world.scheduleBlockUpdate(x, y - 1, z, blockID, 2);

                    if (wID[0] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x + 1, y, z, 0, 3);
                    }

                    if (wID[1] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x - 1, y, z, 0, 3);
                    }

                    if (wID[2] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y + 1, z, 0, 3);
                    }

                    if (wID[4] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y, z + 1, 0, 3);
                    }

                    if (wID[0] == blockID)
                    {
                        world.setBlockMetadataWithNotify(x, y, z - 1, 0, 3);
                    }
                }
            }
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	spread(world, x, y, z, random);
    	//super.updateTick(world, x, y, z, random);
    	int theID = blockID;
    	if(random.nextDouble() < 0.05) {
    		int c = 0;
    		boolean valid = false;
    		do {
    			valid = false;
    			theID = random.nextInt(validIDs.size());
    			theID = validIDs.get(theID);
    			c++;
    			valid = Block.blocksList[theID].canBlockStay(world, x, 255, z) && Block.blocksList[theID].canBlockStay(world, x, y, z);
    		} while(valid && c < 10);
    		if(valid) {
    			System.out.println("ID: " + theID);
    			world.setBlock(x, y, z, theID);
    			//world.setBlock(x, y-1, z, blockID);
    		}
    	}
    	/*else if(random.nextDouble() < 0.25) {
    		world.setBlock(x, y-1, z, blockID);
    	}*/
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block) { }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote && par5Entity instanceof EntityPlayer)
        {
        	if(validPIDs.size() == 0) {
	    		for(int b=Potion.potionTypes.length-1; b>= 0; b--) {
	    			if(Potion.potionTypes[b] != null) {
		    			validPIDs.add((short) b);
	    			}
	    		}
	    	}
        	Random random = par1World.rand;
        	int pid;
        	if(random.nextDouble() < 0.0025) {
        		pid = random.nextInt(validPIDs.size());
        		pid = validPIDs.get(pid);
        		Potion p = Potion.potionTypes[pid];
        		if(par5Entity instanceof EntityLiving) {
        			EntityLiving el = (EntityLiving)par5Entity;
        			if((pid == 6 || pid == 7) && random.nextDouble() < 0.25)
        				el.addPotionEffect(new PotionEffect(pid, 1, 0));
        			else if(pid != 6 && pid != 7)
        				el.addPotionEffect(new PotionEffect(pid, 100, 0));
        		}
        	}
            //((EntityPlayer)par5Entity).addExhaustion(0.002F);
        }
    }
}

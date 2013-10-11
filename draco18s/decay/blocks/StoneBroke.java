package draco18s.decay.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import draco18s.decay.CommonProxy;
import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class StoneBroke extends Block
{
    public StoneBroke(int par1)
    {
        super(par1, Material.rock);
        setTickRandomly(true);
        setHardness(1.0F);
        setResistance(2.5F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Stone (Broken)");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("decayingworld:brokenstone");
    }
    
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
    	return false;
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
    	boolean falling = false;
    	if(world.isBlockSolidOnSide(x, y-1, z, ForgeDirection.DOWN)) {
    		return;
    	}
        int t = (world.isBlockSolidOnSide(x+1, y-1, z, ForgeDirection.DOWN)?1:0);
        t += (world.isBlockSolidOnSide(x-1, y-1, z, ForgeDirection.DOWN)?1:0);
        t += (world.isBlockSolidOnSide(x, y-1, z+1, ForgeDirection.DOWN)?1:0);
        t += (world.isBlockSolidOnSide(x, y-1, z-1, ForgeDirection.DOWN)?1:0);
        if(t < 2) {
            t += (world.isBlockSolidOnSide(x+1, y-1, z, ForgeDirection.DOWN)?1:0);
            t += (world.isBlockSolidOnSide(x-1, y-1, z, ForgeDirection.DOWN)?1:0);
            t += (world.isBlockSolidOnSide(x, y-1, z+1, ForgeDirection.DOWN)?1:0);
            t += (world.isBlockSolidOnSide(x, y-1, z-1, ForgeDirection.DOWN)?1:0);
            if(t < 2) {
            	falling = true;
            }
        }
        if(falling) {
        	world.setBlock(x, y, z, DecayingWorld.stoneCobble.blockID);
        	DecayingWorld.damageNeighbors(world, x, y, z);
        }
    }
    
    public int idDropped(int par1, Random par2Random, int par3)
    {
    	return Block.cobblestone.blockID;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, int ID)
    {
        world.scheduleBlockUpdate(x, y, z, this.blockID, 6 + new Random().nextInt(6));
    }
    
    public void onBlockAdded(World world, int x, int y, int z) {
    	 world.scheduleBlockUpdate(x, y, z, this.blockID, 6 + new Random().nextInt(6));
    }
}

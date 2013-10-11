package draco18s.decay.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import draco18s.decay.CommonProxy;
import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class StoneFrac extends Block
{
    public StoneFrac(int par1)
    {
        super(par1, Material.rock);
        setTickRandomly(true);
        setHardness(1.25F);
        setResistance(7.5F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Stone (Fractured)");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("decayingworld:fracturedstone");
    }

    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
    	boolean falling = false;
    	int s = 0;
    	if(world.isBlockSolidOnSide(x, y-1, z, ForgeDirection.DOWN)) {
    		return;
    	}
    	s = world.getBlockId(x+1, y, z);
        boolean px = (s == DecayingWorld.stoneUnst.blockID || s == Block.stone.blockID);
        s = world.getBlockId(x-1, y, z);
        boolean nx = (s == DecayingWorld.stoneUnst.blockID || s == Block.stone.blockID);
        s = world.getBlockId(x, y, z+1);
        boolean pz = (s == DecayingWorld.stoneUnst.blockID || s == Block.stone.blockID);
        s = world.getBlockId(x, y, z-1);
        boolean nz = (s == DecayingWorld.stoneUnst.blockID || s == Block.stone.blockID);
        s = world.getBlockId(x, y+1, z);
        boolean py = (s == DecayingWorld.stoneUnst.blockID || s == Block.stone.blockID);
        if(!(px || nx || pz || nz || py)) {
        	falling = true;
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
    
    public void onBlockAdded(World world, int x, int y, int z) {
    	 world.scheduleBlockUpdate(x, y, z, this.blockID, 6 + new Random().nextInt(6));
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, int ID)
    {
        world.scheduleBlockUpdate(x, y, z, this.blockID, 6 + new Random().nextInt(6));
    }
}

package draco18s.decay.blocks.decays;

import java.util.Random;

import draco18s.decay.DecayingWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class UnstableDecay extends Block
{
	Icon blockIcons[];
    public UnstableDecay(int par1, Material par3Material)
    {
        super(par1, par3Material);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("Unstable Decay");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = Block.stone.getIcon(0, 0);
    }

    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	int i,j,k,s;
    	for(int l = 0; l < 8; l++) {
	    	i = rand.nextInt(33) - 16;
	    	j = rand.nextInt(33) - 16;
	    	k = rand.nextInt(33) - 16;
	    	s = world.getBlockId(x+i, y+j, z+k);
	    	if(s == Block.stone.blockID) {
	    		world.setBlock(x+i, y+j, z+k, blockID);
	    	}
	    	else if(s == Block.bedrock.blockID && (y+j > 0 || rand.nextInt(8) == 0)) {
	    		world.setBlock(x+i, y+j, z+k, blockID);
	    	}
    	}
    	if(rand.nextInt(50) == 0) {
	    	i = rand.nextInt(33) - 16;
	    	j = rand.nextInt(33) - 16;
	    	k = rand.nextInt(33) - 16;
    		DecayingWorld.drawLine3D(world, DecayingWorld.stoneFrac.blockID, x, y, z, x+i, y+j, z+k, true);
    		DecayingWorld.drawLine3D(world, DecayingWorld.stoneFrac.blockID, x, y, z, x+i, y+j-1, z+k, true);
    		DecayingWorld.drawLine3D(world, DecayingWorld.stoneFrac.blockID, x, y, z, x+i, y+j+1, z+k, true);
    	}
    	else
    		world.setBlock(x, y, z, DecayingWorld.stoneUnst.blockID);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}

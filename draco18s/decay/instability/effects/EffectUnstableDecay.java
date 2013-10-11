package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectUnstableDecay implements IEnvironmentalEffect
{
    private int stackedLevel;

    public EffectUnstableDecay(Integer level)
    {
        stackedLevel = level;
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int x, y, z, m = 0;
        for(int l = stackedLevel; l > 0; l--) {
	        do {
	            x = random.nextInt(16);
	            y = random.nextInt(50) + 5;
	            z = random.nextInt(16);
	            m++;
	        }
	        while (m < 50 && world.getBlockId(x, y, z) != Block.stone.blockID);
	
	        if (world.getBlockId(x, y, z) == Block.stone.blockID)
	        {
	            world.setBlock(wx + x, y, wz + z, DecayingWorld.unstableDecay.blockID);
	        }
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {

    }
}

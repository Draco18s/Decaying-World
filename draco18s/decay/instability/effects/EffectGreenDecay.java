package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectGreenDecay implements IEnvironmentalEffect
{
    private int stackedLevel;

    public EffectGreenDecay(Integer level)
    {
        stackedLevel = level;
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        if (random.nextDouble() < 0.025 * stackedLevel)
        {
            int chunkX = wx / 16;
            int chunkZ = wz / 16;
            int x, y, z;
            x = random.nextInt(16);
            z = random.nextInt(16);
            y = this.getFirstUncoveredBlock(world, wx + x, wz + z) - random.nextInt(3) - 1;

            if (y > 0)
            {
                world.setBlock(wx, y - 1, wz, DecayingWorld.greenDecay.blockID);
            }
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }

    private int getFirstUncoveredBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];
        //if(h > 0)
        return h;
        /*else {
        	int var3;
        	int max = 130;

            for (var3 = 0; var3 < max && !world.canBlockSeeTheSky(x, var3, z); ++var3)
            {
                if(var3 == 8) {
                	var3 = world.provider.getAverageGroundLevel() - 3;
                	if(var3 < 8) {
                		var3 = 8;
                	}
                }
            }
            if(var3 >= max)
            	return -1;
            return var3;
        }*/
    }
}

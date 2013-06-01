package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectFog implements IEnvironmentalEffect
{
    @Override
    public void onChunkPopulate(World world, Random rand, int wx, int wz)
    {
        int x = rand.nextInt(16);
        int z = rand.nextInt(16);
        int y = getFirstUncoveredBlock(world, wx + x, wz + z) + 1;
        world.setBlock(wx + x, y, wz + z, DecayingWorld.fogDecay.blockID);
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }

    private int getFirstUncoveredBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

        if (h > 0)
        {
            return h;
        }
        else
        {
            int var3;
            int max = 130;

            for (var3 = 0; var3 < max && !world.canBlockSeeTheSky(x, var3, z); ++var3)
            {
                if (var3 == 8)
                {
                    var3 = world.provider.getAverageGroundLevel() - 3;

                    if (var3 < 8)
                    {
                        var3 = 8;
                    }
                }
            }

            if (var3 >= max)
            {
                return -1;
            }

            return var3;
        }
    }
}

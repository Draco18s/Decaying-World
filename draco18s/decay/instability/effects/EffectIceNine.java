package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectIceNine implements IEnvironmentalEffect
{
    private int stackedLevel;

    public EffectIceNine(Integer level)
    {
        stackedLevel = level;
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        if (random.nextDouble() < 0.05 * stackedLevel)
        {
            int x, y, z;
            x = random.nextInt(16);
            z = random.nextInt(16);
            y = this.getFirstUncoveredBlock(world, wx + x, wz + z);

            if (y > -1)
            {
                world.setBlock(wx + x, y, wz + z, DecayingWorld.iceIX.blockID, 8, 3);
            }
        }
    }

    private int getFirstUncoveredBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

        if (h < 0)
        {
            int var3;
            int max = 130;

            for (var3 = 0; var3 < max && world.getBlockMaterial(x, var3, z) != Material.water; ++var3)
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

            h = var3;
        }

        if (world.getBlockMaterial(x, h, z) == Material.water)
        {
            return h;
        }
        else if (world.getBlockMaterial(x, h - 1, z) == Material.water)
        {
            return h - 1;
        }
        else if (world.getBlockMaterial(x, h + 1, z) == Material.water)
        {
            return h + 1;
        }

        return -1;
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectMethane implements IEnvironmentalEffect
{
    @Override
    public void onChunkPopulate(World world, Random rand, int wx, int wz)
    {
        int ch = (wx * wx + wz * wz + (int)Math.pow(wx * wz + world.provider.dimensionId, 3)) % (17 + wx % 5);

        if (ch == 0)
        {
            int x = rand.nextInt(16);
            int z = rand.nextInt(16);
            int y = getFirstAirBlock(world, wx + x, wz + z);
            do {
            	y++;
            } while(world.getBlockId(x, y, z) != 0);
            System.out.println("Methane emitter at (" + (wx + x) + "," + y + "," + (wz + z));
            world.setBlock(wx + x, y, wz + z, DecayingWorld.methanedecay.blockID);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }

    private int getFirstAirBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

        if (h < 0)
        {
            int var3;
            int max = 60;

            for (var3 = 0; var3 < max && world.getBlockId(x, var3, z) != 0; ++var3)
            {
                ;
            }

            if (var3 >= max)
            {
                return -1;
            }

            h = var3;
        }

        if (world.getBlockId(x, h, z) == 0)
        {
            return h;
        }

        return -1;
    }
}

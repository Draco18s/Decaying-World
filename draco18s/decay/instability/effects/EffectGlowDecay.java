package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectGlowDecay implements IEnvironmentalEffect
{
    public EffectGlowDecay()
    {
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int chunkX = wx / 16;
        int chunkZ = wz / 16;
        int ch = (chunkX * chunkX + chunkZ * chunkZ + world.provider.dimensionId + (int)Math.pow(chunkX * chunkZ + world.provider.dimensionId, 3)) % (119);

        if (ch == 0)
        {
            //System.out.println("Adding pillar");
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = this.getFirstUncoveredBlock(world, wx + x, wz + z) - random.nextInt(3) - 1;

            if (y > 0)
            {
                world.setBlock(wx + x, y, wz + z, DecayingWorld.glowDecay.blockID);
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
        return h;
    }
}

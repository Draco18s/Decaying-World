package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectPillars implements IEnvironmentalEffect
{
    public EffectPillars()
    {
        //System.out.println("Effect Pillars instanciated");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int chunkX = wx / 16;
        int chunkZ = wz / 16;
        int ch = (chunkX * chunkX + chunkZ * chunkZ + world.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (10 + chunkX % 5);

        if (ch == 0)
        {
            //System.out.println("Adding pillar");
            int x = random.nextInt(16);
            int y = random.nextInt(5) + 1;
            int z = random.nextInt(16);
            world.setBlock(wx + x, y, wz + z, DecayingWorld.pillarDecay.blockID);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

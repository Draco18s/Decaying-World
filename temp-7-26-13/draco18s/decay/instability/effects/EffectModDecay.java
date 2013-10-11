package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectModDecay implements IEnvironmentalEffect
{
    public EffectModDecay()
    {
    }

    @Override
    public void onChunkPopulate(World world, Random random, int chunkX, int chunkZ)
    {
        int ch = (chunkX * chunkX + chunkZ * chunkZ + world.provider.dimensionId + (int)Math.pow(chunkX * chunkZ, 3)) % (347);

        if (ch == 0)
        {
            //System.out.println("Adding specific decay");
            int x = random.nextInt(16);
            int y = random.nextInt(59) + 4;
            int z = random.nextInt(16);
            world.setBlock(chunkX + x, y, chunkZ + z, DecayingWorld.userDecay, DecayingWorld.userMeta, 3);
            world.scheduleBlockUpdate(chunkX + x, y, chunkZ + z, DecayingWorld.userDecay, 2);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

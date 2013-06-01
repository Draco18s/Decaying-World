package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectChaos implements IEnvironmentalEffect
{

    public EffectChaos() { }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int chunkX = wx / 16;
        int chunkZ = wz / 16;
        int ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ + world.provider.dimensionId, 3)) % (141 + chunkX % 5);
        ch = Math.abs(ch);
        BiomeGenBase b = world.getBiomeGenForCoords(wx, wz);
        if (ch <= 0)
        {
            int x = random.nextInt(16);
            int y = 255;
            int z = random.nextInt(16);
            world.setBlock(wx + x, y, wz + z, DecayingWorld.rawChaos.blockID, 0, 3);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

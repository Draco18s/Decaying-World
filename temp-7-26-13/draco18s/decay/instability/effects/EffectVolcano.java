package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectVolcano implements IEnvironmentalEffect
{
    private int stackedLevel;

    public EffectVolcano(Integer level)
    {
        stackedLevel = level;
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int chunkX = wx / 16;
        int chunkZ = wz / 16;
        int ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ + world.provider.dimensionId, 3)) % (41 + chunkX % 5);
        ch = Math.abs(ch);
        BiomeGenBase b = world.getBiomeGenForCoords(wx, wz);

        if (b.biomeName == "Desert")
        {
            ch -= 1;
        }

        ch -= (stackedLevel - 1);

        if (ch <= 0)
        {
            int x = random.nextInt(16);
            int y = 1;
            int z = random.nextInt(16);
            int delay = 152000 + random.nextInt(100000) - stackedLevel * 36000;
            world.setBlock(wx + x, y, wz + z, DecayingWorld.moltenDecay.blockID, 5, 3);
            world.scheduleBlockUpdate(wx + x, y, wz + z, DecayingWorld.moltenDecay.blockID, delay);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

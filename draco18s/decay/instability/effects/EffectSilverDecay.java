package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectSilverDecay implements IEnvironmentalEffect
{
    private int stackedLevel;

    public EffectSilverDecay(Integer level)
    {
        stackedLevel = level;
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int ch, x, y, z, m = 0;
        BiomeGenBase b = world.getBiomeGenForCoords(wx, wz);

        if (b.biomeName.equals("Extreme Hills") || b.biomeName.equals("Extreme Hills Edge") || b.biomeName.equals("MushroomIsland") || b.biomeName.equals("MushroomIslandShore"))
        {
            ch = stackedLevel * 2;
        }
        else
        {
            ch = stackedLevel / 2;
        }

        for (ch = random.nextInt(ch + 1); ch > 0; ch--)
        {
            do
            {
                x = random.nextInt(16);
                y = random.nextInt(50) + 5;
                z = random.nextInt(16);
                m++;
            }
            while (m < 1000 + (stackedLevel * 100) && world.getBlockId(x, y, z) != Block.stone.blockID);

            if (world.getBlockId(x, y, z) == Block.stone.blockID)
            {
                world.setBlock(wx + x, y, wz + z, DecayingWorld.silverDecay.blockID);
            }
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
        // TODO Auto-generated method stub
    }
}

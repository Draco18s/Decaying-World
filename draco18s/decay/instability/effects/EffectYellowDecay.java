package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectYellowDecay implements IEnvironmentalEffect
{
    private Random random;
    private int stackedLevel;

    public EffectYellowDecay(Integer level)
    {
        stackedLevel = level;
        random = new Random();
    }

    @Override
    public void onChunkPopulate(World worldObj, Random rand, int i, int j)
    {
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
        if (random.nextDouble() < 0.005 + 0.001 * stackedLevel)
        {
            System.out.println("Adding yellow block");
            int x = chunk.xPosition * 16 + random.nextInt(16);
            int y = 254;
            int z = chunk.zPosition * 16 + random.nextInt(16);
            worldObj.setBlock(x, y, z, DecayingWorld.yellowDecay.blockID);
        }
    }
}

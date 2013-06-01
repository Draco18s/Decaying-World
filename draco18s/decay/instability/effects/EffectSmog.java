package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectSmog implements IEnvironmentalEffect
{
    @Override
    public void onChunkPopulate(World world, Random rand, int wx, int wz)
    {
        int x = rand.nextInt(16);
        int y = rand.nextInt(50) + 200;
        int z = rand.nextInt(16);
        world.setBlock(wx + x, y, wz + z, DecayingWorld.smogdecay.blockID);
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectNitro implements IEnvironmentalEffect
{
    @Override
    public void onChunkPopulate(World world, Random rand, int wx, int wz)
    {
        int ch = (wx * wx + wz * wz + (int)Math.pow(wx * wz - world.provider.dimensionId, 3)) % (27 + wx % 5);

        if (ch == 0)
        {
            int x = rand.nextInt(16);
            int z = rand.nextInt(16);
            int y = rand.nextInt(48)+16;
            world.setBlock(wx + x, y, wz + z, DecayingWorld.nitroDecay.blockID);
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }
}

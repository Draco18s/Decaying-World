package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class EffectWormDecay implements IEnvironmentalEffect
{
    private int stackedLevel;
    int materialID = 7;
    int materialMeta = 0;
    boolean symbol = false;
    int randInt = 0;

    public EffectWormDecay(Integer level, int ID, int meta)
    {
        if (level == -1)
        {
            symbol = true;
            level = 1;
            //System.out.println("Modified symbol - " + ID + ":" + meta + "," + randInt);
        }

        randInt = new Random().nextInt(117) + 1;
        stackedLevel = level;
        materialID = ID;
        materialMeta = meta;
    }
    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int chunkX = wx / 16;
        int chunkZ = wz / 16;
        int b = world.getBiomeGenForCoords(wx, wz).biomeID + world.provider.dimensionId;
        int ch = (randInt + chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX + chunkZ + (b % 22), 3)) % (118);
        ch = Math.abs(ch);
        ch -= (stackedLevel - 1);

        if (ch <= 0)
        {
            int x, y, z;
            x = random.nextInt(16);
            z = random.nextInt(16);
            int r = getFirstUncoveredBlock(world, wx + x, wz + z);

            if (r < 1)
            {
                r = 64;
            }

            y = random.nextInt(r) + 1;
            world.setBlock(wx + x, y, wz + z, DecayingWorld.wormDecay.blockID, random.nextInt(16), 3);
            MaterialEntity ent = (MaterialEntity)world.getBlockTileEntity(wx + x, y, wz + z);
            //System.out.println(materialID + ":" + materialMeta + "," + randInt + " worm at " + (wx+x) + "," + y + "," + (wz+z));
            ent.materialBlockID = materialID;
            ent.materialBlockMeta = materialMeta;
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }

    private int getFirstUncoveredBlock(World world, int x, int z)
    {
        int var3;

        for (var3 = 0; var3 < 130 && !world.canBlockSeeTheSky(x, var3, z); ++var3)
        {
            if (var3 == 8)
            {
                var3 = world.provider.getAverageGroundLevel() - 3;

                if (var3 < 8)
                {
                    var3 = 8;
                }
            }
        }

        if (var3 > 128)
        {
            return -1;
        }

        return var3;
    }
}

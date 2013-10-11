package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;

public class EffectStarDecay implements IEnvironmentalEffect
{
    //private static MoreDecaySaveData wsd;
    //private static MapStorage perWorldStorage;

    public EffectStarDecay()
    {
        /*perWorldStorage = new MapStorage((ISaveHandler)null);
        MoreDecaySaveData var6 = (MoreDecaySaveData)perWorldStorage.loadData(MoreDecaySaveData.class, "MoreDecayData");

        if (var6 == null)
        {
        	System.out.println("No previous data found");
            wsd = new MoreDecaySaveData("MoreDecayData");
            perWorldStorage.setData("StarryDecay", wsd);
        }
        else
        {
        	System.out.println("Loading previous data");
            wsd = var6;
            perWorldStorage.loadData(MoreDecaySaveData.class, "MoreDecayData");
        }*/
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int id = world.provider.dimensionId;
        ChunkCoordinates spawn = world.getSpawnPoint();
        int a = (spawn.posX >> 4);
        int b = (spawn.posZ >> 4);
        int c = world.provider.dimensionId;
        int t = (a * b) + c;
        int shiftX = t % 10;
        b = c;
        c = -1 * (t >> 1);
        t = (a * b) + c;
        int shiftZ = t % 10;
        int chunkX = wx / 16;
        int chunkZ = wz / 16;

        if (chunkX != (spawn.posX >> 4) + shiftX || chunkZ != (spawn.posZ >> 4) + shiftZ)
        {
            return;
        }

        //if(wsd.dimsUsed.indexOf(id) == -1) {
        int x, y, z;
        x = random.nextInt(16);
        z = random.nextInt(16);
        y = this.getFirstUncoveredBlock(world, wx + x, wz + z) - 1;

        if (y > -1)
        {
            //System.out.println("Star decay placed at " + (wx+x) + "," + y + "," + (wz+z));
            world.setBlock(wx + x, y, wz + z, DecayingWorld.starDecay.blockID);
            //wsd.dimsUsed.add(id);
            //wsd.markDirty();
            //perWorldStorage.saveAllData();
        }

        //}
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

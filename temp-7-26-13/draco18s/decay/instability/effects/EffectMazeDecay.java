package draco18s.decay.instability.effects;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class EffectMazeDecay implements IEnvironmentalEffect
{
    int materialID = 7;
    int materialMeta = 0;

    //int[] extraIDs;
    //int[] extraMetas;
    int extraCount;
    //BlockDescriptor[] blocksdesc;
    int[] extraIDs;
    int[] extraMetas;

    public EffectMazeDecay(int ID, int meta)
    {
        materialID = ID;
        materialMeta = meta;
    }

    public void addMaterials(BlockDescriptor[] blocks, int count)
    {
        if (count == 0)
        {
            return;
        }

        //System.out.println("Count EMD ln34: " + count);
        extraIDs = new int[count];
        extraMetas = new int[count];
        /*blocksdesc = blocks;*/
        extraCount = count-1;

        count--;
        do
        {
            System.out.println("Extra ID: " + blocks[count].id);
            extraIDs[count] = blocks[count].id;
            extraMetas[count] = blocks[count].metadata;
            count--;
        }
        while (count >= 0);
    }

    @Override
    public void onChunkPopulate(World world, Random random, int wx, int wz)
    {
        int id = world.provider.dimensionId;
        ChunkCoordinates spawn = world.getSpawnPoint();
        int chunkX = wx / 16;
        int chunkZ = wz / 16;

        if (chunkX != (spawn.posX >> 4) || chunkZ != (spawn.posZ >> 4))
        {
            return;
        }

        int x, y, z;
        x = spawn.posX;
        z = spawn.posZ;
        y = getFirstUncoveredBlock(world, x, z) - 9;
        if (y < 0)
        {
            y = 3;
        }

        for (int i = -5; i < 6; i++)
        {
            for (int j = -5; j < 6; j++)
            {
                world.setBlock(x + (i * 3), y, z + (j * 3), DecayingWorld.mazeDecay.blockID, random.nextDouble() > 0.5 ? 3 : 1, 3);
            }
        }

        world.setBlock(spawn.posX, 0, spawn.posZ, DecayingWorld.mazeWallsMat.blockID);
        MaterialEntity mme = (MaterialEntity)world.getBlockTileEntity(spawn.posX, 0, spawn.posZ);
        //System.out.println("MME: " + mme);
        if (mme != null)
        {
            mme.setMaterials(extraIDs, extraMetas);
            //mme.matID = extraIDs;
            //mme.matMD = extraMetas;
            //mme.extraCount = extraCount;
        }
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
    }

    private int getFirstUncoveredBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

        if (h > 0)
        {
            return h;
        }
        else
        {
            int var3;
            int max = 130;

            for (var3 = 0; var3 < max && !world.canBlockSeeTheSky(x, var3, z); ++var3)
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

            if (var3 >= max)
            {
                return -1;
            }

            return var3;
        }
    }
}

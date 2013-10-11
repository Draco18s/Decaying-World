package draco18s.decay;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class SeedDecay implements IWorldGenerator
{
    private static double[] randomValue = new double[16000];

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        //DecayingWorld.greenDecay.blockID
        //int dim = world.worldInfo.getDimension();
        int dim = world.provider.dimensionId;

        if (randomValue[dim + 8000] == 0.0D)
        {
            randomValue[dim + 8000] = new Random().nextDouble();
            System.out.println(randomValue[dim + 8000]);
        }

        int x;
        int y;
        int z;
        int i;
        double v;
        int ch;
        BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);

        if (dim == 0)
        {
            ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ, 3)) % (27 + chunkX % 5);

            //System.out.println("Methane check: " + ch);
            if (ch == 0)
            {
                x = random.nextInt(16);
                z = random.nextInt(16);
                y = getFirstAirBlock(world, chunkX * 16 + x, chunkZ * 16 + z);
                System.out.println("Methane placed at " + (chunkX * 16 + x) + "," + y + "," + (chunkZ * 16 + z));
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.methanedecay.blockID);
            }

            ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ, 3)) % (125); //flat 125?

            if (ch == 0)
            {
                System.out.println("Adding molten");
                x = random.nextInt(16);
                y = 1;
                z = random.nextInt(16);
                System.out.println((chunkX * 16 + x) + "," + y + "," + (chunkZ * 16 + z));
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.moltenDecay.blockID, 4 + random.nextInt(4), 0);
                world.scheduleBlockUpdate(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.moltenDecay.blockID, 40000 + new Random().nextInt(200000));
            }

            //System.out.println("Adding silver");
            if (b.biomeName.equals("Extreme Hills") || b.biomeName.equals("Extreme Hills Edge") || b.biomeName.equals("MushroomIsland") || b.biomeName.equals("MushroomIslandShore"))
            {
                ch = 5;
            }
            else
            {
                ch = 1;
            }

            for (ch = random.nextInt(ch + 1); ch > 0; ch--)
            {
                if (this.getFirstUncoveredBlock(world, chunkX * 16, chunkZ * 16) < 10)
                {
                    break;
                }

                do
                {
                    x = random.nextInt(16);
                    y = random.nextInt(60) + 5;
                    z = random.nextInt(16);
                }
                while (world.getBlockId(x, y, z) != Block.stone.blockID);

                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.silverDecay.blockID);
            }

            ChunkCoordinates spawn = world.getSpawnPoint();
            int a = (spawn.posX >> 4);
            int b1 = (spawn.posZ >> 4);
            int c = world.provider.dimensionId;
            int t = (a * b1) + c;
            int shiftX = t % 10;
            b1 = c;
            c = -1 * (t >> 1);
            t = (a * b1) + c;
            int shiftZ = t % 10;

            if (chunkX != (spawn.posX >> 4) + shiftX || chunkZ != (spawn.posZ >> 4) + shiftZ)
            {
                return;
            }

            x = random.nextInt(16);
            z = random.nextInt(16);
            y = this.getFirstUncoveredBlock(world, chunkX * 16 + x, chunkZ * 16 + z);

            if (y > -1)
            {
                System.out.println("Star decay placed at " + (chunkX * 16 + x) + "," + y + "," + (chunkZ * 16 + z));
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.starDecay.blockID);
            }
        }
        else if (dim == -1)
        {
            ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ, 3)) % (119);

            if (ch == 0)
            {
                System.out.println("Adding molten");
                x = random.nextInt(16);
                y = 1;
                z = random.nextInt(16);
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.moltenDecay.blockID, 4 + random.nextInt(4), 0);
                world.scheduleBlockUpdate(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.moltenDecay.blockID, 24000 + new Random().nextInt(48000));
            }

            ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ, 3)) % (15 + chunkX % 5);

            if (ch == 0)
            {
                System.out.println("Adding pillar");
                x = random.nextInt(16);
                y = random.nextInt(5) + 1;
                z = random.nextInt(16);
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.pillarDecay.blockID);
            }
        }
        else if (dim == 1)
        {
            ch = (chunkX * chunkX + chunkZ * chunkZ + (int)Math.pow(chunkX * chunkZ, 3)) % (10 + chunkZ % 5);

            if (ch == 0)
            {
                System.out.println("Adding pillar");
                x = random.nextInt(16);
                y = 0;
                z = random.nextInt(16);
                world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, DecayingWorld.pillarDecay.blockID);
            }
        }
    }

    private int getFirstUncoveredBlock(World par1World, int x, int z)
    {
        int var3;

        for (var3 = 0; !par1World.isAirBlock(x, var3 + 1, z) || !par1World.canBlockSeeTheSky(x,  var3 + 1, z); ++var3)
        {
            if (var3 == 8)
            {
                var3 = 63;
            }
        }

        return var3;
    }

    private int getFirstAirBlock(World world, int x, int z)
    {
        int h = world.getChunkFromBlockCoords(x, z).heightMap[(z & 15) << 4 | (x & 15)];

        if (h < 0)
        {
            int var3;
            int max = 60;

            for (var3 = 0; var3 < max && world.getBlockId(x, var3, z) != 0; ++var3)
            {
                ;
            }

            if (var3 >= max)
            {
                return -1;
            }

            h = var3;
        }

        if (world.getBlockId(x, h, z) == 0)
        {
            return h;
        }

        return -1;
    }
}

package draco18s.decay.world;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrystalFormation extends WorldGenerator
{
    static final byte[] otherCoordPairs = new byte[] {(byte)2, (byte)0, (byte)0, (byte)1, (byte)2, (byte)1};
    private World worldObj;
    private int blockId;
    int[] crystalbase;
    int baserange = 2;

    public WorldGenCrystalFormation(int var1)
    {
        this.blockId = var1;
    }

    void placeBase() {}

    void generateLine(Random var1)
    {
        float var2 = var1.nextFloat() * 140.0F + 15.0F;
        int var3 = var1.nextInt(7) + 6;
        int[] var4 = new int[] {this.crystalbase[0] + (int)((double)var3 * Math.cos((double)var2 * Math.PI / 180.0D)), this.crystalbase[1] + (int)((double)var3 * Math.sin((double)var2 * Math.PI / 180.0D)), this.crystalbase[2] + var1.nextInt(7) - 3};
        this.placeBlockLine(this.crystalbase, var4, this.blockId);
    }

    void placeBlockLine(int[] var1, int[] var2, int var3)
    {
        int[] var4 = new int[] {0, 0, 0};
        byte var5 = 0;
        byte var6;

        for (var6 = 0; var5 < 3; ++var5)
        {
            var4[var5] = var2[var5] - var1[var5];

            if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
            {
                var6 = var5;
            }
        }

        if (var4[var6] != 0)
        {
            byte var7 = otherCoordPairs[var6];
            byte var8 = otherCoordPairs[var6 + 3];
            byte var9;

            if (var4[var6] > 0)
            {
                var9 = 1;
            }
            else
            {
                var9 = -1;
            }

            double var10 = (double)var4[var7] / (double)var4[var6];
            double var12 = (double)var4[var8] / (double)var4[var6];
            int[] var14 = new int[] {0, 0, 0};
            int var15 = 0;

            for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
            {
                var14[var6] = MathHelper.floor_double((double)(var1[var6] + var15) + 0.5D);
                var14[var7] = MathHelper.floor_double((double)var1[var7] + (double)var15 * var10 + 0.5D);
                var14[var8] = MathHelper.floor_double((double)var1[var8] + (double)var15 * var12 + 0.5D);
                this.drawSphere(var14[0], var14[1], var14[2], var3);
            }
        }
    }

    void drawSphere(int var1, int var2, int var3, int var4)
    {
        this.worldObj.setBlock(var1, var2, var3, this.blockId);
        this.worldObj.setBlock(var1 + 1, var2, var3, this.blockId);
        this.worldObj.setBlock(var1 - 1, var2, var3, this.blockId);
        this.worldObj.setBlock(var1, var2 + 1, var3, this.blockId);
        this.worldObj.setBlock(var1, var2 - 1, var3, this.blockId);
        this.worldObj.setBlock(var1, var2, var3 + 1, this.blockId);
        this.worldObj.setBlock(var1, var2, var3 - 1, this.blockId);
    }
    /**
     * Generates the feature
     * Parameters are world, random, crystalbase X, unused, crystalbase Z
     */
    public boolean generate(World var1, Random var2, int var3, int var4, int var5)
    {
        this.worldObj = var1;
        this.crystalbase = new int[3];
        this.crystalbase[0] = var3;
        this.crystalbase[1] = 0;
        this.crystalbase[2] = var5;

        if (!this.validLocation())
        {
            return false;
        }
        else
        {
            this.placeBase();
            int var6 = var2.nextInt(3) + 1;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                this.generateLine(var2);
            }

            return true;
        }
    }

    private boolean validLocation()
    {
        int var1;

        for (var1 = this.worldObj.getBlockId(this.crystalbase[0], this.crystalbase[1], this.crystalbase[2]); var1 == 0; var1 = this.worldObj.getBlockId(this.crystalbase[0], this.crystalbase[1], this.crystalbase[2]))
        {
            ++this.crystalbase[1];

            if (this.crystalbase[1] > this.worldObj.getHeight())
            {
                return false;
            }
        }

        while (var1 != Block.waterMoving.blockID && var1 != Block.waterStill.blockID && var1 != 0)
        {
            ++this.crystalbase[1];
            var1 = this.worldObj.getBlockId(this.crystalbase[0], this.crystalbase[1], this.crystalbase[2]);
        }

        this.crystalbase[1] -= 2;
        return this.worldObj.getBlockId(this.crystalbase[0], this.crystalbase[1], this.crystalbase[2]) > 0;
    }
}

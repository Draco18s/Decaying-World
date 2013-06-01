package draco18s.decay.instability.effects;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.xcompwiz.mystcraft.api.symbol.logic.IEnvironmentalEffect;

public class EffectNearSun implements IEnvironmentalEffect
{
    int level;

    public EffectNearSun(int var1)
    {
        level = var1;
    }

    @Override
    public void onChunkPopulate(World worldObj, Random rand, int i, int j)
    {
    }

    @Override
    public void tick(World worldObj, Chunk chunk)
    {
        int s = worldObj.calculateSkylightSubtracted(0);

        if (s < 8 && !worldObj.isRaining())
        {
            if (chunk.hasEntities)
            {
                if (worldObj.rand.nextInt(10) == 0)
                {
                    int var3 = worldObj.rand.nextInt(chunk.entityLists.length);
                    List var4 = chunk.entityLists[var3];

                    if (var4.size() > 0)
                    {
                        Entity var5 = (Entity)var4.get(worldObj.rand.nextInt(var4.size()));

                        if (var5 instanceof EntityLiving && worldObj.canBlockSeeTheSky(MathHelper.floor_double(var5.posX), MathHelper.floor_double(var5.posY), MathHelper.floor_double(var5.posZ)))
                        {
                            var5.setFire(4 * level);
                        }
                    }
                }
            }
        }
    }
}

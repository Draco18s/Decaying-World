package draco18s.decay.instability.providers;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectWormDecay;

public class EffectWormDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "WormyDecay";
    }

    @Override
    public int stabilization()
    {
        return 75;
    }

    @Override
    public Integer minimumInstability()
    {
        return 250;
    }

    @Override
    public Integer maximumInstability()
    {
        return 1500;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        //System.out.println("Wormy decay added to world " + level + " times.");
        controller.registerEffect(new EffectWormDecay(level, Block.cobblestone.blockID, 0));
    }

    @Override
    public Integer maxLevel()
    {
        return 5;
    }
}

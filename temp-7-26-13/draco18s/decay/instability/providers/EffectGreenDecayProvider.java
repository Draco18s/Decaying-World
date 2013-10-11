package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectGreenDecay;

public class EffectGreenDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "GreenDecay";
    }

    @Override
    public int stabilization()
    {
        return 250;
    }

    @Override
    public Integer minimumInstability()
    {
        return 250;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        //System.out.println("Green decay added to world " + level + " times.");
        controller.registerEffect(new EffectGreenDecay(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 10;
    }
}

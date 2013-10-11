package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectBrittleDecay;

public class EffectBrittleDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "BrittleDecay";
    }

    @Override
    public int stabilization()
    {
        return 500;
    }

    @Override
    public Integer minimumInstability()
    {
        return 600;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        controller.registerEffect(new EffectBrittleDecay(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 10;
    }
}

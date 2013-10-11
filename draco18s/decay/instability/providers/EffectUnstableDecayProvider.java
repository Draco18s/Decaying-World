package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectUnstableDecay;

public class EffectUnstableDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "UnstableDecay";
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
        controller.registerEffect(new EffectUnstableDecay(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 10;
    }
}

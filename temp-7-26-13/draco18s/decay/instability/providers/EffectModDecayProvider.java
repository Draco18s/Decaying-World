package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectModDecay;

public class EffectModDecayProvider implements IInstabilityProvider
{
    public EffectModDecayProvider()
    {
    }

    @Override
    public String identifier()
    {
        return "DecaySpecial";
    }

    @Override
    public int stabilization()
    {
        return 400;
    }

    @Override
    public Integer minimumInstability()
    {
        return 1200;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        controller.registerEffect(new EffectModDecay());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

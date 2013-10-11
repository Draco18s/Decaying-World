package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectStarDecay;

public class EffectStarDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "StarryDecay";
    }

    @Override
    public int stabilization()
    {
        return 10;
    }

    @Override
    public Integer minimumInstability()
    {
        return 1;
    }

    @Override
    public Integer maximumInstability()
    {
        return 400;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        //System.out.println("Starry decay added to world.");
        controller.registerEffect(new EffectStarDecay());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

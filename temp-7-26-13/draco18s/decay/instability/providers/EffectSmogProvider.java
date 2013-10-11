package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectSmog;

public class EffectSmogProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "Smoggy";
    }

    @Override
    public int stabilization()
    {
        return 150;
    }

    @Override
    public Integer minimumInstability()
    {
        return 300;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        controller.registerEffect(new EffectSmog());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

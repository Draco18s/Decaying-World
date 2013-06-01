package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectPoisonFog;

public class EffectPoisonFogProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "DeadlyFog";
    }

    @Override
    public int stabilization()
    {
        return 250;
    }

    @Override
    public Integer minimumInstability()
    {
        return 400;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        controller.registerEffect(new EffectPoisonFog());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

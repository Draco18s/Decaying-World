package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectNegativeEnergy;

public class EffectNegativeEnergyProvider implements IInstabilityProvider
{
    public EffectNegativeEnergyProvider()
    {
    }

    @Override
    public String identifier()
    {
        return "NegativeEnergy";
    }

    @Override
    public int stabilization()
    {
        return 75;
    }

    @Override
    public Integer minimumInstability()
    {
        return 0;
    }

    @Override
    public Integer maximumInstability()
    {
        return 450;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        controller.registerEffect(new EffectNegativeEnergy(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 4;
    }
}

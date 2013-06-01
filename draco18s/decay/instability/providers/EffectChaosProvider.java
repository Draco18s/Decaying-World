package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectChaos;

public class EffectChaosProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "DecayChaos";
    }

    @Override
    public int stabilization()
    {
        return 1000;
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
        //System.out.println("Chaos instability added to world.");
        controller.registerEffect(new EffectChaos());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

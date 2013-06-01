package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectMethane;

public class EffectMethaneProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "MethanePockets";
    }

    @Override
    public int stabilization()
    {
        return 100;
    }

    @Override
    public Integer minimumInstability()
    {
        return null;
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
        controller.registerEffect(new EffectMethane());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

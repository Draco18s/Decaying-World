package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectYellowDecay;

public class EffectYellowDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "YellowDecay";
    }

    @Override
    public int stabilization()
    {
        return 200;
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
        //System.out.println("Yellow decay added to world " + level + " times.");
        controller.registerEffect(new EffectYellowDecay(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 5;
    }
}

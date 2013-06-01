package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectSilverDecay;

public class EffectSilverDecayProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        // TODO Auto-generated method stub
        return "SilverDecay";
    }

    @Override
    public int stabilization()
    {
        return 50;
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
        //System.out.println("Silver instability added to world " + level + " times.");
        controller.registerEffect(new EffectSilverDecay(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 10;
    }
}

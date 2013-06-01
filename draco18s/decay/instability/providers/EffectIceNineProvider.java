package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectIceNine;

public class EffectIceNineProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        // TODO Auto-generated method stub
        return "IceNine";
    }

    @Override
    public int stabilization()
    {
        // TODO Auto-generated method stub
        return 50;
    }

    @Override
    public Integer minimumInstability()
    {
        // TODO Auto-generated method stub
        return 500;
    }

    @Override
    public Integer maximumInstability()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        //System.out.println("Ice-nine instability added to world " + level + " times.");
        controller.registerEffect(new EffectIceNine(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 20;
    }
}

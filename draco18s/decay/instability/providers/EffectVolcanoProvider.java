package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectVolcano;

public class EffectVolcanoProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        // TODO Auto-generated method stub
        return "DecayVolcano";
    }

    @Override
    public int stabilization()
    {
        // TODO Auto-generated method stub
        return 150;
    }

    @Override
    public Integer minimumInstability()
    {
        // TODO Auto-generated method stub
        return -200;
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
        //System.out.println("Volcano instability added to world " + level + " times.");
        controller.registerEffect(new EffectVolcano(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 4;
    }
}

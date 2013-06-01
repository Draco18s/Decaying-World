package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectDenseFog;
import draco18s.decay.instability.effects.EffectFog;

public class EffectDenseFogProvider implements IInstabilityProvider
{
    @Override
    public String identifier()
    {
        return "DenseFog";
    }

    @Override
    public int stabilization()
    {
        return 25;
    }

    @Override
    public Integer minimumInstability()
    {
        return 0;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
    	if(level == 1)
    		controller.registerEffect(new EffectFog());
    	else
    		controller.registerEffect(new EffectDenseFog());
    }

    @Override
    public Integer maxLevel()
    {
        return 2;
    }
}

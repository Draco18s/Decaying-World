package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectNearSun;

public class EffectNearSunProvider implements IInstabilityProvider
{
    public EffectNearSunProvider()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String identifier()
    {
        return "CloseOrbit";
    }

    @Override
    public int stabilization()
    {
        return 75;
    }

    @Override
    public Integer minimumInstability()
    {
        return 75;
    }

    @Override
    public Integer maximumInstability()
    {
        return null;
    }

    @Override
    public void addEffects(IInstabilityController controller, Integer level)
    {
        //System.out.println("Pillar instability added to world.");
        controller.registerEffect(new EffectNearSun(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 2;
    }
}

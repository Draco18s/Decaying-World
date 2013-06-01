package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectPillars;

public class EffectPillarsProvider implements IInstabilityProvider
{
    public EffectPillarsProvider()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String identifier()
    {
        return "DecayPillars";
    }

    @Override
    public int stabilization()
    {
        return 100;
    }

    @Override
    public Integer minimumInstability()
    {
        return 250;
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
        controller.registerEffect(new EffectPillars());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectNitro;

public class EffectNitroProvider implements IInstabilityProvider
{
    public EffectNitroProvider()
    {
    }

    @Override
    public String identifier()
    {
        return "DecayNitro";
    }

    @Override
    public int stabilization()
    {
        return 100;
    }

    @Override
    public Integer minimumInstability()
    {
        return 100;
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
        controller.registerEffect(new EffectNitro());
    }

    @Override
    public Integer maxLevel()
    {
        return 1;
    }
}

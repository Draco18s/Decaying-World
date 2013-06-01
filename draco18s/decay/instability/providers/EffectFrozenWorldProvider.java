package draco18s.decay.instability.providers;

import com.xcompwiz.mystcraft.api.instability.IInstabilityController;
import com.xcompwiz.mystcraft.api.instability.IInstabilityProvider;

import draco18s.decay.instability.effects.EffectFrozenWorld;

public class EffectFrozenWorldProvider implements IInstabilityProvider
{
    public EffectFrozenWorldProvider()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String identifier()
    {
        return "FrozenWorld";
    }

    @Override
    public int stabilization()
    {
        return 150;
    }

    @Override
    public Integer minimumInstability()
    {
        return 150;
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
        controller.registerEffect(new EffectFrozenWorld(level));
    }

    @Override
    public Integer maxLevel()
    {
        return 4;
    }
}

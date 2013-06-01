package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectWildTemps;

public class SymbolExtremeTemperatures implements IAgeSymbol
{
    //boolean unstable = false;

    @Override
    public float getRarity()
    {
        return 5;
    }

    @Override
    public void registerLogic(IAgeController controller, long seed)
    {
        controller.registerInterface(new EffectWildTemps());
    }

    @Override
    public int instabilityModifier(int count)
    {
        //int in = (unstable)?100:-100;
        //in += (count > 1)?count*50:0;
        count--;

        if (count > 0)
        {
            return 100 + count * 25;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public String identifier()
    {
        return "WildTemps";
    }

    @Override
    public String displayName()
    {
        return "Extreme Temperatures";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Time", "System", "Cycle", "Energy"};
        return str;
    }
}

package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectMethane;

public class SymbolMethane implements IAgeSymbol
{
    //boolean unstable = false;

    @Override
    public float getRarity()
    {
        return 2;
    }

    @Override
    public void registerLogic(IAgeController controller, long seed)
    {
        controller.registerInterface(new EffectMethane());
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 1) ? 50 : 0;
    }

    @Override
    public String identifier()
    {
        return "MethanePockets";
    }

    @Override
    public String displayName()
    {
        return "Bad Air Pockets";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Contain", "Energy", "Inhibit", "Life"};
        return str;
    }
}

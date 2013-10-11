package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.*;

public class SymbolSmog implements IAgeSymbol
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
        controller.registerInterface(new EffectSmog());
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 1) ? 150 : 50;
    }

    @Override
    public String identifier()
    {
        return "SmoggySkies";
    }

    @Override
    public String displayName()
    {
        return "Polluted Sky";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Inhibit", "Life", "Sacrifice", "Balance"};
        return str;
    }
}

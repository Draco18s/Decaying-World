package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectFrozenWorld;

public class SymbolFrozen implements IAgeSymbol
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
        controller.registerInterface(new EffectFrozenWorld(1));
    }

    @Override
    public int instabilityModifier(int count)
    {
        //int in = (unstable)?100:-100;
        //in += (count > 1)?count*50:0;
        if (count <= 3)
        {
            return 0;
        }
        else
        {
            return count * 75;
        }
    }

    @Override
    public String identifier()
    {
        return "FrozenWorld";
    }

    @Override
    public String displayName()
    {
        return "Frozen Age";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Void", "System", "Inhibit", "Energy"};
        return str;
    }
}

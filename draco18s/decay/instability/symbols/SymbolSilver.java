package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectSilverDecay;

public class SymbolSilver implements IAgeSymbol
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
        controller.registerInterface(new EffectSilverDecay(1));
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 6) ? 25 : 0;
    }

    @Override
    public String identifier()
    {
        return "DecaySilver";
    }

    @Override
    public String displayName()
    {
        return "Silverfish";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Nature", "Chaos", "Rebirth", "Sacrifice"};
        return str;
    }
}

package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectVolcano;

public class SymbolMolten implements IAgeSymbol
{
    //boolean unstable = false;

    /*@Override
    public float getRarity()
    {
        return 2;
    }*/

    @Override
    public void registerLogic(IAgeController controller, long seed)
    {
        controller.registerInterface(new EffectVolcano(1));
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 3) ? 75 : 0;
    }

    @Override
    public String identifier()
    {
        return "DecayMolten";
    }

    @Override
    public String displayName()
    {
        return "Volcanoes";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Nature", "Dynamic", "Energy", "System"};
        return str;
    }

	@Override
	public float getRarity() {
		return 0.4f;//getDescriptorWords
	}
}

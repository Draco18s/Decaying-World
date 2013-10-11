package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectGlowDecay;

public class SymbolGlow implements IAgeSymbol
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
        controller.registerInterface(new EffectGlowDecay());
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 1) ? 25 : 0;
    }

    @Override
    public String identifier()
    {
        return "DecayGlow";
    }

    @Override
    public String displayName()
    {
        return "Glowing Crystals";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Entropy", "Death", "Rebirth", "Life"};
        return str;
    }

	@Override
	public float getRarity() {
		return 0.4f;//getDescriptorWords
	}
}

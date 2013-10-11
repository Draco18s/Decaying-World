package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectPositiveEnergy;

public class SymbolPositiveEnergy implements IAgeSymbol
{
    //boolean unstable = false;

    /*@Override
    public float getRarity()
    {
        return 1;
    }*/

    @Override
    public void registerLogic(IAgeController controller, long seed)
    {
        controller.registerInterface(new EffectPositiveEnergy(1));
    }

    @Override
    public int instabilityModifier(int count)
    {
        //int in = (unstable)?100:-100;
        //in += (count > 1)?count*50:0;
        if (count < 3)
        {
            return 0;
        }
        else
        {
            return count * 25;
        }
    }

    @Override
    public String identifier()
    {
        return "PositiveEnergy";
    }

    @Override
    public String displayName()
    {
        return "Positive Energy";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"System", "Encourage", "Infinity", "Energy"};
        return str;
    }

	@Override
	public float getRarity() {
		return 0.4f;//getDescriptorWords
	}
}

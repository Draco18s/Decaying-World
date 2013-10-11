package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import com.xcompwiz.mystcraft.api.symbol.Modifier;

import draco18s.decay.instability.effects.*;

public class SymbolDenseFog implements IAgeSymbol
{
    //boolean unstable = false;

    @Override
    public float getRarity()
    {
        return 10;
    }

    @Override
    public void registerLogic(IAgeController controller, long seed)
    {
        int density = 1;
    	Number a = controller.popModifier("FogSymbol").asNumber();
    	if (a != null) {
    		density += a.intValue();
    	}
    	if(density == 1)
    		controller.registerInterface(new EffectFog());
    	else
    		controller.registerInterface(new EffectDenseFog());

    	Modifier obj = new Modifier(density,0);
    	controller.setModifier("FogSymbol", obj);
    }

    @Override
    public int instabilityModifier(int count)
    {
        return (count > 2) ? 10*count : 5;
    }

    @Override
    public String identifier()
    {
        return "DenseFog";
    }

    @Override
    public String displayName()
    {
        return "Obscuring Mist";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Nature", "Ethereal", "Chaos", "Flow"};
        return str;
    }
}

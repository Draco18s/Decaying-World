package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectWormDecay;

public class SymbolWorm implements IAgeSymbol
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
        BlockDescriptor blockDescriptor = BlockDescriptor.popBlockOfType(controller, "STRUCTURE");
        EffectWormDecay feature;

        if (blockDescriptor != null)
        {
            //System.out.println("Worm tube material: " + blockDescriptor.id);
            feature = new EffectWormDecay(-1, blockDescriptor.id, blockDescriptor.metadata);
            double x = (double)blockDescriptor.getInstability(250);
            int inst = (int)(10 + 7.7 * x + 0.026 * x * x + 0.0003 * x * x * x);
            //-20.+8.0x+0.038 x^2+0.0002 x^3
            System.out.println("Worm instability amount: " + inst);
            controller.addInstability(inst);
            //unstable = true;
        }
        else
        {
            feature = new EffectWormDecay(1, Block.cobblestone.blockID, 0);
            controller.addInstability(-70);
        }

        controller.registerInterface(feature);
    }

    @Override
    public int instabilityModifier(int count)
    {
        count--;
        return count * count * 3;
    }

    @Override
    public String identifier()
    {
        return "DecayWorm";
    }

    @Override
    public String displayName()
    {
        return "Worms";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Chaos", "Motion", "Form", "Flow"};
        return str;
    }
}

package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockCategory;
import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import com.xcompwiz.mystcraft.api.symbol.ModifierUtils;

import draco18s.decay.instability.effects.EffectMazeDecay;

public class SymbolMaze implements IAgeSymbol
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
    	controller.popModifier("");
    	BlockDescriptor block = ModifierUtils.popBlockOfType(controller, BlockCategory.STRUCTURE);
        EffectMazeDecay feature;
        double IF;

        if (block != null)
        {
            //System.out.println("Maze wall material: " + block.id);
            //System.out.println("Instability: " + block.getInstability(2000));
            feature = new EffectMazeDecay(block.id, block.metadata);
            BlockDescriptor[] allBlocks = new BlockDescriptor[4];
            allBlocks[0] = block;
            block = ModifierUtils.popBlockOfType(controller, BlockCategory.STRUCTURE);
            int i = 0;

            while (block != null)
            {
                i++;
                allBlocks[i] = block;
                block = ModifierUtils.popBlockOfType(controller, BlockCategory.STRUCTURE);
            }
            if (i > 0)
            {
                double allInst = 0;
                allBlocks = sortDescriptors(allBlocks, 0, i);
                System.out.println("aB[0]" + allBlocks[0].id);
                IF = (double)allBlocks[0].getInstability(13.88F * 157.5F);

                if (IF < 0)
                {
                    IF = 0;
                }

                allInst += IF / (i + 1);

                if (i >= 1)
                {
                	System.out.println("aB[1]" + allBlocks[1].id);
                    IF = (double)allBlocks[1].getInstability(13.88F * 157.5F);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                if (i >= 2)
                {
                	System.out.println("aB[2]" + allBlocks[2].id);
                    IF = (double)allBlocks[2].getInstability(13.88F * 157.5F);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                if (i >= 3)
                {
                	System.out.println("aB[3]" + allBlocks[3].id);
                    IF = (double)allBlocks[3].getInstability(13.88F * 157.5F);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                feature.addMaterials(allBlocks, i+1);
                allInst += i;
                controller.addInstability((int)(allInst));
            }
            else
            {
                controller.addInstability(allBlocks[0].getInstability(13.88F * 157.5F));
                feature.addMaterials(allBlocks, 1);
            }

            //unstable = true;
        }
        else
        {
        	//System.out.println("Did not find a valid modifier");
            feature = new EffectMazeDecay(Block.bedrock.blockID, 0);
            controller.addInstability(-100);
        }

        controller.registerInterface(feature);
        //controller.registerInterface(new EffectMazeDecay());
    }

    private BlockDescriptor[] sortDescriptors(BlockDescriptor[] allBlocks, int left, int right)
    {
        int pivot = 0;

        //System.out.println("Quicksort:" + left + "," + right);
        if (left < right)
        {
            pivot = partition(allBlocks, left, right, pivot);
            allBlocks = sortDescriptors(allBlocks, left, pivot - 1);
            allBlocks = sortDescriptors(allBlocks, pivot + 1, right);
        }

        return allBlocks.clone();
    }

    private int partition(BlockDescriptor[] allBlocks, int left, int right, int index)
    {
        int pivotValue = allBlocks[index].getInstability(13.88F * 157.5F);
        //swap pivot to the end
        BlockDescriptor temp = allBlocks[right];
        allBlocks[right] = allBlocks[index];
        allBlocks[index] = temp;
        int storeIndex = left;

        for (int i = left; i < right; i++)
        {
            if (allBlocks[i].getInstability(13.88F * 157.5F) <= pivotValue)
            {
                temp = allBlocks[i];
                allBlocks[i] = allBlocks[storeIndex];
                allBlocks[storeIndex] = temp;
                storeIndex++;
            }
        }

        temp = allBlocks[right];
        allBlocks[right] = allBlocks[storeIndex];
        allBlocks[storeIndex] = temp;
        return storeIndex;
    }

    @Override
    public int instabilityModifier(int count)
    {
        //int in = (unstable)?100:-100;
        //in += (count > 1)?count*50:0;
        count--;
        return count * 50;
    }

    @Override
    public String identifier()
    {
        return "DecayMaze";
    }

    @Override
    public String displayName()
    {
        return "Maze";
    }

    @Override
    public String[] getDescriptorWords()
    {
        String[] str = {"Explore", "Chaos", "Form", "Puzzle"};
        return str;
    }

	@Override
	public float getRarity() {
		return 0.4f;//getDescriptorWords
	}
}

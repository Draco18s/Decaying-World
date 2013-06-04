package draco18s.decay.instability.symbols;

import net.minecraft.block.Block;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;
import com.xcompwiz.mystcraft.api.symbol.IAgeController;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;

import draco18s.decay.instability.effects.EffectMazeDecay;

public class SymbolMaze implements IAgeSymbol
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
        BlockDescriptor blockDescriptor = BlockDescriptor.popBlockOfType(controller, BlockDescriptor.ANY);
        EffectMazeDecay feature;
        double IF;

        if (blockDescriptor != null)
        {
            System.out.println("Maze wall material: " + blockDescriptor.id);
            System.out.println("Instability: " + blockDescriptor.getInstability(2000));
            feature = new EffectMazeDecay(blockDescriptor.id, blockDescriptor.metadata);
            BlockDescriptor[] allBlocks = new BlockDescriptor[4];
            allBlocks[0] = blockDescriptor;
            blockDescriptor = BlockDescriptor.popBlockOfType(controller, BlockDescriptor.STRUCTURE);
            int i = 0;

            while (blockDescriptor != null)
            {
                i++;
                allBlocks[i] = blockDescriptor;
                blockDescriptor = BlockDescriptor.popBlockOfType(controller, BlockDescriptor.STRUCTURE);
            }

            if (i > 0)
            {
                double allInst = 0;
                allBlocks = sortDescriptors(allBlocks, 0, i);
                System.out.println("aB[0]" + allBlocks[0].getInstability(2000));
                IF = (double)allBlocks[0].getInstability(2000);

                if (IF < 0)
                {
                    IF = 0;
                }

                allInst += IF / (i + 1);

                if (i > 1)
                {
                    IF = (double)allBlocks[1].getInstability(2000);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                if (i > 2)
                {
                    IF = (double)allBlocks[2].getInstability(2000);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                if (i > 3)
                {
                    IF = (double)allBlocks[3].getInstability(2000);

                    if (IF < 0)
                    {
                        IF = 0;
                    }

                    allInst += IF / (i + 1);
                }

                feature.addMaterials(allBlocks, i);
                allInst += i;
                controller.addInstability((int)(12 * allInst));
            }
            else
            {
                controller.addInstability(12 * allBlocks[0].getInstability(2000));
            }

            //unstable = true;
        }
        else
        {
        	System.out.println("Did not find a valid modifier");
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

        return allBlocks;
    }

    private int partition(BlockDescriptor[] allBlocks, int left, int right, int index)
    {
        int pivotValue = allBlocks[index].getInstability(2000);
        //swap pivot to the end
        BlockDescriptor temp = allBlocks[right];
        allBlocks[right] = allBlocks[index];
        allBlocks[index] = temp;
        int storeIndex = left;

        for (int i = left; i < right; i++)
        {
            if (allBlocks[i].getInstability(2000) <= pivotValue)
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
}
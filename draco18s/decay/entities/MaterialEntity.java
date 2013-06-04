package draco18s.decay.entities;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MaterialEntity extends TileEntity
{
    public int materialBlockID = 4;
    public int materialBlockMeta = 0;

    public int[] matID = new int[3];
    public int[] matMD = new int[3];
    public int extraCount = 0;

    @Override
    public void readFromNBT(NBTTagCompound tc)
    {
        super.readFromNBT(tc);
        materialBlockID = tc.getInteger("materialBlockID");
        materialBlockMeta = tc.getInteger("materialBlockMeta");
        extraCount = tc.getInteger("ExtraCount");
        if(extraCount != 0) {
	        matID = tc.getIntArray("ExtraIDs");
	        matMD = tc.getIntArray("ExtraMetas");
        }
        else {
            matID = new int[3];
            matMD = new int[3];
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tc)
    {
        super.writeToNBT(tc);
        tc.setInteger("materialBlockID", (int)materialBlockID);
        tc.setInteger("materialBlockMeta", (int)materialBlockMeta);
        tc.setInteger("ExtraCount", extraCount);
        if(extraCount != 0) {
	        tc.setIntArray("ExtraIDs", matID);
	        tc.setIntArray("ExtraMetas", matMD);
        }
    }
    
    public void setMaterials(BlockDescriptor[] allBlocks) {
    	if(allBlocks.length > 4)
    		return;
    	if(extraCount == 0) {
    		int count = allBlocks.length-1;
    		do
            {
                matID[count - 1] = allBlocks[count].id;
                matMD[count - 1] = allBlocks[count].metadata;
                count--;
            }
            while (count > 0);
    	}
    	else if(extraCount < 3) {
    		int count = 0;
    		do
            {
                matID[count + extraCount] = allBlocks[count].id;
                matMD[count + extraCount] = allBlocks[count].metadata;
                count++;
            }
            while (count < extraCount - 3);
    	}
    	else {
    		return;
    	}
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
}

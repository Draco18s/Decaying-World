package draco18s.decay.entities;

import com.xcompwiz.mystcraft.api.internals.BlockDescriptor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MaterialEntity extends TileEntity
{
    public int materialBlockID = 4;
    public int materialBlockMeta = 0;

    public int[] matID = new int[4];
    public int[] matMD = new int[4];
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
    
    /*public void setMaterials(BlockDescriptor[] allBlocks) {
    	if(allBlocks.length > 4)
    		return;
    	if(extraCount == 0) {
    		int count = allBlocks.length-1;
    		extraCount = count;
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
    		extraCount +=  count;
    	}
    	else {
    		return;
    	}
    }*/
    
    public void setMaterials(int[] blockIds, int[] blockMds) {
    	if(blockIds.length > 4)
    		return;
    	if(extraCount == 0) {
    		int count = blockIds.length-1;
    		extraCount = blockIds.length;
    		do
            {
                matID[count] = blockIds[count];
                matMD[count] = blockMds[count];
                count--;
            }
            while (count >= 0);
    		//System.out.println("Material IDs: " + matID);
            //materialBlockID = blockIds[count];
            //materialBlockMeta = blockMds[count];
    	}
    	else if(extraCount < 4) {
    		int count = 0;
    		do
            {
                matID[count + extraCount] = blockIds[count];
                matMD[count + extraCount] = blockMds[count];
                count++;
            }
            while (count + extraCount < 4);
    		extraCount +=  count;
    	}
    	else {
    		return;
    	}
    }
}

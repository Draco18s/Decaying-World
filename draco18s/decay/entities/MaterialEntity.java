package draco18s.decay.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MaterialEntity extends TileEntity
{
    public int materialBlockID = 4;
    public int materialBlockMeta = 0;

    public int[] matID = new int[3];
    public int[] matMD = new int[3];
    public int extraCount = 0;
    
    public MaterialEntity() {
        //matID = new int[3];
        //matMD = new int[3];
        System.out.println("Setup " + matID[0]);
        System.out.println("Setup " + matID[1]);
        System.out.println("Setup " + matID[2]);
    }

    @Override
    public void readFromNBT(NBTTagCompound tc)
    {
        super.readFromNBT(tc);
        System.out.println("Reading NBT: " + materialBlockID);
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
        System.out.println("Writing NBT1: " + materialBlockID);
        tc.setInteger("materialBlockID", (int)materialBlockID);
        System.out.println("Writing NBT2: " + materialBlockMeta);
        tc.setInteger("materialBlockMeta", (int)materialBlockMeta);
        System.out.println("Writing NBT3: " + extraCount);
        tc.setInteger("ExtraCount", extraCount);
        if(extraCount != 0) {
	        System.out.println("Writing NBT4: " + matID[0]);
	        System.out.println("Writing NBT4: " + matID[1]);
	        System.out.println("Writing NBT4: " + matID[2]);
	        tc.setIntArray("ExtraIDs", matID);
	        System.out.println("Writing NBT5: " + matMD[0]);
	        System.out.println("Writing NBT5: " + matMD[1]);
	        System.out.println("Writing NBT5: " + matMD[2]);
	        tc.setIntArray("ExtraMetas", matMD);
        }
        System.out.println("Done Writing");
    }
}

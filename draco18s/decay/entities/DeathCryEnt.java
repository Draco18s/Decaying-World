package draco18s.decay.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class DeathCryEnt extends TileEntity
{
    public int expCount = 0;
    public int delay = 0;

    @Override
    public void readFromNBT(NBTTagCompound tc)
    {
        super.readFromNBT(tc);
        expCount = tc.getInteger("ExpCount");
        delay = tc.getInteger("Delay");
    }

    @Override
    public void writeToNBT(NBTTagCompound tc)
    {
        super.writeToNBT(tc);
        tc.setInteger("ExpCount", expCount);
        tc.setInteger("Delay", delay);
    }
    
    @Override
    public void updateEntity() {
    	if(delay > 0)
    		delay--;
    }
}

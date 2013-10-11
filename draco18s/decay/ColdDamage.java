package draco18s.decay;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class ColdDamage extends DamageSource
{
    public ColdDamage()
    {
        super("cold");
        setDamageBypassesArmor();
    }

    /*@Override
    public String getDeathMessage(EntityLiving par1EntityLiving)
    {
        EntityPlayer par1EntityPlayer = (EntityPlayer)par1EntityLiving;
        return par1EntityPlayer.username + " froze to death.";
    }*/
}
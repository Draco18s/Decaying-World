package draco18s.decay;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class NegativeDamage extends DamageSource
{
	public static final DamageSource type = new NegativeDamage();
    public NegativeDamage()
    {
        super("level drain");
        setDamageBypassesArmor();
        setMagicDamage();
    }

    @Override
    public String getDeathMessage(EntityLiving par1EntityLiving)
    {
        EntityPlayer par1EntityPlayer = (EntityPlayer)par1EntityLiving;
        return par1EntityPlayer.username + " expired from experience loss.";
    }
}
package draco18s.decay;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class PositiveDamage extends DamageSource
{
	public static final DamageSource type = new PositiveDamage();
    private PositiveDamage()
    {
        super("healing");
        setDamageBypassesArmor();
        setMagicDamage();
    }

    @Override
    public String getDeathMessage(EntityLiving par1EntityLiving)
    {
        EntityPlayer par1EntityPlayer = (EntityPlayer)par1EntityLiving;
        return par1EntityPlayer.username + " expired from over-healing.";
    }
}
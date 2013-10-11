package draco18s.decay.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import org.lwjgl.opengl.GL11;

import draco18s.decay.entities.EntityFooDog;

@SideOnly(Side.CLIENT)
public class RenderFooDog extends RenderLiving
{
    public RenderFooDog()
    {
        super(new ModelFooDog(), 0.5F);
    }

    protected float getTailRotation(EntityFooDog par1EntityWolf, float par2)
    {
        return par1EntityWolf.getTailRotation();
    }

    protected int func_82447_a(EntityFooDog par1EntityWolf, int par2, float par3)
    {
        float f1;

        if (par2 == 0 && par1EntityWolf.getWolfShaking())
        {
            f1 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
            this.loadTexture(par1EntityWolf.getTexture());
            GL11.glColor3f(f1, f1, f1);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.func_82447_a((EntityFooDog)par1EntityLiving, par2, par3);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
    {
        return this.getTailRotation((EntityFooDog)par1EntityLiving, par2);
    }
}

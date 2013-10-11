package draco18s.decay.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import draco18s.decay.entities.EntityEmpyreal;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderEmpyreal extends RenderLiving
{
    private int field_77068_a;

    public RenderEmpyreal()
    {
        super(new ModelEmpyreal(), 0.5F);
        this.field_77068_a = ((ModelEmpyreal)this.mainModel).func_78104_a();
    }

    public void renderBlaze(EntityEmpyreal par1EntityBlaze, double par2, double par4, double par6, float par8, float par9)
    {
        int i = ((ModelEmpyreal)this.mainModel).func_78104_a();

        if (i != this.field_77068_a)
        {
            this.field_77068_a = i;
            this.mainModel = new ModelEmpyreal();
        }

        super.doRenderLiving(par1EntityBlaze, par2, par4, par6, par8, par9);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderBlaze((EntityEmpyreal)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderBlaze((EntityEmpyreal)par1Entity, par2, par4, par6, par8, par9);
    }
}

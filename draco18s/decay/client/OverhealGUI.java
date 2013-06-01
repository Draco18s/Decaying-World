package draco18s.decay.client;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import draco18s.decay.PositiveDamage;

public class OverhealGUI implements ITickHandler
{
    public Minecraft mc;
    public int playerOverflowHp = 0;
    public int flag = 0;
    public int thisFreezeTimer = 0;
    public int lastFreezeTimer = 0;

    public OverhealGUI()
    {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        FontRenderer fontrenderer = mc.fontRenderer;
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        mc.entityRenderer.setupOverlayRendering();

        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        EnumSet a;
        a = EnumSet.of(TickType.RENDER);
        return a;
    }

    @Override
    public String getLabel()
    {
        return "OverhealBar";
    }

    public void onRenderTick()
    {
        if (mc.currentScreen == null && !mc.thePlayer.capabilities.isCreativeMode)
        {
            GuiIngame gui = this.mc.ingameGUI;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/mods/DecayingWorld/textures/gui/gui.png"));
            //System.out.println("Current hp: " + mc.thePlayer.getHealth());
            int hpo = playerOverflowHp;
            //hpo = 5;
            int u = (int)((double)hpo / 40 * 81);

            if (u > 0)
            {
                //mc.renderEngine.bindTexture("/mods/DecayingWorld/textures/gui/overheal.png");
                //ScaledResolution scaler = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
                //int width = scaler.getScaledWidth();
                //int barXStart = width / 2 - 182 / 2;
                //gui.drawTexturedModalRect(10, 10, 0, 0, 182, 5);
                int u2 = ((int)((double)(hpo - (hpo % 4)) / 4) + 1) * 8 + 1;
                int v = 0;
                //int u2 = u - u%4 + 4;
                //if(hpo % 2 == 0)
                u++;
                int delay = 16;
                //System.out.println("flag " + flag);

                if (flag > 20 && flag < 20 + delay)
                {
                    v = 18;
                }
                else if (flag > 21 + delay * 2 && flag < 20 + delay * 3)
                {
                    v = 18;
                }
                else if (flag > 104)
                {
                    flag = 0;
                }

                //System.out.println(v);
                gui.drawTexturedModalRect(122, 209, 0, v, u, 9);
                gui.drawTexturedModalRect(122, 209, 0, v + 9, u2, 9);

                if (flag > 0)
                {
                    flag += 1;
                }
            }
            else {
            	flag = 0;
            }
            
            if(thisFreezeTimer > lastFreezeTimer) {
            	lastFreezeTimer = thisFreezeTimer;
            }
            if(lastFreezeTimer > 0) {
            	lastFreezeTimer--;
            	u = (int)((float)lastFreezeTimer / 490 * 8);
            	u *= 32;
                float minU = (float)u/512;
                float minV = 72F/512;
                float maxU = (float)(u+32)/512;
                float maxV = 88F/512;
            	ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int k = scaledresolution.getScaledWidth();
                int l = scaledresolution.getScaledHeight();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                this.mc.renderEngine.bindTexture("/mods/DecayingWorld/textures/gui/gui.png");
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(0.0D,      (double)l, -90.0D, minU, maxV);
                tessellator.addVertexWithUV((double)k, (double)l, -90.0D, maxU, maxV);
                tessellator.addVertexWithUV((double)k, 0.0D,      -90.0D, maxU, minV);
                tessellator.addVertexWithUV(0.0D,      0.0D,      -90.0D, minU, minV);
                tessellator.draw();
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}

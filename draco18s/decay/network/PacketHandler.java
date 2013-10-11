package draco18s.decay.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import draco18s.decay.DecayingWorld;
import draco18s.decay.client.OverhealGUI;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals("MoreDecay"))
        {
            handleRandom(packet, player);
        }
    }

    private void handleRandom(Packet250CustomPayload packet, Player player)
    {
        EntityPlayer p = (EntityPlayer) player;
        World world = p.worldObj;
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
        //System.out.println("Packet get");
        try
        {
            int guiId = dis.readInt();
            OverhealGUI gui = (OverhealGUI)DecayingWorld.overlayGui;
            //System.out.println("switch("+guiId+")");
            switch (guiId)
            {
                case 1:
                    gui.playerOverflowHp = dis.readFloat();
                    //System.out.println("hp = "+gui.playerOverflowHp+"");
                    break;
                case 2:
                    gui.flag = 1;
                    //System.out.println("flag = 1");
                    break;
                case 3:
                	gui.thisFreezeTimer = dis.readInt();
                    //System.out.println("freeze = "+gui.thisFreezeTimer+"");
                	break;
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to read packet");
        }
        finally
        {
        }
    }
}

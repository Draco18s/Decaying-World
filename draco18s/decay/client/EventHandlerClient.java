package draco18s.decay.client;

import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.FMLCommonHandler;

public class EventHandlerClient {

	@ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event)
    {
    	FMLCommonHandler.instance().getFMLLogger().log(Level.INFO, "[Decay] Loading sounds...");
    	try {
	    	event.manager.addSound("decayingworld:rock1.ogg");
	    	event.manager.addSound("decayingworld:rock2.ogg");
    	}
    	catch (Exception e) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "[Decay] Failed adding sound file.");
			FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "[Decay] " + e.getMessage());
			e.printStackTrace();
    	}
    }
}

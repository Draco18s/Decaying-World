package draco18s.decay.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import draco18s.decay.CommonProxy;
import draco18s.decay.DecayingWorld;
import draco18s.decay.EventHandlers;
import draco18s.decay.entities.EntityBlinkDog;
import draco18s.decay.entities.EntityEmpyreal;
import draco18s.decay.entities.EntityFooDog;
import draco18s.decay.entities.EntityLifeBomb;
import draco18s.decay.entities.EntitySolidifier;
import draco18s.decay.entities.EntityTreant;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders() {
    	DecayingWorld.overlayGui = new OverhealGUI();
        TickRegistry.registerTickHandler(DecayingWorld.overlayGui, Side.CLIENT);
		RenderingRegistry.registerEntityRenderingHandler(EntitySolidifier.class, new RenderSnowball(DecayingWorld.solidifier));
        RenderingRegistry.registerEntityRenderingHandler(EntityLifeBomb.class, new RenderSnowball(DecayingWorld.lifebomb));
        RenderingRegistry.registerEntityRenderingHandler(EntityTreant.class, new RenderTreant());
        RenderingRegistry.registerEntityRenderingHandler(EntityBlinkDog.class, new RenderBlinkDog());
        RenderingRegistry.registerEntityRenderingHandler(EntityEmpyreal.class, new RenderEmpyreal());
        RenderingRegistry.registerEntityRenderingHandler(EntityFooDog.class, new RenderFooDog());
	}
	
	public void loadSounds() {
        MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
	}
}
package draco18s.decay;

import cpw.mods.fml.client.registry.RenderingRegistry;
import draco18s.decay.client.RenderBlinkDog;
import draco18s.decay.client.RenderEmpyreal;
import draco18s.decay.client.RenderFooDog;
import draco18s.decay.client.RenderTreant;
import draco18s.decay.entities.EntityBlinkDog;
import draco18s.decay.entities.EntityEmpyreal;
import draco18s.decay.entities.EntityFooDog;
import draco18s.decay.entities.EntityLifeBomb;
import draco18s.decay.entities.EntitySolidifier;
import draco18s.decay.entities.EntityTreant;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.util.Icon;

public class CommonProxy
{
    public Icon[] metadataIcons = new Icon[16];
    
    public void registerRenderers() {

    }
}
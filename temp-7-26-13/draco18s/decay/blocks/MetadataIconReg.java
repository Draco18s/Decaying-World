package draco18s.decay.blocks;

import draco18s.decay.DecayingWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class MetadataIconReg extends Block
{
    public MetadataIconReg(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        DecayingWorld.proxy.metadataIcons[0] = iconRegister.registerIcon("DecayingWorld:meta0");
        DecayingWorld.proxy.metadataIcons[1] = iconRegister.registerIcon("DecayingWorld:meta1");
        DecayingWorld.proxy.metadataIcons[2] = iconRegister.registerIcon("DecayingWorld:meta2");
        DecayingWorld.proxy.metadataIcons[3] = iconRegister.registerIcon("DecayingWorld:meta3");
        DecayingWorld.proxy.metadataIcons[4] = iconRegister.registerIcon("DecayingWorld:meta4");
        DecayingWorld.proxy.metadataIcons[5] = iconRegister.registerIcon("DecayingWorld:meta5");
        DecayingWorld.proxy.metadataIcons[6] = iconRegister.registerIcon("DecayingWorld:meta6");
        DecayingWorld.proxy.metadataIcons[7] = iconRegister.registerIcon("DecayingWorld:meta7");
        DecayingWorld.proxy.metadataIcons[8] = iconRegister.registerIcon("DecayingWorld:meta8");
        DecayingWorld.proxy.metadataIcons[9] = iconRegister.registerIcon("DecayingWorld:meta9");
        DecayingWorld.proxy.metadataIcons[10] = iconRegister.registerIcon("DecayingWorld:meta10");
        DecayingWorld.proxy.metadataIcons[11] = iconRegister.registerIcon("DecayingWorld:meta11");
        DecayingWorld.proxy.metadataIcons[12] = iconRegister.registerIcon("DecayingWorld:meta12");
        DecayingWorld.proxy.metadataIcons[13] = iconRegister.registerIcon("DecayingWorld:meta13");
        DecayingWorld.proxy.metadataIcons[14] = iconRegister.registerIcon("DecayingWorld:meta14");
        DecayingWorld.proxy.metadataIcons[15] = iconRegister.registerIcon("DecayingWorld:meta15");
        blockIcon = DecayingWorld.proxy.metadataIcons[0];
    }
}

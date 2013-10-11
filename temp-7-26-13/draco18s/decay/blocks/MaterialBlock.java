package draco18s.decay.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import draco18s.decay.entities.MaterialEntity;

public class MaterialBlock extends BlockContainer
{
    public MaterialBlock(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setHardness(-1F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Material Holder");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("bedrock");
    }

    @Override
    public TileEntity createNewTileEntity(World var1)
    {
        return new MaterialEntity();
    }
}

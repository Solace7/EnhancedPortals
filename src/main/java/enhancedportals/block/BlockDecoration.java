package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDecoration extends Block
{
    protected BlockDecoration(String n)
    {
        super(Material.rock);
        setBlockName(n);
        setHardness(3);
        setStepSound(soundTypeStone);
        setCreativeTab(CommonProxy.creativeTab);
    }
}

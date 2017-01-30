package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDecoration extends Block
{

    protected BlockDecoration(String n)
    {
        super(Material.ROCK);
        setHardness(3);
        setSoundType(SoundType.STONE);
        setCreativeTab(CommonProxy.creativeTab);
    }
}

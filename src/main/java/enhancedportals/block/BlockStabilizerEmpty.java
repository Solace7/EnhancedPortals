package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStabilizerEmpty extends Block
{
    public static BlockStabilizerEmpty instance;

    public BlockStabilizerEmpty(String n)
    {
        super(Material.ROCK);
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setHardness(5);
        setResistance(2000);
        setUnlocalizedName(n);
        setSoundType(SoundType.STONE);
    }
}

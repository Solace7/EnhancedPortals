package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStabilizerEmpty extends Block
{
    public static BlockStabilizerEmpty instance;

    public BlockStabilizerEmpty()
    {
        super(Material.ROCK);
        setRegistryName("dbs_empty");
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setHardness(5);
        setResistance(2000);
        setUnlocalizedName("dbs_empty");
        setSoundType(SoundType.STONE);
    }
}

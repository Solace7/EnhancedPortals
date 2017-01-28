package enhancedportals.block;

import enhancedportals.Reference;
import enhancedportals.network.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDecoration extends Block
{

    protected BlockDecoration(String n)
    {
        super(Material.ROCK);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(n);
        setHardness(3);
        setSoundType(SoundType.STONE);
        setCreativeTab(CommonProxy.creativeTab);
    }
}

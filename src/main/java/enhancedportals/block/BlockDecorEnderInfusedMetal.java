package enhancedportals.block;

import enhancedportals.Reference;

public class BlockDecorEnderInfusedMetal extends BlockDecoration
{
    public static BlockDecorEnderInfusedMetal instance;

    public BlockDecorEnderInfusedMetal(String n)
    {
        super(n);
        instance = this;
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(n);
    }

}

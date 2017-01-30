package enhancedportals.block;

import enhancedportals.Reference;

public class BlockDecorBorderedQuartz extends BlockDecoration
{
    public static BlockDecorBorderedQuartz instance;

    public BlockDecorBorderedQuartz(String n)
    {
        super(n);
        instance = this;
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
    }

}
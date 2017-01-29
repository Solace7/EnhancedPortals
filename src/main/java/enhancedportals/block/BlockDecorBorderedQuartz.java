package enhancedportals.block;

import enhancedportals.Reference;

public class BlockDecorBorderedQuartz extends BlockDecoration
{
    public static BlockDecorBorderedQuartz instance;

    public BlockDecorBorderedQuartz(String q)
    {
        super(q);
        instance = this;
        setRegistryName(Reference.EPMod.mod_id, q);
        setUnlocalizedName(getRegistryName().toString());
    }

}
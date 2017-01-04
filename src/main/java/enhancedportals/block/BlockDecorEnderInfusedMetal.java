package enhancedportals.block;

import enhancedportals.utility.ConnectedTexturesDetailed;

public class BlockDecorEnderInfusedMetal extends BlockDecoration
{
    public static BlockDecorEnderInfusedMetal instance;
    static ConnectedTexturesDetailed connectedTextures;

    public BlockDecorEnderInfusedMetal(String n)
    {
        super(n);
        instance = this;
        setRegistryName("decor_dbs");
        connectedTextures = new ConnectedTexturesDetailed(BlockStabilizer.connectedTextures.toString(), this, -1);
    }

}

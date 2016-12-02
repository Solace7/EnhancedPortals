package enhancedportals.block;

import enhancedportals.utility.ConnectedTexturesDetailed;

public class BlockDecorBorderedQuartz extends BlockDecoration
{
    public static BlockDecorBorderedQuartz instance;
    static ConnectedTexturesDetailed connectedTextures;

    public BlockDecorBorderedQuartz(String n)
    {
        super(n);
        instance = this;
        setRegistryName("borderedQuartz");
        connectedTextures = new ConnectedTexturesDetailed(BlockFrame.connectedTextures.toString(), this, -1);
    }

}

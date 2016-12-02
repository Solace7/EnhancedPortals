package enhancedportals.utility;

import net.minecraft.block.Block;

public class ConnectedTexturesDetailed extends ConnectedTextures
{
    private static final short[] connectionToIndex = {0, 36, 12, 24, 1, 37, 13, 25, 3, 39, 15, 27, 2, 38, 14, 26}, transformIndex26 = {26, 45, 44, 22, 33, 23, 35, 9, 32, 34, 10, 21, 11, 8, 20, 46};

    protected ConnectedTexturesDetailed()
    {
        super();
    }
/*
    public ConnectedTexturesDetailed(ConnectedTexturesDetailed ct, Block block, int meta)
    {
        this(ct.textureLoc, block, meta, -1);
        textures = ct.textures;
    }

    public ConnectedTexturesDetailed(ConnectedTexturesDetailed ct, Block block, int meta, int meta2)
    {
        this(ct.textureLoc, block, meta, meta2);
        textures = ct.textures;
    }*/

    public ConnectedTexturesDetailed(String textureLocation, Block block, int meta)
    {
        this(textureLocation, block, meta, -1);
    }

    public ConnectedTexturesDetailed(String textureLocation, Block block, int meta, int meta2)
    {
        super(textureLocation, block, meta, meta2);
        //textures = new IIcon[47];
    }

}

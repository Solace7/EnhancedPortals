package enhancedportals.registration;

import cpw.mods.fml.common.registry.GameRegistry;
import enhancedportals.tile.*;

public class RegisterTiles
{
    public static final void preInit()
    {
        GameRegistry.registerTileEntity(TilePortal.class, "epP");
        GameRegistry.registerTileEntity(TileFrameBasic.class, "epF");
        GameRegistry.registerTileEntity(TileController.class, "epPC");
        GameRegistry.registerTileEntity(TileRedstoneInterface.class, "epRI");
        GameRegistry.registerTileEntity(TileNetworkInterface.class, "epNI");
        GameRegistry.registerTileEntity(TileDialingDevice.class, "epDD");
        GameRegistry.registerTileEntity(TilePortalManipulator.class, "epMM");
        GameRegistry.registerTileEntity(TileStabilizer.class, "epDBS");
        GameRegistry.registerTileEntity(TileStabilizerMain.class, "epDBSM");
        GameRegistry.registerTileEntity(TileTransferEnergy.class, "epTE");
        GameRegistry.registerTileEntity(TileTransferFluid.class, "epTF");
        GameRegistry.registerTileEntity(TileTransferItem.class, "epTI");
    }
}

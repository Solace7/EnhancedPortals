package enhancedportals.tile;

import enhancedportals.utility.ConfigurationHandler;
import enhancedportals.utility.GeneralUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileFrame extends TilePortalPart
{
    protected boolean wearingGoggles = GeneralUtils.isWearingGoggles();

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        /*if (b == worldObj.getBlock(xCoord, yCoord, zCoord))
        {
            return;
        }*/


        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.onPartFrameBroken();
        }
    }


    //todo Get texture and colour

/*    public int getColour()
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            return controller.activeTextureData.getFrameColour();
        }
        else if (portalController != null)
        {
            EnhancedPortals.proxy.waitForController(new ChunkPos(portalController.chunkXPos, portalController.chunkZPos), getChunkCoordinates());
        }

        return 0xFFFFFF;
    }
*/
    public void onBlockDismantled(BlockPos pos)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.deconstruct();
        }
    }

    protected boolean shouldShowOverlay()
    {
        return wearingGoggles || ConfigurationHandler.CONFIG_FORCE_FRAME_OVERLAY;
    }


    //todo Update to check if glasses are being worn
  /*  @Override
    public void update()
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && Minecraft.getSystemTime() % 10 == 0)
        {
            boolean wGoggles = GeneralUtils.isWearingGoggles();

            if (wGoggles != wearingGoggles)
            {
                //todo worldObj.notifyBlockUpdate(pos);
                wearingGoggles = wGoggles;
            }
        }
    }*/
}

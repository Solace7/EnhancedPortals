package enhancedportals.tile;

import enhancedportals.utility.GeneralUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public abstract class TileFrame extends TilePortalPart implements ITickable
{
    protected boolean wearingGoggles = GeneralUtils.isWearingGoggles();

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
//        if (b == worldObj.getBlock(xCoord, yCoord, zCoord))
        if (world.getBlockState(getPos()).getBlock() == worldObj.getBlockState(getPos()).getBlock())
        {
            return;
        }

        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.onPartFrameBroken();
        }
    }

    public void onBlockDismantled(BlockPos pos)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.deconstruct();
        }
    }

/*    protected boolean shouldShowOverlay()
    {
        return wearingGoggles || ConfigurationHandler.CONFIG_FORCE_FRAME_OVERLAY;
    }*/


    @Override
    public void update()
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && Minecraft.getSystemTime() % 10 == 0)
        {
            boolean wGoggles = GeneralUtils.isWearingGoggles();

            if (wGoggles != wearingGoggles)
            {
                //todo worldObj.notifyBlockUpdate(pos);
                final IBlockState state = getWorld().getBlockState(getPos());
                worldObj.notifyBlockUpdate(getPos(), state, state, 3);
                wearingGoggles = wGoggles;
            }
        }
    }
}

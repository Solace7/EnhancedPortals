package enhancedportals.tile;

import enhancedportals.EnhancedPortals;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.utility.GeneralUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;


public class TilePortal extends TilePortalPart
{
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (GeneralUtils.isWrench(stack))
            {
                GuiHandler.openGui(player, controller, GuiHandler.PORTAL_CONTROLLER_A);
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, player.isSneaking() ? GuiHandler.TEXTURE_C : GuiHandler.TEXTURE_B);
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

    public int getColour()
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            return controller.activeTextureData.getPortalColour();
        }
        else if (portalController != null)
        {
            EnhancedPortals.proxy.waitForController(new ChunkPos(portalController.chunkXPos, portalController.chunkZPos), getChunkPos());
        }

        return 0xFFFFFF;
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

    }

    public void onEntityCollidedWithBlock(Entity entity)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.onEntityEnterPortal(entity, this);
        }
    }
}

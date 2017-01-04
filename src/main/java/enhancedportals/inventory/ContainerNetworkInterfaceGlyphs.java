package enhancedportals.inventory;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.client.gui.BaseGui;
import enhancedportals.client.gui.GuiNetworkInterfaceGlyphs;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.tile.TileController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerNetworkInterfaceGlyphs extends BaseContainer
{
    TileController controller;

    public ContainerNetworkInterfaceGlyphs(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiNetworkInterfaceGlyphs.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        controller = c;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("nid"))
        {
            controller.setIdentifierNetwork(new GlyphIdentifier(tag.getString("nid")));
            player.openGui(EnhancedPortals.instance, Reference.GuiEnums.GUI_MISC.NETWORK_INTERFACE_A.ordinal(), controller.getWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}

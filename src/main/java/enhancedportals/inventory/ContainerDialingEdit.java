package enhancedportals.inventory;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.portal.GlyphElement;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.tile.TileDialingDevice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerDialingEdit extends ContainerDialingAdd
{
    public ContainerDialingEdit(TileDialingDevice d, InventoryPlayer p)
    {
        super(d, p);
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("id") && tag.hasKey("uid") && tag.hasKey("texture") && tag.hasKey("name"))
        {
            PortalTextureManager ptm = new PortalTextureManager();
            ptm.readFromNBT(tag, "texture");
            dial.glyphList.set(tag.getInteger("id"), new GlyphElement(tag.getString("name"), new GlyphIdentifier(tag.getString("uid")), ptm));
            player.openGui(EnhancedPortals.instance, Reference.GuiEnums.GUI_DIAL.DIAL_A.ordinal(), dial.getWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}

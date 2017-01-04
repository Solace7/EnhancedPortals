package enhancedportals.inventory;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.portal.GlyphElement;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerAddressEdit extends BaseContainer
{
    ItemAddressBook addressBook;

    public ContainerAddressEdit(ItemAddressBook b, InventoryPlayer p)
    {
        super(null, p);
        addressBook = b;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("id") && tag.hasKey("uid") && tag.hasKey("texture") && tag.hasKey("name"))
        {
            PortalTextureManager ptm = new PortalTextureManager();
            ptm.readFromNBT(tag, "texture");
            addressBook.glyphList.set(tag.getInteger("id"), new GlyphElement(tag.getString("name"), new GlyphIdentifier(tag.getString("uid")), ptm));
            player.openGui(EnhancedPortals.instance, Reference.GuiEnums.GUI_ADDRESS_BOOK.AB_A.ordinal(), player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}

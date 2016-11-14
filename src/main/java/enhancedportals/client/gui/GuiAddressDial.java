package enhancedportals.client.gui;

import enhancedportals.client.gui.elements.ElementScrollAddressDial;
import enhancedportals.inventory.ContainerAddressBookDiallingDevice;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.tile.TileController;
import enhancedportals.tile.TileDialingDevice;
import enhancedportals.utility.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAddressDial extends BaseGui
{
    public static final int CONTAINER_SIZE = 175, CONTAINER_WIDTH = 256, CONTAINER_INW = 49, CONTAINER_INH = 201;
    GuiButton ButtonCopyTo, ButtonCopyFrom;
    TileDialingDevice dial;
    ItemAddressBook addressBook;
    TileController controller;

    public GuiAddressDial(TileDialingDevice d, ItemAddressBook ab, EntityPlayer player)
    {
        super(new ContainerAddressBookDiallingDevice(d, ab, player.inventory), CONTAINER_SIZE);
        texture = new ResourceLocation("enhancedportals", "textures/gui/address_book_dialling_device.png");
        xSize = CONTAINER_WIDTH;
        dial = d;
        addressBook = ab;
        controller = dial.getPortalController();
        name = "enhancedportals.gui.addressTransfer";
        setHidePlayerInventory();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        getFontRenderer().drawString(Localization.get("gui.addressTranfser"), 7, 18, 0x404040);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        ButtonCopyFrom = new GuiButton(0, guiLeft + 5, guiTop + ySize - 27, 12, 16, "CopyFrom");
        ButtonCopyTo = new GuiButton(1, guiLeft + 78, guiTop + ySize - 27, 12, 16, "CopyTo");

        buttonList.add(ButtonCopyFrom);
        buttonList.add(ButtonCopyTo);

        addElement(new ElementScrollAddressDial(this, dial, addressBook, 7, 28));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {

    }

    /*public void onEntryEdited(int entry)
    {
        ClientProxy.editingID = entry;
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("edit", entry);
        EnhancedPortals.packetPipeline.sendToServer(new PacketGuiData(tag));
    }

    public void onEntryDeleted(int entry)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("delete", entry);
        EnhancedPortals.packetPipeline.sendToServer(new PacketGuiData(tag));
    }*/

    /*public void onEntryCopied(int entry)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("copy", entry);
        EnhancedPortals.packetPipeline.sendToServer(new PacketGuiData(tag));
    }*/
}

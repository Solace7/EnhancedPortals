package enhancedportals.client.gui;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.client.gui.elements.ElementScrollAddressBook;
import enhancedportals.inventory.ContainerAddressBook;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.packet.PacketGuiData;
import enhancedportals.network.packet.PacketRequestGui;
import enhancedportals.utility.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiAddressBook extends BaseGui
{

    public static final int CONTAINER_SIZE = 175, CONTAINER_WIDTH = 256;
    GuiButton buttonAddressBookEntry;
    EntityPlayer player;
    ItemAddressBook addressBook;

    public GuiAddressBook(ItemAddressBook b, EntityPlayer player)
    {
        super(new ContainerAddressBook(b, player.inventory), CONTAINER_SIZE);
        texture = new ResourceLocation("enhancedportals", "textures/gui/address_book.png");
        xSize = CONTAINER_WIDTH;
        name = "gui.addressBook";
        setHidePlayerInventory();
        this.player = player;
        this.addressBook = b;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonAddressBookEntry = new GuiButton(0, guiLeft + 78, guiTop + ySize - 27, 100, 20, Localization.get("gui.addressEntry"));
        buttonList.add(buttonAddressBookEntry);

        addElement(new ElementScrollAddressBook(this, addressBook, 7, 28));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(player, Reference.GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_B));
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);

        getFontRenderer().drawString(Localization.get("gui.storedAddresses"), 7, 18, 0x404040);
    }

    public void onEntryEdited(int entry)
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
    }
}

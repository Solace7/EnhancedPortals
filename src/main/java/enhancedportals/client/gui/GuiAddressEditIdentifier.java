package enhancedportals.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.client.gui.elements.ElementGlyphSelector;
import enhancedportals.client.gui.elements.ElementGlyphViewer;
import enhancedportals.client.gui.tabs.TabTip;
import enhancedportals.inventory.ContainerAddressEditIdentifier;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.packet.PacketRequestGui;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.utility.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class GuiAddressEditIdentifier extends BaseGui
{
    public static final int CONTAINER_SIZE = 135;
    ItemAddressBook addressBook;
    EntityPlayer player;
    GuiButton buttonCancel, buttonSave;
    ElementGlyphSelector selector;

    public GuiAddressEditIdentifier(ItemAddressBook b, EntityPlayer player)
    {
        super(new ContainerAddressEditIdentifier(b, player.inventory));
        addressBook = b;
        name = "gui.addressBook";
        this.player = player;
        setHidePlayerInventory();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        int buttonWidth = 80;
        buttonCancel = new GuiButton(0, guiLeft + 7, guiTop + containerSize - 27, buttonWidth, 20, Localization.get("gui.cancel"));
        buttonSave = new GuiButton(1, guiLeft + xSize - buttonWidth - 7, guiTop + containerSize - 27, buttonWidth, 20, Localization.get("gui.save"));
        buttonList.add(buttonCancel);
        buttonList.add(buttonSave);
        addTab(new TabTip(this, "glyphs"));
        selector = new ElementGlyphSelector(this, 7, 52);
        selector.setIdentifierTo(ClientProxy.saveGlyph);
        addElement(selector);
        addElement(new ElementGlyphViewer(this, 7, 29, selector));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (isShiftKeyDown())
        {
            if (button.id == buttonCancel.id)
            {
                selector.reset();
            }
            else if (button.id == buttonSave.id)
            {
                // Random
                Random random = new Random();
                GlyphIdentifier iden = new GlyphIdentifier();

                for (int i = 0; i < (isCtrlKeyDown() ? 9 : random.nextInt(8) + 1); i++)
                {
                    iden.addGlyph(random.nextInt(27));
                }

                selector.setIdentifierTo(iden);
            }
        }
        else if (button.id == buttonCancel.id)
        {
            //todo and this
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(player, Reference.GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_D));
        }
        else if (button.id == buttonSave.id)
        {
            // Save Changes
            ClientProxy.saveGlyph = selector.getGlyphIdentifier();
            //todo fix this
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(player, Reference.GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_D));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        getFontRenderer().drawString(Localization.get("gui.uniqueIdentifier"), 7, 19, 0x404040);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if (isShiftKeyDown())
        {
            buttonCancel.displayString = ChatFormatting.AQUA + Localization.get("gui.clear");
            buttonSave.displayString = (isCtrlKeyDown() ? ChatFormatting.GOLD : ChatFormatting.AQUA) + Localization.get("gui.random");
        }
        else
        {
            buttonCancel.displayString = Localization.get("gui.cancel");
            buttonSave.displayString = Localization.get("gui.save");
        }
    }
}

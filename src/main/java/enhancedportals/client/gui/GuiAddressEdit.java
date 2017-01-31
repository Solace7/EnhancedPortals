package enhancedportals.client.gui;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.inventory.ContainerAddressEdit;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.packet.PacketGuiData;
import enhancedportals.network.packet.PacketRequestGui;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.utility.Localization;
import enhancedportals.utility.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Arrays;

public class GuiAddressEdit extends GuiAddressBookAdd
{
    boolean receivedData = false;

    public GuiAddressEdit(ItemAddressBook b, EntityPlayer player)
    {
        super(new ContainerAddressEdit(b, player.inventory), CONTAINER_SIZE);
        addressBook = b;
        setHidePlayerInventory();
        name = "gui.addressBook";
        allowUserInput = true;
        Keyboard.enableRepeatEvents(true);

        if (ClientProxy.saveTexture == null)
        {
            ClientProxy.saveTexture = new PortalTextureManager();
        }
    }

    @Override
    public void initGui()
    {
        if (ClientProxy.saveName == null)
        {
            ClientProxy.saveName = "";
            ClientProxy.saveGlyph = new GlyphIdentifier();
        }
        else
        {
            receivedData = true;
        }
        LogHelper.debug("data has been received");

        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (receivedData)
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);

            if (mouseX >= guiLeft + 7 && mouseX <= guiLeft + 168 && mouseY >= guiTop + 52 && mouseY < guiTop + 70)
            {
                isEditing = true;
                EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(player, Reference.GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_E));
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException
    {
        if (receivedData)
        {
            super.keyTyped(par1, par2);
        }
        else if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.getKeyCode())
        {
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);

        if (!receivedData) // Just in case the users connection is very slow
        {
            System.out.println("I havent got any data captn");
            drawRect(0, 0, xSize, ySize, 0xCC000000);
            String s = Localization.get("gui.waitingForDataFromServer");
            getFontRenderer().drawSplitString(s, xSize / 2 - getFontRenderer().getStringWidth(s) / 2, ySize / 2 - getFontRenderer().FONT_HEIGHT / 2, xSize, 0xFF0000);
        }

        if (par1 >= guiLeft + 7 && par1 <= guiLeft + 168 && par2 >= guiTop + 52 && par2 < guiTop + 70)
        {
            drawHoveringText(Arrays.asList(new String[]{Localization.get("gui.clickToModify")}), par1 - guiLeft, par2 - guiTop, getFontRenderer());
        }
    }


    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            try
            {
                EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(player, Reference.GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_A));
            }
            catch (Exception e)
            {
                System.out.println("Caught");
                LogHelper.catching(e);
            }
        }
        else if (button.id == 1) // save
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("id", ClientProxy.editingID);
            tag.setString("name", text.getText());
            tag.setString("uid", ClientProxy.saveGlyph.getGlyphString());
            ClientProxy.saveTexture.writeToNBT(tag, "texture");
            try
            {
                EnhancedPortals.packetPipeline.sendToServer(new PacketGuiData(tag));
            }
            catch (NullPointerException e)
            {
                LogHelper.catching(e);
            }
        }
    }

    public void receivedData()
    {
        receivedData = true;
        text.setText(ClientProxy.saveName);
        display.setIdentifier(ClientProxy.saveGlyph);
    }
}

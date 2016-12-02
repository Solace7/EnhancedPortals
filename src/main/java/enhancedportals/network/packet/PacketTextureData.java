package enhancedportals.network.packet;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import enhancedportals.client.gui.GuiAddressEdit;
import enhancedportals.client.gui.GuiDialingEdit;
import enhancedportals.network.ClientProxy;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.utility.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PacketTextureData extends PacketEP
{
    PortalTextureManager ptm;
    String name;
    String glyphs;

    public PacketTextureData()
    {

    }

    public PacketTextureData(String n, String g, PortalTextureManager t)
    {
        ptm = t;
        name = n;
        glyphs = g;
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        NBTTagCompound data = ByteBufUtils.readTag(buffer);
        ptm = new PortalTextureManager();

        if (data.hasKey("Texture"))
        {
            ptm.readFromNBT(data, "Texture");
        }

        name = data.getString("name");
        glyphs = data.getString("glyphs");
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        NBTTagCompound data = new NBTTagCompound();

        if (ptm != null)
        {
            ptm.writeToNBT(data, "Texture");
        }

        data.setString("name", name);
        data.setString("glyphs", glyphs);

        ByteBufUtils.writeTag(buffer, data);

    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        ClientProxy.saveName = name;
        ClientProxy.saveGlyph = new GlyphIdentifier(glyphs);
        ClientProxy.saveTexture = ptm;

        if (FMLClientHandler.instance().getClient().currentScreen.getClass() == GuiDialingEdit.class)
        {
            ((GuiDialingEdit) FMLClientHandler.instance().getClient().currentScreen).receivedData();
        }
        else if (FMLClientHandler.instance().getClient().currentScreen.getClass() == GuiAddressEdit.class)
        {
            try
            {
                LogHelper.debug("Here's a packet");
                ((GuiAddressEdit) FMLClientHandler.instance().getClient().currentScreen).receivedData();
            }
            catch (Exception e)
            {
                LogHelper.catching(e);
            }
        }

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}

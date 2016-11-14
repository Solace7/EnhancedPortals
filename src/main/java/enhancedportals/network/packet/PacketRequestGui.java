package enhancedportals.network.packet;

import enhancedportals.EnhancedPortals;
import enhancedportals.tile.TileEP;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketRequestGui extends PacketEP
{
    int x, y, z, g;
    TileEntity t;

    public PacketRequestGui()
    {

    }

    public PacketRequestGui(TileEP tile, int gui)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        g = gui;
    }

    public PacketRequestGui(EntityPlayer player, int gui)
    {
        //x = (int) player.posX;
        //y = (int) player.posY;
        //z = (int) player.posZ;
        g = gui;
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        g = buffer.readInt();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(g);
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        t = player.worldObj.getTileEntity(x, y, z);

        if (t != null)
        {
            player.openGui(EnhancedPortals.instance, g, t.getWorldObj(), x, y, z);
        }
        else
        {

            player.openGui(EnhancedPortals.instance, g, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}

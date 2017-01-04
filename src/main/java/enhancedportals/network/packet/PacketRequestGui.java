package enhancedportals.network.packet;

import enhancedportals.EnhancedPortals;
import enhancedportals.tile.TileEP;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class PacketRequestGui extends PacketEP
{
    int x, y, z;
    Enum g;
    TileEntity t;

    public PacketRequestGui()
    {

    }

    public PacketRequestGui(TileEP tile, Enum gui)
    {
        x = tile.getPos().getX();
        y = tile.getPos().getY();
        z = tile.getPos().getZ();
        g = gui;
    }

    public PacketRequestGui(EntityPlayer player, Enum gui)
    {
        x = (int) player.posX;
        y = (int) player.posY;
        z = (int) player.posZ;
        g = gui;
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
//        g = buffer.readInt();
        buffer.writeInt(g.ordinal());
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
//        buffer.writeInt(g);
//        buffer.writeBytes(g.name().getBytes());
        buffer.writeInt(g.ordinal());
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        t = player.worldObj.getTileEntity(new BlockPos(x, y, z));

        if (t != null)
        {
            player.openGui(EnhancedPortals.instance, g.ordinal(), t.getWorld(), x, y, z);
        }
        else
        {

            player.openGui(EnhancedPortals.instance, g.ordinal(), Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}

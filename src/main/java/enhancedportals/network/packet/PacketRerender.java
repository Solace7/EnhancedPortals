package enhancedportals.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PacketRerender extends PacketEP
{
    int posX, posY, posZ;

    public PacketRerender()
    {

    }

    public PacketRerender(BlockPos pos)
    {
        posX = pos.getX();
        posY = pos.getY();
        posZ = pos.getZ();
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        posX = buffer.readInt();
        posY = buffer.readInt();
        posZ = buffer.readInt();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        //todo markBlockForUpdate
        player.worldObj.markBlockForUpdate(posX, posY, posZ);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}

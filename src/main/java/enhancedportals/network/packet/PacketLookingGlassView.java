package enhancedportals.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketLookingGlassView implements IMessage
{

    private int viewX, viewY, viewZ, dimension;

    public void PacketCreateLookingGlassView()
    {
    }

    public void PacketCreateLookingGlassView(int viewX, int viewY, int viewZ, int dimension)
    {
        this.viewX = viewX;
        this.viewY = viewY;
        this.viewZ = viewZ;
        this.dimension = dimension;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.viewX = buf.readInt();
        this.viewY = buf.readInt();
        this.viewZ = buf.readInt();
        this.dimension = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.viewX);
        buf.writeInt(this.viewY);
        buf.writeInt(this.viewZ);
        buf.writeInt(this.dimension);
    }

}

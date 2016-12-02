package enhancedportals.tile;

import enhancedportals.utility.DimensionCoordinates;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;

public class TileEP extends TileEntity
{
    public ChunkPos getChunkPos()
    {
        return new ChunkPos(getPos());
    }

    public DimensionCoordinates getDimensionCoordinates()
    {
        return new DimensionCoordinates(getPos(), worldObj.provider.getDimension());
    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }
}

package enhancedportals.tile;

import enhancedportals.utility.DimensionCoordinates;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class TileEP extends TileEntity
{
    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public ChunkCoordinates getChunkCoordinates()
    {
        return new ChunkCoordinates(xCoord, yCoord, zCoord);
    }

    public DimensionCoordinates getDimensionCoordinates()
    {
        return new DimensionCoordinates(getChunkCoordinates(), worldObj.provider.dimensionId);
    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }
}

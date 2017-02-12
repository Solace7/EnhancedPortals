package enhancedportals.utility;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class DimensionCoordinates extends ChunkPos
{
    public int dimension;

    public DimensionCoordinates(int x, int z, int dim)
    {
        super(x, z);
        dimension = dim;
    }

    public DimensionCoordinates(BlockPos pos, int dim)
    {
        super(pos);
        dimension = dim;
    }

    public DimensionCoordinates(NBTTagCompound tag)
    {
        this(tag.getInteger("X"), tag.getInteger("Z"), tag.getInteger("D"));
    }

    public DimensionCoordinates(DimensionCoordinates coord)
    {
        super(coord.chunkXPos, coord.chunkZPos);
        dimension = coord.dimension;
    }

    public BlockPos getBlock(int x, int y, int z)
    {
        WorldServer world = getWorld();

        if (!world.getChunkProvider().chunkExists(this.chunkXPos >> 4, this.chunkZPos >> 4))
        {
            world.getChunkProvider().loadChunk(this.chunkXPos >> 4, this.chunkZPos >> 4);
        }

        return new BlockPos(this.chunkXPos << 4 + x, y, (this.chunkZPos << 4) + z);
    }

    //todo getMeta
    public IBlockState getMetadata(BlockPos pos)
    {
        WorldServer world = getWorld();

        if (!world.getChunkProvider().chunkExists(this.chunkXPos >> 4, this.chunkZPos >> 4))
        {
            world.getChunkProvider().loadChunk(this.chunkXPos >> 4, this.chunkZPos >> 4);
        }

        return world.getBlockState(pos);
    }


    public TileEntity getTileEntity(BlockPos pos)
    {
        WorldServer world = getWorld();

        if (world == null)
        {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);

            if (world == null)
            {
                return null; // How?
            }
        }

        if (!world.getChunkProvider().chunkExists(this.chunkXPos >> 4, this.chunkZPos >> 4))
        {
            world.getChunkProvider().loadChunk(this.chunkXPos >> 4, this.chunkZPos >> 4);
        }

        return world.getTileEntity(pos);
    }

    public WorldServer getWorld()
    {
        WorldServer world = DimensionManager.getWorld(dimension);

        if (world == null)
        {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);

            if (world == null)
            {
                return null; // How?
            }
        }

        return world;
    }

    public DimensionCoordinates offset(EnumFacing facing)
    {
        return new DimensionCoordinates(this.chunkXPos + facing.getFrontOffsetX(), this.chunkZPos + facing.getFrontOffsetZ(), dimension);
    }

    @Override
    public String toString()
    {
        return String.format("WorldCoordinates (X %s, Y %s, Z %s, D %s)", this.chunkXPos, this.chunkZPos, dimension);
    }

    public void writeToNBT(NBTTagCompound t)
    {
        t.setInteger("X", this.chunkXPos);
        t.setInteger("Z", this.chunkZPos);
        t.setInteger("D", dimension);
    }
}

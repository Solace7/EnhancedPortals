package enhancedportals.tile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.utility.DimensionCoordinates;
import enhancedportals.utility.GeneralUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class TileStabilizer extends TileEP implements IEnergyReceiver, IEnergyProvider
{
    ChunkPos mainBlock;
    int rows;
    boolean is3x3 = false;

    @SideOnly(Side.CLIENT)
    public boolean isFormed;

    public TileStabilizer()
    {
        mainBlock = null;
    }


    //todo renable activate, get offset

   /* public boolean activate(EntityPlayer player)
    {
        if (worldObj.isRemote)
        {
            return true;
        }

        TileStabilizerMain main = getMainBlock();

        if (main != null)
        {
            return main.activate(player);
        }
        else if (GeneralUtils.isWrench(player.inventory.getCurrentItem()))
        {
            DimensionCoordinates topLeft = getDimensionCoordinates();

            IBlockState state = this.worldObj.getBlockState(getPos());

            // todo Get the Top-Northwest-most block in the DBS block group.

            // todo Check for valid DBS configurations (3x3, 2x3, 3x2):
            ArrayList<ChunkPos> blocks = checkShapeThreeWide(topLeft); // 3x3
            if (blocks.isEmpty())
            {
                blocks = checkShapeTwoWide(topLeft, true); // Try the 3x2 X axis
                if (blocks.isEmpty())
                {
                    blocks = checkShapeTwoWide(topLeft, false); // Try the 3x2 Z axis before failing
                }
            }
            // blocks wont be empty if we gathered information about the array in the last functions.
            if (!blocks.isEmpty())
            {
                // todo Need to check if there's already a DBS here.
                for (ChunkPos c : blocks)
                {
                    TileEntity tile = worldObj.getTileEntity(getPos());

                    if (tile instanceof TileStabilizer)
                    {
                        if (((TileStabilizer) tile).getMainBlock() != null)
                        {
                            TileStabilizerMain m = ((TileStabilizer) tile).getMainBlock();
                            m.deconstruct();
                        }
                    }
                    else if (tile instanceof TileStabilizerMain)
                    {
                        ((TileStabilizerMain) tile).deconstruct();
                    }
                }

                // todo Otherwise start marking the blocks for the DBS block.
                for (ChunkPos c : blocks)
                {
//todo                    worldObj.setBlock(getPos(), BlockStabilizer.instance, 0, 2);
//                    final IBlockState state = getWorld().getBlockState(getPos());
                    worldObj.setBlockState(getPos(), state, 3);

                    TileEntity tile = worldObj.getTileEntity(getPos());

                    if (tile instanceof TileStabilizer)
                    {
                        TileStabilizer t = (TileStabilizer) tile;
                        t.mainBlock = topLeft;
//                        worldObj.markBlockForUpdate(t.xCoord, t.yCoord, t.zCoord);
                        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
                    }
                }

                // todo Create a BlockStabilizer.
//                worldObj.setBlock(topLeft.posX, topLeft.posY, topLeft.posZ, BlockStabilizer.instance, 1, 3);
//                final IBlockState state = getWorld().getBlockState(getPos());
                worldObj.setBlockState(getPos(), state);

                TileEntity tile = topLeft.getTileEntity(getPos());

                // Check if everything went successful and set up.
                if (tile instanceof TileStabilizerMain)
                {
                    ((TileStabilizerMain) tile).setData(blocks, rows, is3x3);
                    return true;
                }
            }
        }
        return false;
    }*/

    public void breakBlock()
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return;
        }

        main.deconstruct();
    }

    private boolean isStabilizerAt(BlockPos pos) {
        if (this.worldObj ==null) {
            return false;
        } else {
            Block block = this.worldObj.getBlockState(pos).getBlock();
            return block instanceof BlockStabilizer;
        }
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from)
    {
        return true;
    }

    //todo multiblock

    ArrayList<ChunkPos> checkShapeThreeWide(DimensionCoordinates topLeft)
    {
        ArrayList<ChunkPos> blocks = new ArrayList<ChunkPos>();
        ChunkPos heightChecker = new ChunkPos(topLeft.getBlock(getPos().getX(), getPos().getY(), getPos().getZ()));
        rows = 0;

//        while (worldObj.getBlock(heightChecker.posX, heightChecker.posY, heightChecker.posZ) == BlockStabilizer.instance)
/*          while (worldObj.getBlockState(getPos()) == BlockStabilizer.instance)
        {
            heightChecker.get--;
            rows++;
        }

        if (rows < 2)
        {
            rows = 0;
            return new ArrayList<ChunkPos>();
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                for (int k = 0; k < rows; k++)
                {
                    if (worldObj.getBlockState(getPos()).getBlock(topLeft.posX + i, topLeft.posY - k, topLeft.posZ + j) != BlockStabilizer.instance)
                    {
                        return new ArrayList<ChunkPos>();
                    }

                    blocks.add(new ChunkPos(topLeft.getXStart() + i, topLeft.posY - k, topLeft.getZStart() + j));
                }
            }
        }*/

        is3x3 = true;
        return blocks;
    }

/*    ArrayList<ChunkPos> checkShapeTwoWide(DimensionCoordinates topLeft, boolean isX)
    {
        ArrayList<ChunkPos> blocks = new ArrayList<ChunkPos>();
        ChunkPos heightChecker = new ChunkPos(topLeft);
        rows = 0;

        while (worldObj.getBlock(heightChecker.posX, heightChecker.posY, heightChecker.posZ) == BlockStabilizer.instance)
        {
            heightChecker.posY--;
            rows++;
        }

        if (rows < 2)
        {
            rows = 0;
            return new ArrayList<ChunkPos>();
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                for (int k = 0; k < rows; k++)
                {
                    if (worldObj.getBlock(topLeft.posX + (isX ? i : j), topLeft.posY - k, topLeft.posZ + (!isX ? i : j)) != BlockStabilizer.instance)
                    {
                        return new ArrayList<ChunkPos>();
                    }

                    blocks.add(new ChunkPos(topLeft.posX + (isX ? i : j), topLeft.posY - k, topLeft.posZ + (!isX ? i : j)));
                }
            }
        }

        is3x3 = false;
        return blocks;
    }*/

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate)
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return 0;
        }

        return main.extractEnergy(from, maxExtract, simulate);
    }

    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("formed", mainBlock != null);

//        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
        return new SPacketUpdateTileEntity();
    }

    @Override
    public int getEnergyStored(EnumFacing from)
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return 0;
        }

        return main.getEnergyStored(from);
    }

    /***
     * Gets the block that does all the processing for this multiblock. If that block is self, will return self.
     */
    public TileStabilizerMain getMainBlock()
    {
        if (mainBlock != null)
        {
            TileEntity tile = worldObj.getTileEntity(getPos());

            if (tile != null && tile instanceof TileStabilizerMain)
            {
                return (TileStabilizerMain) tile;
            }
        }

        return null;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from)
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return 0;
        }

        return main.getMaxEnergyStored(from);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound tag = pkt.getNbtCompound();
        isFormed = tag.getBoolean("formed");
//   todo     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        mainBlock = GeneralUtils.loadChunkCoord(tag, "mainBlock");
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return 0;
        }

        return main.receiveEnergy(from, maxReceive, simulate);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        GeneralUtils.saveChunkCoord(tag, mainBlock, "mainBlock");
        return tag;
    }
}
package enhancedportals.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public abstract class TilePortalPart extends TileEP
{
    ChunkPos portalController;
    TileController cachedController;

    public boolean activate(EntityPlayer player, ItemStack stack, BlockPos blockPos)
    {
        return false;
    }

    public abstract void addDataToPacket(NBTTagCompound tag);

    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.connectionTerminate();
        }
    }

    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();

        if (portalController != null)
        {
            tag.setInteger("PortalControllerX", portalController.chunkXPos);
            tag.setInteger("PortalControllerZ", portalController.chunkZPos);
        }

        addDataToPacket(tag);
        return new SPacketUpdateTileEntity(BlockPos.ORIGIN, 0, tag);
    }

    public TileController getPortalController()
    {
        if (cachedController != null)
        {
            return cachedController;
        }

        TileEntity tile = portalController == null ? null : worldObj.getTileEntity(this.getPos());

        if (tile != null && tile instanceof TileController)
        {
            cachedController = (TileController) tile;
            return cachedController;
        }

        return null;
    }

    /*
     * Called when this block is placed in the world.
     *
     * @param entity
     * @param stack

    public void onBlockPlaced(EntityLivingBase entity, ItemStack stack, EntityPlayer player)
    {
        for (int i = 0; i < 6; i++)
        {
            ChunkPos c = GeneralUtils.offset(getChunkCoordinates(), EnumFacing.getFront(i));
            TileEntity tile = worldObj.getTileEntity(c.posX, c.posY, c.posZ);

            if (tile != null && tile instanceof TilePortalPart)
            {
                ((TilePortalPart) tile).onNeighborPlaced(entity, xCoord, yCoord, zCoord);
            }
        }
    }*/

    public abstract void onDataPacket(NBTTagCompound tag);

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound tag = pkt.getNbtCompound();

        portalController = null;
        cachedController = null;

        if (tag.hasKey("PortalControllerX"))
        {
            portalController = new ChunkPos(tag.getInteger("PortalControllerX"), tag.getInteger("PortalControllerZ"));
        }

        onDataPacket(tag);
        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        //todo mark for update
    }

    /**
     * Called when a portal part gets placed next to this one. Is used to notify the Portal Controller to dismantle the structure.
     *
     * @param x
     * @param y
     * @param z
     */
    public void onNeighborPlaced(EntityLivingBase entity, int x, int y, int z)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.deconstruct();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Controller"))
        {
            NBTTagCompound controller = compound.getCompoundTag("Controller");
            portalController = new ChunkPos(controller.getInteger("X"), controller.getInteger("Z"));
        }
    }

    /**
     * Sets the Portal Controller to the specified coordinates, and sends an update packet.
     *
     * @param c
     */
    public void setPortalController(ChunkPos c)
    {
        portalController = c;
        cachedController = null;
        markDirty();
        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        //todo mark for update
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (portalController != null)
        {
            NBTTagCompound controller = new NBTTagCompound();
            controller.setInteger("X", portalController.chunkXPos);
            controller.setInteger("Z", portalController.chunkZPos);
            compound.setTag("Controller", controller);
        }

        return compound;
    }
}

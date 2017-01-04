package enhancedportals.inventory;

import enhancedportals.client.gui.BaseGui;
import enhancedportals.client.gui.GuiTransferFluid;
import enhancedportals.tile.TileTransferFluid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerTransferFluid extends BaseContainer
{
    TileTransferFluid fluid;
    byte wasSending = -1;
    int fluidID = -1, fluidAmt = -1;

    public ContainerTransferFluid(TileTransferFluid f, InventoryPlayer p)
    {
        super(null, p, GuiTransferFluid.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        fluid = f;
    }

   /* @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        byte isSending = (byte) (fluid.isSending ? 1 : 0);
        //todo getFluid

//        int fID = fluid.tank.getFluid() != null ? fluid.tank.getFluid().getFluidID() : -1, fAmt = fluid.tank.getFluidAmount();
        int fID = fluid.tank.getFluid() != null ? fluid.tank.getFluid() : ;
        int fAmt = fluid.tank.getFluidAmount();

        for (int i = 0; i < this.listeners.size(); i++)
        {
            IContainerListener iContainerListener = (IContainerListener)this.listeners.get(i);

            if (wasSending != isSending)
            {
                iContainerListener.sendProgressBarUpdate(this, 0, isSending);
            }

            if (fluidID != fID)
            {
                iContainerListener.sendProgressBarUpdate(this, 1, fID);
            }

            if (fluidAmt != fAmt)
            {
                iContainerListener.sendProgressBarUpdate(this, 2, fAmt);
            }

            wasSending = isSending;
            fluidID = fID;
            fluidAmt = fAmt;
        }
    }*/

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        fluid.isSending = !fluid.isSending;
    }

   /*
    //todo fluid transfer
    @Override
    public void updateProgressBar(Fluid fluid, int val)
    {
        if (id == 0)
        {
            fluid.isSending = val == 1;
        }
        else if (id == 1)
        {
            if (val == -1)
            {
                fluid.tank.setFluid(null);
            }
            else
            {
                fluid.tank.setFluid(new FluidStack(id, val));
            }
        }
        else if (id == 2)
        {
            FluidStack f = fluid.tank.getFluid();

            if (f != null)
            {
                f.amount = val;
                fluid.tank.setFluid(f);
            }
        }
    }*/
}

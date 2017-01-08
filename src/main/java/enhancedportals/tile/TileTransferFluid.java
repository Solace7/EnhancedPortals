package enhancedportals.tile;

import enhancedportals.Reference;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.utility.GeneralUtils;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;

@InterfaceList(value = {@Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})
public class TileTransferFluid extends TileFrameTransfer implements IFluidHandler, SimpleComponent
{
    public FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);

    int tickTimer = 20, time = 0;

    IFluidHandler[] handlers = new IFluidHandler[6];

    boolean cached = false;

    byte outputTracker = 0;

//    @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        if (player.isSneaking())
        {
            return false;
        }

        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (GeneralUtils.isWrench(stack))
            {
                GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_TRANFER.TRANSFER_FLUID.ordinal());
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid)
    {
        return true;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }

        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
    {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public void onNeighborChanged()
    {
        updateFluidHandlers();
    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        if (tank.getFluid() != null)
        {
            buffer.writeBoolean(false);
            //todo write fluid id
//            buffer.writeInt(tank.getFluid().getFluidID());
            buffer.writeInt(tank.getFluidAmount());
        }
        else
        {
            buffer.writeBoolean(false);
        }
    }

    @Override
    public void packetGuiUse(ByteBuf buffer)
    {
        if (buffer.readBoolean())
        {
            tank.setFluid(new FluidStack(FluidRegistry.getFluid(buffer.readInt()), buffer.readInt()));
        }
        else
        {
            tank.setFluid(null);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        tank.writeToNBT(tag);
    }

    void transferFluid(int side)
    {
        if (handlers[side] == null)
        {
            return;
        }

        tank.drain(handlers[side].fill(EnumFacing.getFront(side).getOpposite(), tank.getFluid(), true), true);
    }

    public void update()
    {
        super.update();

        if (!worldObj.isRemote)
        {
            if (isSending)
            {
                if (time >= tickTimer)
                {
                    time = 0;

                    TileController controller = getPortalController();

                    if (controller != null && controller.isPortalActive() && tank.getFluidAmount() > 0)
                    {
                        TileController exitController = (TileController) controller.getDestinationLocation().getTileEntity(getPos());

                        if (exitController != null)
                        {
                            for (ChunkPos c : exitController.getTransferFluids())
                            {
                                TileEntity tile = exitController.getWorld().getTileEntity(getPos());

                                if (tile != null && tile instanceof TileTransferFluid)
                                {
                                    TileTransferFluid fluid = (TileTransferFluid) tile;

                                    if (!fluid.isSending)
                                    {
                                        if (fluid.fill(null, tank.getFluid(), false) > 0)
                                        {
                                            tank.drain(fluid.fill(null, tank.getFluid(), true), true);
                                        }
                                    }
                                }

                                if (tank.getFluidAmount() == 0)
                                {
                                    break;
                                }
                            }
                        }
                    }
                }

                time++;
            }
            else
            {
                if (!cached)
                {
                    updateFluidHandlers();
                }

                for (int i = outputTracker; i < 6 && tank.getFluidAmount() > 0; i++)
                {
                    transferFluid(i);
                }

                outputTracker++;
                outputTracker = (byte) (outputTracker % 6);
            }
        }
    }

    void updateFluidHandlers()
    {
        for (int i = 0; i < 6; i++)
        {
            ChunkPos c = GeneralUtils.offset(getChunkPos(), EnumFacing.getFront(i));
            TileEntity tile = worldObj.getTileEntity(getPos());

            if (tile != null && tile instanceof IFluidHandler)
            {
                IFluidHandler fluid = (IFluidHandler) tile;

                if (fluid.getTankInfo(EnumFacing.getFront(i).getOpposite()) != null)
                {
                    handlers[i] = fluid;
                }
                else
                {
                    handlers[i] = null;
                }
            }
            else
            {
                handlers[i] = null;
            }
        }

        cached = true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tank.readFromNBT(tag);

        return tag;
    }
    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_transfer_fluid";
    }

    @Callback(direct = true, limit = 1, doc = "function():table -- Get a description of the fluid stored inside the module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getFluid(Context context, Arguments args)
    {
        return new Object[]{tank.getInfo()};
    }

    @Callback(direct = true, doc = "function():boolean -- Returns true if the module is set to send fluids.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] isSending(Context context, Arguments args)
    {
        return new Object[]{isSending};
    }
}

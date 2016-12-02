package enhancedportals.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import enhancedportals.Reference;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.utility.GeneralUtils;
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
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;

@InterfaceList(value = {@Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = Reference.Dependencies.MODID_OPENCOMPUTERS), @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})


public class TileTransferEnergy extends TileFrameTransfer implements IEnergyHandler, IPeripheral, SimpleComponent
{
    public final EnergyStorage storage = new EnergyStorage(16000);
    // public final PowerHandler mjHandler;

    int tickTimer = 20, time = 0;

    IEnergyHandler[] handlers = new IEnergyHandler[6];

    boolean cached = false;

    byte outputTracker = 0;

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
                GuiHandler.openGui(player, this, GuiHandler.TRANSFER_ENERGY);
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, GuiHandler.TEXTURE_A);
                return true;
            }
        }

        return false;
    }


    //OpenComputers Methods

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public void attach(IComputerAccess computer)
    {

    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception
    {
        if (method == 0)
        {
            return new Object[]{storage.getEnergyStored()};
        }
        else if (method == 1)
        {
            return new Object[]{storage.getEnergyStored() == storage.getMaxEnergyStored()};
        }
        else if (method == 2)
        {
            return new Object[]{storage.getEnergyStored() == 0};
        }
        else if (method == 3)
        {
            return new Object[]{isSending};
        }

        return null;
    }

    @Override
    public void onNeighborChanged()
    {
        updateEnergyHandlers();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    //todo maybe override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    void transferEnergy(int side)
    {
        if (handlers[side] == null)
        {
            return;
        }

        storage.extractEnergy(handlers[side].receiveEnergy(EnumFacing.getFront(side).getOpposite(), storage.getEnergyStored(), false), false);
    }

    void updateEnergyHandlers()
    {
        for (int i = 0; i < 6; i++)
        {
            ChunkPos c = GeneralUtils.offset(getChunkPos(), EnumFacing.getFront(i));
            TileEntity tile = worldObj.getTileEntity(getPos());

            if (tile != null && tile instanceof IEnergyHandler)
            {
                IEnergyHandler energy = (IEnergyHandler) tile;

                if (energy.canConnectEnergy(EnumFacing.getFront(i).getOpposite()))
                {
                    handlers[i] = energy;
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

    public void update()
    {
//        super.update();

        if (!worldObj.isRemote)
        {
            if (isSending)
            {
                if (time >= tickTimer)
                {
                    time = 0;

                    TileController controller = getPortalController();

                    if (controller != null && controller.isPortalActive() && storage.getEnergyStored() > 0)
                    {
                        TileController exitController = (TileController) controller.getDestinationLocation().getTileEntity(getPos());

                        if (exitController != null)
                        {
                            for (ChunkPos c : exitController.getTransferEnergy())
                            {
                                TileEntity tile = exitController.getWorld().getTileEntity(getPos());

                                if (tile != null && tile instanceof TileTransferEnergy)
                                {
                                    TileTransferEnergy energy = (TileTransferEnergy) tile;

                                    if (!energy.isSending)
                                    {
                                        if (energy.receiveEnergy(null, storage.getEnergyStored(), true) > 0)
                                        {
                                            storage.extractEnergy(energy.receiveEnergy(null, storage.getEnergyStored(), false), false);
                                        }
                                    }
                                }

                                if (storage.getEnergyStored() == 0)
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
                    updateEnergyHandlers();
                }

                for (int i = outputTracker; i < 6 && storage.getEnergyStored() > 0; i++)
                {
                    transferEnergy(i);
                }

                outputTracker++;
                outputTracker = (byte) (outputTracker % 6);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from)
    {
        return true;
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public void detach(IComputerAccess computer)
    {

    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public boolean equals(IPeripheral other)
    {
        return other == this;
    }

    //todo maybe override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_transfer_energy";
    }

    @Callback(direct = true, doc = "function():number -- Returns the amount of energy stored inside the module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getEnergy(Context context, Arguments args)
    {
        return new Object[]{storage.getEnergyStored()};
    }

    @Override
    public int getEnergyStored(EnumFacing from)
    {
        return storage.getEnergyStored();
    }

    @Callback(direct = true, doc = "function():number -- Get the maximum amount of energy stored inside the module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getMaxEnergy(Context context, Arguments args)
    {
        return new Object[]{storage.getMaxEnergyStored()};
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from)
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String[] getMethodNames()
    {
        return new String[]{"getEnergyStored", "isFull", "isEmpty", "isSending"};
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getType()
    {
        return "ep_transfer_energy";
    }

    @Callback(direct = true, doc = "function():boolean -- Returns true if the module is set to send energy.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] isSending(Context context, Arguments args)
    {
        return new Object[]{isSending};
    }

}

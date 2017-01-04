package enhancedportals.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
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

@InterfaceList(value = {@Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})


public class TileTransferEnergy extends TileFrameTransfer implements IEnergyProvider, IEnergyReceiver, SimpleComponent
{
    public final EnergyStorage storage = new EnergyStorage(16000);

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
                GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_TRANFER.TRANSFER_ENERGY.ordinal());
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
    @Override
    public boolean canConnectEnergy(EnumFacing from)
    {
        return true;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from)
    {
        return storage.getMaxEnergyStored();
    }

    //todo transf energy
 /*   void transferEnergy(int side)
    {
        if (handlers[side] == null)
        {
            return;
        }

        storage.extractEnergy(handlers[side].receiveEnergy(EnumFacing.getFront(side).getOpposite(), storage.getEnergyStored(), false), false);
    }*/

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
       super.update();

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
             /*   {
                    transferEnergy(i);
                }*/

                outputTracker++;
                outputTracker = (byte) (outputTracker % 6);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);

        return nbt;
    }

    /////////////////////////
    //OpenComputers Methods//
    /////////////////////////


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

    @Callback(direct = true, doc = "function():number -- Get the maximum amount of energy stored inside the module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getMaxEnergy(Context context, Arguments args)
    {
        return new Object[]{storage.getMaxEnergyStored()};
    }

    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String[] getMethodNames()
    {
        return new String[]{"getEnergyStored", "isFull", "isEmpty", "isSending"};
    }

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

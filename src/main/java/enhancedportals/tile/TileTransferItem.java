package enhancedportals.tile;

import enhancedportals.Reference;
import enhancedportals.network.GuiHandler;
import enhancedportals.utility.GeneralUtils;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nullable;

@InterfaceList(value = {@Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})
public class TileTransferItem extends TileFrameTransfer implements IInventory, SimpleComponent
{
    ItemStack stack;

    int tickTimer = 20, time = 0;

    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        if (player.isSneaking())
        {
            return false;
        }

        TileController controller = getPortalController();

        if (GeneralUtils.isWrench(stack) && controller != null && controller.isFinalized())
        {
            GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_TRANFER.TRANSFER_ITEM.ordinal());
            return true;
        }

        return false;
    }

//    @Override
    public String getInventoryName()
    {
        return "tile.frame.item.name";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack stack = getStackInSlot(i);

        if (stack != null)
        {
            if (stack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            }
            else
            {
                stack = stack.splitStack(j);

                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }

        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return null;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        if (stack != null)
        {
            buffer.writeBoolean(true);
            ByteBufUtils.writeItemStack(buffer, stack);
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
            stack = ByteBufUtils.readItemStack(buffer);
        }
        else
        {
            stack = null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        stack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack"));
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        stack = itemstack;
    }

   @Override
    public void update()
    {
//        super.updateEntity();

        if (!worldObj.isRemote)
        {
            if (isSending)
            {
                if (time >= tickTimer)
                {
                    time = 0;

                    TileController controller = getPortalController();

                    if (controller != null && controller.isPortalActive() && stack != null)
                    {
                        TileController exitController = (TileController) controller.getDestinationLocation().getTileEntity(getPos());

                        if (exitController != null)
                        {
                            for (ChunkPos c : exitController.getTransferItems())
                            {
                                TileEntity tile = exitController.getWorld().getTileEntity(getPos());

                                if (tile != null && tile instanceof TileTransferItem)
                                {
                                    TileTransferItem item = (TileTransferItem) tile;

                                    if (!item.isSending)
                                    {
                                        if (item.getStackInSlot(0) == null)
                                        {
                                            item.setInventorySlotContents(0, stack);
                                            item.markDirty();
                                            stack = null;
                                            markDirty();
                                        }
                                        else if (item.getStackInSlot(0).getItem() == stack.getItem())
                                        {
                                            int amount = 0;

                                            if (item.getStackInSlot(0).stackSize + stack.stackSize <= stack.getMaxStackSize())
                                            {
                                                amount = stack.stackSize;
                                            }
                                            else
                                            {
                                                amount = stack.stackSize - (item.getStackInSlot(0).stackSize + stack.stackSize - 64);
                                            }

                                            if (amount <= 0)
                                            {
                                                continue;
                                            }

                                            item.getStackInSlot(0).stackSize += amount;
                                            item.markDirty();

                                            if (amount == stack.stackSize)
                                            {
                                                stack = null;
                                            }
                                            else
                                            {
                                                stack.stackSize -= amount;
                                            }

                                            markDirty();
                                        }
                                    }
                                }

                                if (stack == null)
                                {
                                    break;
                                }
                            }
                        }
                    }
                }

                time++;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagCompound st = new NBTTagCompound();

        if (stack != null)
        {
            stack.writeToNBT(st);
        }

        tag.setTag("stack", st);

        return tag;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return null;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return stack;
    }

//    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return stack;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return true;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }

//    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_transfer_item";
    }

    @Callback(direct = true, doc = "function():table -- Returns a description of the item stored in this module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getStack(Context context, Arguments args)
    {
        return new Object[]{stack.copy()};
    }

    @Callback(direct = true, doc = "function():boolean -- Return whether there is an item stored in this module.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] hasStack(Context context, Arguments args)
    {
        return new Object[]{stack != null};
    }


    @Callback(direct = true, doc = "function():boolean -- Returns true if the module is set to send item.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] isSending(Context context, Arguments args)
    {
        return new Object[]{isSending};
    }

}

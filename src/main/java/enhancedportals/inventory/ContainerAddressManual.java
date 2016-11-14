package enhancedportals.inventory;

import enhancedportals.item.ItemAddressBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerAddressManual extends BaseContainer
{
    ItemAddressBook addressBook;

    public ContainerAddressManual(ItemAddressBook b, InventoryPlayer p)
    {
        super(null, p);
        hideInventorySlots();
        addressBook = b;
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {

    }
}

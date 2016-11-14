package enhancedportals.inventory;

import enhancedportals.item.ItemAddressBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerAddressEditIdentifier extends BaseContainer
{
    ItemAddressBook addressBook;

    public ContainerAddressEditIdentifier(ItemAddressBook b, InventoryPlayer p)
    {
        super(null, p);
        addressBook = b;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {

    }


}

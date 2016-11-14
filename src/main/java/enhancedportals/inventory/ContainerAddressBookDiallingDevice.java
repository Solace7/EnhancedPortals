package enhancedportals.inventory;

import enhancedportals.item.ItemAddressBook;
import enhancedportals.tile.TileDialingDevice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class ContainerAddressBookDiallingDevice extends BaseContainer
{
    TileDialingDevice dial;
    ItemAddressBook addressBook;
    ArrayList<String> dialList = new ArrayList<String>();
    ArrayList<String> addressList = new ArrayList<String>();

    public ContainerAddressBookDiallingDevice(TileDialingDevice d, ItemAddressBook ab, InventoryPlayer player)
    {
        super(null, player);
        dial = d;
        addressBook = ab;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {

    }

    void updateAll()
    {

    }
}

package enhancedportals.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemEP extends Item
{

    private final String name = "itemName";

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }

    public void writeToNBT(ItemStack itemStack)
    {

    }

    public void readFromNBT(ItemStack itemStack)
    {

    }

    public String getName() {

        return name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItem)
    {
        subItem.add(new ItemStack(this, 1));
    }

}

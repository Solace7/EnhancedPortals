package enhancedportals.item;

import enhancedportals.Reference;
import enhancedportals.utility.Localization;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPortalFrame extends ItemBlock
{
    public static String[] unlocalizedName = new String[]{"frame", "controller", "redstone", "network_interface", "dial_device", "upgrade", "fluid", "item", "energy"};

    public ItemPortalFrame(String n, Block block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(n);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int damage = stack.getItemDamage();

        if (damage > 0)
        {
            list.add(Localization.get("block.portalFramePart"));
        }
    }

    @Override
    public int getMetadata(int metadata) {

        return metadata;
    }

    /*@SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
    {
        for (int i = 0; i < BlockFrame.FRAME_TYPES; i++)
        {
            list.add(new ItemStack(par1, 1, i));
        }
    }*/

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + unlocalizedName[stack.getItemDamage()];
    }
}

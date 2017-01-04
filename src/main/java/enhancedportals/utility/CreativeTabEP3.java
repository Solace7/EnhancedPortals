package enhancedportals.utility;

import enhancedportals.EnhancedPortals;
import enhancedportals.item.ItemLocationCard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabEP3 extends CreativeTabs
{
    public CreativeTabEP3()
    {
        super(EnhancedPortals.MOD_ID);
    }

    @Override
    public Item getTabIconItem()
    {
        return ItemLocationCard.instance;
    }
}

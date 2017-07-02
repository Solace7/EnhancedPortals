package enhancedportals.item;

import enhancedportals.network.CommonProxy;
import net.minecraft.item.Item;

public class ItemDiamondNugget extends Item
{
    public static ItemDiamondNugget instance;

    public ItemDiamondNugget(String n)
    {
        setMaxStackSize(64);
        setCreativeTab(CommonProxy.creativeTab);
        instance = this;
        setRegistryName(n);
        setUnlocalizedName(getRegistryName().toString());
    }
}

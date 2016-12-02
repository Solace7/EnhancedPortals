package enhancedportals.item;

import enhancedportals.network.CommonProxy;
import net.minecraft.item.Item;

public class ItemDiamondNugget extends Item
{
    public static ItemDiamondNugget instance;

    public ItemDiamondNugget()
    {
        setUnlocalizedName("diamondNugget");
        setMaxStackSize(64);
        setCreativeTab(CommonProxy.creativeTab);
        instance = this;
        setRegistryName("diamondNugget");
    }
}

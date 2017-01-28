package enhancedportals.item;

import enhancedportals.network.CommonProxy;

public class ItemBlankUpgrade extends ItemEP
{
    public static ItemBlankUpgrade instance;

    public ItemBlankUpgrade(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName(n);
        setRegistryName(n);
    }

}

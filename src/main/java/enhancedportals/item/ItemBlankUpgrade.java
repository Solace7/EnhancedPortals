package enhancedportals.item;

import enhancedportals.network.CommonProxy;

public class ItemBlankUpgrade extends ItemEP
{
    public static ItemBlankUpgrade instance;

    public ItemBlankUpgrade()
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("blank_upgrade");
        setRegistryName("blank_upgrade");
    }

}

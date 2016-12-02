package enhancedportals.item;

import enhancedportals.network.CommonProxy;

public class ItemBlankPortalModule extends ItemEP
{
    public static ItemBlankPortalModule instance;

    public ItemBlankPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName(n);
        setRegistryName("blank_portal_module");
    }
    
}

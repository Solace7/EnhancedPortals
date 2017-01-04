package enhancedportals.item;

import enhancedportals.network.CommonProxy;

public class ItemBlankPortalModule extends ItemEP
{
    public static ItemBlankPortalModule instance;

    public ItemBlankPortalModule()
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("blank_portal_module");
        setRegistryName("blank_portal_module");
    }
    
}

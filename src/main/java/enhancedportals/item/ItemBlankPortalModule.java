package enhancedportals.item;

import enhancedportals.Reference;
import enhancedportals.network.CommonProxy;

public class ItemBlankPortalModule extends ItemEP
{
    public static ItemBlankPortalModule instance;

    public ItemBlankPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
    }
    
}

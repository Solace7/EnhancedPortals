package enhancedportals.item;

import enhancedportals.Reference;
import enhancedportals.network.CommonProxy;

public class ItemBlankUpgrade extends ItemEP
{
    public static ItemBlankUpgrade instance;

    public ItemBlankUpgrade(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(n);
    }

}

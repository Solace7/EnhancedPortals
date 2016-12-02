package enhancedportals.registration;

import enhancedportals.guidebook.WormholeTunnelManual;
import enhancedportals.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class registerItems

{
    public static Item itemWrench;
    public static Item itemNanobrush;
    public static Item itemGlasses;
    public static Item itemLocationCard;
    public static Item itemPortalModule;
    public static Item itemUpgrade;
    public static Item itemBlankPortalMod;
    public static Item itemBlankUpgrade;
    public static Item itemAddressBook;
    public static Item itemDiamondNugget;

    public static final void init()
    {
        //todo Remove String n parameter from all items
        GameRegistry.register(itemWrench = new ItemWrench());
        GameRegistry.register(itemNanobrush = new ItemNanobrush());
        GameRegistry.register(itemGlasses = new ItemGlasses());
        GameRegistry.register(itemLocationCard = new ItemLocationCard("location_card"));
        GameRegistry.register(itemPortalModule = new ItemPortalModule("portal_module"));
        GameRegistry.register(itemUpgrade = new ItemUpgrade("upgrade"));
        GameRegistry.register(itemBlankPortalMod = new ItemBlankPortalModule("blank_portal_module"));
        GameRegistry.register(itemBlankUpgrade = new ItemBlankUpgrade());
        GameRegistry.register(itemAddressBook = new ItemAddressBook());
        GameRegistry.register(itemDiamondNugget = new ItemDiamondNugget());

        GameRegistry.register(WormholeTunnelManual.epManual);

        /*if (CONFIG_RECIPES_TE && Loader.isModLoaded(Reference.Dependencies.MODID_THERMALEXPANSION))
        {
            ThermalExpansion.registerItems();
            ThermalExpansion.registerMachineRecipes();
        }*/
    }


}

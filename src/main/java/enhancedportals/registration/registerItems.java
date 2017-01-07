package enhancedportals.registration;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import enhancedportals.Reference;
import enhancedportals.crafting.ThermalExpansion;
import enhancedportals.item.*;
import enhancedportals.utility.LogHelper;

import static enhancedportals.utility.ConfigurationHandler.CONFIG_RECIPES_TE;

public class registerItems

{

    public static final void init()
    {

        GameRegistry.registerItem(new ItemWrench("wrench"), "wrench");
        GameRegistry.registerItem(new ItemNanobrush("nanobrush"), "nanobrush");
        GameRegistry.registerItem(new ItemGlasses("glasses"), "glasses");
        GameRegistry.registerItem(new ItemLocationCard("location_card"), "location_card");
        GameRegistry.registerItem(new ItemPortalModule("portal_module"), "portal_module");
        GameRegistry.registerItem(new ItemUpgrade("upgrade"), "upgrade");
        GameRegistry.registerItem(new ItemBlankPortalModule("blank_portal_module"), "blank_portal_module");
        GameRegistry.registerItem(new ItemBlankUpgrade("blank_upgrade"), "blank_upgrade");
        GameRegistry.registerItem(new ItemAddressBook("address_book"), "address_book");

        if (CONFIG_RECIPES_TE && Loader.isModLoaded(Reference.Dependencies.MODID_THERMALEXPANSION))
        {
            LogHelper.info("Debugging: Thermal Expansion Enabled");
            ThermalExpansion.registerItems();
            ThermalExpansion.registerMachineRecipes();
        }
    }


}

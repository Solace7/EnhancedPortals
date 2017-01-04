package enhancedportals.registration;

import enhancedportals.block.BlockFrame;
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
    public static Item itemBlankPortalModule;
    public static Item itemBlankUpgrade;
    public static Item itemAddressBook;
    public static Item itemDiamondNugget;
    public static Item itemFrame;
    public static Item itemStabilizer;

    public static final void preinit()
    {
        GameRegistry.register(itemWrench = new ItemWrench());
        GameRegistry.register(itemNanobrush = new ItemNanobrush());
        GameRegistry.register(itemGlasses = new ItemGlasses());
        GameRegistry.register(itemLocationCard = new ItemLocationCard());
        GameRegistry.register(itemPortalModule = new ItemPortalModule());
        GameRegistry.register(itemUpgrade = new ItemUpgrade());
        GameRegistry.register(itemBlankPortalModule = new ItemBlankPortalModule());
        GameRegistry.register(itemBlankUpgrade = new ItemBlankUpgrade());
        GameRegistry.register(itemAddressBook = new ItemAddressBook());
        GameRegistry.register(itemDiamondNugget  = new ItemDiamondNugget());
        GameRegistry.register(itemFrame = new ItemPortalFrame(new BlockFrame()));
        GameRegistry.register(itemStabilizer = new ItemStabilizer());

    }


}

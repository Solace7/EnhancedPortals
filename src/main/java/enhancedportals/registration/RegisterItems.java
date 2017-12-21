package enhancedportals.registration;

import enhancedportals.block.BlockFrame;
import enhancedportals.client.render.items.ItemRenderRegister;
import enhancedportals.item.*;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterItems

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

    public static void register(Item item, int metadata, String name) {
        GameRegistry.register(item);
    }

    public static void preinit()
    {
        itemWrench = GameRegistry.register(new ItemWrench("wrench"));
        ItemRenderRegister.registerRender(itemWrench);

        itemNanobrush = GameRegistry.register(new ItemNanobrush("nanobrush"));
        ItemRenderRegister.registerRender(itemNanobrush);

        itemGlasses = GameRegistry.register(new ItemGlasses("glasses"));
        ItemRenderRegister.registerRender(itemGlasses);

        itemLocationCard = GameRegistry.register(new ItemLocationCard("location_card"));
        ItemRenderRegister.registerRender(itemLocationCard);

        itemBlankPortalModule = GameRegistry.register(new ItemBlankPortalModule("blank_portal_module"));
        ItemRenderRegister.registerRender(itemBlankPortalModule);

        itemBlankUpgrade = GameRegistry.register(new ItemBlankUpgrade("blank_upgrade"));
        ItemRenderRegister.registerRender(itemBlankUpgrade);

        itemAddressBook = GameRegistry.register(new ItemAddressBook("address_book"));
        ItemRenderRegister.registerRender(itemAddressBook);

        for(int i = 0; i < BlockFrame.FrameType.values().length; i++) {
            ItemRenderRegister.registerRender(RegisterBlocks.itemFrame, i, "block_frame_" + BlockFrame.FrameType.values()[i].getName());
        }

        itemPortalModule = GameRegistry.register(new ItemPortalModule("portal_module"));
        for(int i = 0; i < ItemPortalModule.PortalModule.values().length; i++) {
            ItemRenderRegister.registerRender(RegisterItems.itemPortalModule, i, "portal_module_" + ItemPortalModule.PortalModule.values()[i].getName());
        }

        itemUpgrade = GameRegistry.register(new ItemUpgrade("upgrade"));
        for(int i = 0; i < ItemUpgrade.unlocalizedName.length; i++) {
            ItemRenderRegister.registerRender(RegisterItems.itemUpgrade, i, "upgrade_" + ItemUpgrade.unlocalizedName[i]);
        }

        if (ConfigurationHandler.CONFIG_RECIPES_TE) {
            itemDiamondNugget  = GameRegistry.register(new ItemDiamondNugget("diamondNugget"));
            ItemRenderRegister.registerRender(itemDiamondNugget);
        }
    }
}

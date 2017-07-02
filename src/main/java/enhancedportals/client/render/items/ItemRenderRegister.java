package enhancedportals.client.render.items;

import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.item.ItemUpgrade;
import enhancedportals.registration.RegisterBlocks;
import enhancedportals.registration.RegisterItems;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{

    public static void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerRender(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + "_" + metadata));
    }

    private static void registerRender(Item item, int metadata, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(new ResourceLocation(Reference.EPMod.mod_id, fileName), "inventory"));
    }

    public static void preinit(){
        registerRender(RegisterItems.itemAddressBook);
        registerRender(RegisterItems.itemBlankUpgrade);
        registerRender(RegisterItems.itemBlankPortalModule);
        registerRender(RegisterItems.itemGlasses);
        registerRender(RegisterItems.itemLocationCard);
        registerRender(RegisterItems.itemNanobrush);
        registerRender(RegisterItems.itemWrench);

        registerRender(RegisterBlocks.itemStabilizer);

        for(int i = 0; i < BlockFrame.FrameType.values().length; i++) {
            registerRender(RegisterBlocks.itemFrame, i, "block_frame_" + BlockFrame.FrameType.values()[i].getName());
        }

        Item itemBlockDecorBorderedQuartz = Item.getItemFromBlock(RegisterBlocks.blockDecorBorderedQuartz);
        registerRender(itemBlockDecorBorderedQuartz);

        Item itemEnderInfusedMetal = Item.getItemFromBlock(RegisterBlocks.blockDecorEnderInfusedMetal);
        registerRender(itemEnderInfusedMetal);


        if(ConfigurationHandler.CONFIG_RECIPES_TE) {
            registerRender(RegisterItems.itemDiamondNugget);
        }

        for(int i = 0; i < ItemPortalModule.PortalModule.values().length; i++) {
            registerRender(RegisterItems.itemPortalModule, i, "portal_module_" + ItemPortalModule.PortalModule.values()[i].getName());
        }
        for(int i = 0; i < 7; i++) {
            registerRender(RegisterItems.itemUpgrade, i, "upgrade_" + ItemUpgrade.unlocalizedName[i]);
        }
    }

}


package enhancedportals.client.render.items;

import enhancedportals.block.BlockFrame;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.registration.RegisterItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{

    private static void registerRender(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerMetaRender(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + "_" + metadata));
    }

    public static void preinit(){
        for(int i = 0; i < ItemPortalModule.PortalModules.values().length; i++) {
            registerMetaRender(RegisterItems.itemPortalModule, i);
        }
        for(int i = 0; i < BlockFrame.FrameType.values().length; i++) {
            registerMetaRender(RegisterItems.itemUpgrade, i);
        }
    }

    public static void init()
    {
        registerRender(RegisterItems.itemAddressBook);
        registerRender(RegisterItems.itemBlankUpgrade);
        registerRender(RegisterItems.itemBlankPortalModule);
        registerRender(RegisterItems.itemGlasses);
        registerRender(RegisterItems.itemLocationCard);
        registerRender(RegisterItems.itemNanobrush);
        registerRender(RegisterItems.itemWrench);

//        registerRender(RegisterItems.itemDiamondNugget);

    }
}


package enhancedportals.client.render.items;

import enhancedportals.Reference;
import enhancedportals.registration.registerItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{
    public static String modid = Reference.EPMod.mod_id;

    private static final void registerRender(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static final void registerMetaRender(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + "_" + metadata));
    }

    public static void preinit(){

        registerMetaRender(registerItems.itemPortalModule, 0);
        registerMetaRender(registerItems.itemPortalModule, 1);
        registerMetaRender(registerItems.itemPortalModule, 2);
        registerMetaRender(registerItems.itemPortalModule, 3);
        registerMetaRender(registerItems.itemPortalModule, 4);
        registerMetaRender(registerItems.itemPortalModule, 5);
        registerMetaRender(registerItems.itemPortalModule, 6);
        registerMetaRender(registerItems.itemPortalModule, 7);

        registerMetaRender(registerItems.itemUpgrade, 0);
        registerMetaRender(registerItems.itemUpgrade, 1);
        registerMetaRender(registerItems.itemUpgrade, 2);
        registerMetaRender(registerItems.itemUpgrade, 3);
        registerMetaRender(registerItems.itemUpgrade, 4);
        registerMetaRender(registerItems.itemUpgrade, 5);
        registerMetaRender(registerItems.itemUpgrade, 6);
        registerMetaRender(registerItems.itemUpgrade, 7);
    }

    public static void init()
    {
        registerRender(registerItems.itemAddressBook);
        registerRender(registerItems.itemDiamondNugget);
        registerRender(registerItems.itemBlankUpgrade);
        registerRender(registerItems.itemBlankPortalModule);
        registerRender(registerItems.itemGlasses);
        registerRender(registerItems.itemLocationCard);
        registerRender(registerItems.itemNanobrush);
        registerRender(registerItems.itemWrench);
    }

}


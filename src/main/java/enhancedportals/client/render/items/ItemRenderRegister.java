package enhancedportals.client.render.items;

import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.registration.RegisterBlocks;
import enhancedportals.registration.RegisterItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{
    public static String modid = Reference.EPMod.mod_id;

    private static final void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.EPMod.mod_id + ":" + item.getRegistryName(), "inventory"));
    }

    private static final void registerMetaRender(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName() + "_" + metadata));
    }

    public static void preinit(){

        for(int i = 0; i < ItemPortalModule.PortalModules.values().length; i++)
            registerMetaRender(RegisterItems.itemPortalModule, i);

        for(int i = 0; i < BlockFrame.FrameType.values().length; i++)
            registerMetaRender(RegisterItems.itemUpgrade, i);
    }

    public static void init()
    {
        registerRender(RegisterItems.itemAddressBook);
//        registerRender(registerItems.itemDiamondNugget);
        registerRender(RegisterItems.itemBlankUpgrade);
        registerRender(RegisterItems.itemBlankPortalModule);
        registerRender(RegisterItems.itemGlasses);
        registerRender(RegisterItems.itemLocationCard);
        registerRender(RegisterItems.itemNanobrush);
        registerRender(RegisterItems.itemWrench);

        ModelLoader.setCustomModelResourceLocation((RegisterBlocks.itemblockDecorEnderInfusedMetal), 0, new ModelResourceLocation(RegisterBlocks.itemblockDecorEnderInfusedMetal.getRegistryName(), "inventory"));
    }

}


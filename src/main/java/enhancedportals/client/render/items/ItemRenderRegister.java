package enhancedportals.client.render.items;

import enhancedportals.Reference;
import enhancedportals.registration.registerItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister
{
    public static String modid = Reference.EPMod.mod_id;

    public static void register(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }


    public static void init()
    {
        register(registerItems.itemAddressBook);
        register(registerItems.itemDiamondNugget);
        register(registerItems.itemBlankUpgrade);
        register(registerItems.itemBlankPortalMod);
        register(registerItems.itemGlasses);
        register(registerItems.itemLocationCard);
        register(registerItems.itemNanobrush);
        register(registerItems.itemPortalModule);
        register(registerItems.itemUpgrade);
        register(registerItems.itemWrench);
    }
}

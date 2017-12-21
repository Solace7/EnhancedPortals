package enhancedportals.client.render.items;

import enhancedportals.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegister
{

    public static void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void registerRender(Item item, int metadata, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(Reference.EPMod.mod_id + ":" + fileName, "inventory"));
    }
}


package enhancedportals.client.render.blocks;

import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.registration.RegisterBlocks;
import enhancedportals.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockRenderRegister
{
    public static void registerRender(Block block)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    public static void registerRender(Block block, int metadata)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    public static void registerRender(Block block, int metadata, String fileName)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(Reference.EPMod.mod_id + ":" + fileName, "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

}

package enhancedportals.client.render.blocks;

import enhancedportals.registration.RegisterBlocks;
import enhancedportals.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockRenderRegister
{
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    public static void init()
    {
//        registerRender(RegisterBlocks.blockDecorBorderedQuartz);
//        registerRender(RegisterBlocks.blockDecorEnderInfusedMetal);
        registerRender(RegisterBlocks.blockFrame);
        registerRender(RegisterBlocks.blockPortal);
        registerRender(RegisterBlocks.blockStabilizer);
    }
}

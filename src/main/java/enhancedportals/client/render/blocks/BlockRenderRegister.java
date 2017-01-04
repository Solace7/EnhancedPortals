package enhancedportals.client.render.blocks;

import enhancedportals.registration.registerBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public final class BlockRenderRegister
{

    public static void register(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new
                ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    public static void init()
    {
        register(registerBlocks.blockDecorBorderedQuartz);
        register(registerBlocks.blockStabalizerEmpty);
        register(registerBlocks.blockStabalizer);
        register(registerBlocks.blockDecorEnderInfusedMetal);
        register(registerBlocks.blockFrame);
        register(registerBlocks.blockPortal);
    }
}

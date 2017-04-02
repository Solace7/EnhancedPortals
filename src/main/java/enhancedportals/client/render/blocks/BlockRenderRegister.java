package enhancedportals.client.render.blocks;

import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockStabilizer;
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
    private static void registerRender(Block block, int meta)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    private static void registerVariantRender(Block block, int meta)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
    }

    public static void init()
    {
        registerRender(RegisterBlocks.blockDecorBorderedQuartz, 0);
        registerRender(RegisterBlocks.blockDecorEnderInfusedMetal, 0);
        registerRender(RegisterBlocks.blockPortal, 0);

        registerRender(RegisterBlocks.blockFrame, BlockFrame.FrameType.FRAME.getMetadata());
        registerRender(RegisterBlocks.blockFrame, BlockFrame.FrameType.PORTAL_CONTROLLER.getMetadata());

        registerRender(RegisterBlocks.blockStabilizer, BlockStabilizer.StabilizerPart.STABILIZER.getMetadata());
        registerRender(RegisterBlocks.blockStabilizer, BlockStabilizer.StabilizerPart.MAIN.getMetadata());

    }
}

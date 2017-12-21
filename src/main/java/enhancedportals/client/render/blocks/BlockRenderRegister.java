package enhancedportals.client.render.blocks;

import enhancedportals.Reference;
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
    private static void registerRender(Block block)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    private static void registerRender(Block block, int metadata)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    private static void registerRender(Block block, int metadata, String fileName)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(Reference.EPMod.mod_id + ":" + fileName, "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    public static void preInit()
    {
        registerRender(RegisterBlocks.blockDecorBorderedQuartz);
        registerRender(RegisterBlocks.blockDecorEnderInfusedMetal);
        registerRender(RegisterBlocks.blockPortal);

        for(int i = 0; i < BlockFrame.FrameType.values().length; i++)
        {
            registerRender(RegisterBlocks.blockFrame, i, "block_frame_" + BlockFrame.FrameType.values()[i].getName());
        }

        for (int i = 0; i < BlockStabilizer.StabilizerPart.values().length; i++) {
            registerRender(RegisterBlocks.blockStabilizer, i,"dbs_" + BlockStabilizer.StabilizerPart.values()[i].getName());
        }


    }
}

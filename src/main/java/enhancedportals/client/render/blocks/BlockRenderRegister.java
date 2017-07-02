package enhancedportals.client.render.blocks;

import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.registration.RegisterBlocks;
import enhancedportals.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

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

    private static void registerRender(Block block, int meta)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        } else {
            LogHelper.debug("No item block for: " + block.getRegistryName());
        }
    }

    private static void registerRender(Block block, int meta, String fileName)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Reference.EPMod.mod_id, fileName), "inventory"));
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


        if (Item.getItemFromBlock(RegisterBlocks.blockFrame)!= null) {
            ModelBakery.registerItemVariants(Item.getItemFromBlock(RegisterBlocks.blockFrame),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_frame"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_controller"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_redstone_interface"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_network_interface"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_dialling_device"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_module_manipulator"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_transfer_fluid"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_transfer_item"),
                    new ResourceLocation(Reference.EPMod.mod_id, "block_frame_transfer_energy"));
        } else {
            LogHelper.log(Level.INFO,"Debugging: itemFrame is null!");
        }

        for (int i = 0; i < BlockStabilizer.StabilizerPart.values().length; i++) {
            registerRender(RegisterBlocks.blockStabilizer, i);
        }


    }
}

package enhancedportals.registration;

import enhancedportals.block.*;
import enhancedportals.client.render.blocks.BlockRenderRegister;
import enhancedportals.item.ItemPortalFrame;
import enhancedportals.item.ItemStabilizer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterBlocks
{
    public static BlockFrame blockFrame;
    public static ItemBlock itemFrame;

    public static BlockStabilizer blockStabilizer;
    public static ItemBlock itemStabilizer;

    public static BlockDecorBorderedQuartz blockDecorBorderedQuartz;

    public static BlockDecorEnderInfusedMetal blockDecorEnderInfusedMetal;

    public static BlockPortal blockPortal;

    private static void registerBlock(Block block)
    {
        ItemBlock itemBlock = new ItemBlock((block));
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
    }

    private static void registerBlock(Block block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
    }


    public static void preinit()
    {
        blockFrame = new BlockFrame("frame");
        itemFrame = new ItemPortalFrame(blockFrame);
        registerBlock(blockFrame, itemFrame);
        BlockRenderRegister.registerRender(RegisterBlocks.blockFrame);
        for(int i = 0; i < BlockFrame.FrameType.values().length; i++)
        {
            BlockRenderRegister.registerRender(RegisterBlocks.blockFrame, i, "block_frame_" + BlockFrame.FrameType.values()[i].getName());
        }

        blockStabilizer = new BlockStabilizer("dbs");
        itemStabilizer = new ItemStabilizer(blockStabilizer);
        registerBlock(blockStabilizer,itemStabilizer);

        for(int i = 0; i < BlockStabilizer.StabilizerPart.values().length; i++) {
            BlockRenderRegister.registerRender(RegisterBlocks.blockStabilizer,i);
        }

        registerBlock(blockDecorBorderedQuartz = new BlockDecorBorderedQuartz("decor_frame"));
        BlockRenderRegister.registerRender(RegisterBlocks.blockDecorBorderedQuartz);

        registerBlock(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"));
        BlockRenderRegister.registerRender(RegisterBlocks.blockDecorEnderInfusedMetal);

        registerBlock(blockPortal = new BlockPortal("portal"));
        BlockRenderRegister.registerRender(RegisterBlocks.blockPortal);

    }
}

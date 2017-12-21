package enhancedportals.registration;

import enhancedportals.block.*;
import enhancedportals.item.ItemPortalFrame;
import enhancedportals.item.ItemStabilizer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterBlocks
{
    public static BlockFrame blockFrame;
    public static ItemPortalFrame itemFrame;

    public static BlockStabilizer blockStabilizer;
    public static ItemStabilizer itemStabilizer;

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
        registerBlock(blockPortal = new BlockPortal("portal"));

        itemFrame = new ItemPortalFrame(new BlockFrame("frame"));
        registerBlock(blockFrame = new BlockFrame("frame"), itemFrame);

        itemStabilizer = GameRegistry.register(new ItemStabilizer("dbs", new BlockStabilizer("dbs")));
        GameRegistry.register(blockStabilizer = new BlockStabilizer("dbs"));

        registerBlock(blockDecorBorderedQuartz = new BlockDecorBorderedQuartz("decor_frame"));
        registerBlock(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"));    }
}

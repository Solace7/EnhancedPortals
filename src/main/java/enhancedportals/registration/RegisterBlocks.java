package enhancedportals.registration;

import enhancedportals.block.*;
import enhancedportals.item.ItemPortalFrame;
import enhancedportals.item.ItemStabilizer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterBlocks
{
    public static Block blockFrame;
    public static ItemBlock itemFrame;

    public static Block blockStabilizer;
    public static Item itemStabilizer;

    public static Block blockDecorBorderedQuartz;

    public static Block blockDecorEnderInfusedMetal;

    public static Block blockPortal;

    private static void registerBlock(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
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
        registerBlock(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"));


    }
}

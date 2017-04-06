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
    public static Item itemFrame;

    public static Block blockPortal;

    public static Block blockStabilizer;
    public static Item itemStabilizer;

    public static Block blockDecorBorderedQuartz;

    public static Block blockDecorEnderInfusedMetal;

    private static void registerBlocks(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }


    public static void preinit()
    {
         GameRegistry.register(blockPortal = new BlockPortal("portal"));

        GameRegistry.register(blockFrame = new BlockFrame("frame"));
        itemFrame = GameRegistry.register(new ItemPortalFrame("frame", new BlockFrame("frame")));


        GameRegistry.register(blockStabilizer = new BlockStabilizer("dbs"));
        itemStabilizer = GameRegistry.register(new ItemStabilizer("dbs", new BlockStabilizer("dbs")));

        registerBlocks(blockDecorBorderedQuartz = new BlockDecorBorderedQuartz("decor_frame"));
        registerBlocks(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"));
    }
}

package enhancedportals.registration;

import enhancedportals.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterBlocks
{
    public static Block blockFrame;
    public static Block blockPortal;
    public static Block blockStabilizer;
    public static Block blockDecorBorderedQuartz;
    public static ItemBlock itemBlockDecorBorderedQuartz;
    public static ItemBlock itemblockDecorEnderInfusedMetal;
    public static Block blockDecorEnderInfusedMetal;

    private static void registerBlocks(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    private static void registerTiles(Block block)
    {

    }


    public static void preinit()
    {
        blockFrame = GameRegistry.register(new BlockFrame("frame"));

        blockPortal = GameRegistry.register(new BlockPortal("portal"));

        blockStabilizer = GameRegistry.register(new BlockStabilizer("dbs"));

        blockDecorBorderedQuartz = GameRegistry.register(new BlockDecorBorderedQuartz("decor_frame"));
        itemBlockDecorBorderedQuartz = new ItemBlock(blockDecorBorderedQuartz);
        itemBlockDecorBorderedQuartz.setRegistryName(blockDecorBorderedQuartz.getRegistryName());
        GameRegistry.register(itemBlockDecorBorderedQuartz);

        blockDecorEnderInfusedMetal = GameRegistry.register(new BlockDecorEnderInfusedMetal("decor_dbs"));
    }
}

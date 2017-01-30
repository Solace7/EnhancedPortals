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
    public static ItemBlock itemBlockDecorBorderedQuartz;

    public static ItemBlock itemblockDecorEnderInfusedMetal;
    public static Block blockDecorEnderInfusedMetal;

    private static void registerTiles(Block block)
    {

    }


    public static void preinit()
    {
        blockFrame = GameRegistry.register(new BlockFrame("frame"));
        itemFrame = GameRegistry.register(new ItemPortalFrame("frame", new BlockFrame("frame")));

        blockPortal = (new BlockPortal("portal"));

        blockStabilizer = (new BlockStabilizer("dbs"));
        itemStabilizer = GameRegistry.register(new ItemStabilizer("bridge_stabilizer"));

        blockDecorBorderedQuartz = GameRegistry.register(new BlockDecorBorderedQuartz("decor_frame"));
        itemBlockDecorBorderedQuartz = new ItemBlock(blockDecorBorderedQuartz);
        GameRegistry.register(itemBlockDecorBorderedQuartz.setRegistryName(blockDecorBorderedQuartz.getRegistryName()));

        blockDecorEnderInfusedMetal = GameRegistry.register(new BlockDecorEnderInfusedMetal("decor_dbs"));
        itemblockDecorEnderInfusedMetal = new ItemBlock(blockDecorEnderInfusedMetal);
        GameRegistry.register(itemblockDecorEnderInfusedMetal.setRegistryName(blockDecorEnderInfusedMetal.getRegistryName()));
    }
}

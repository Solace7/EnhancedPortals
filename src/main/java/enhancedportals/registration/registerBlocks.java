package enhancedportals.registration;

import enhancedportals.block.*;
import enhancedportals.item.ItemStabilizer;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class registerBlocks
{
    public static Block blockFrame;
    public static Block blockPortal;
    public static Block blockStabalizer;
    public static Block blockDecorBorderedQuartz;
    public static Block blockDecorEnderInfusedMetal;
    public static Block blockStabalizerEmpty;

    public static void init()
    {
        GameRegistry.registerBlock(blockFrame = new BlockFrame("frame"), "frame");
        GameRegistry.registerBlock(blockPortal = new BlockPortal("portal"), "portal");
        GameRegistry.registerBlock(blockStabalizer = new BlockStabilizer("dbs"), ItemStabilizer.class, "dbs");
        GameRegistry.registerBlock(blockDecorBorderedQuartz = new BlockDecorBorderedQuartz("decor_frame"), "decor_frame");
        GameRegistry.registerBlock(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"), "decor_dbs");
        GameRegistry.registerBlock(blockStabalizerEmpty = new BlockStabilizerEmpty("dbs_empty"), "dbs_empty");
    }
}

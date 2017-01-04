package enhancedportals.registration;

import enhancedportals.block.*;
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

    public static void preinit()
    {
        GameRegistry.register(blockFrame = new BlockFrame());
        GameRegistry.register(blockPortal = new BlockPortal());
        GameRegistry.register(blockStabalizer = new BlockStabilizer());
        GameRegistry.register(blockDecorBorderedQuartz = new BlockDecorBorderedQuartz("decor_frame"));
        GameRegistry.register(blockDecorEnderInfusedMetal = new BlockDecorEnderInfusedMetal("decor_dbs"));
        GameRegistry.register(blockStabalizerEmpty = new BlockStabilizerEmpty());
    }
}

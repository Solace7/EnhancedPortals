package enhancedportals.registration;

import cpw.mods.fml.common.registry.GameRegistry;
import enhancedportals.block.*;
import enhancedportals.item.ItemFrame;
import enhancedportals.item.ItemStabilizer;

public class RegisterBlocks
{
    public static void preInit()
    {
        GameRegistry.registerBlock(new BlockFrame("frame"), ItemFrame.class, "frame");
        GameRegistry.registerBlock(new BlockPortal("portal"), "portal");
        GameRegistry.registerBlock(new BlockStabilizer("dbs"), ItemStabilizer.class, "dbs");
        GameRegistry.registerBlock(new BlockDecorBorderedQuartz("decor_frame"), "decor_frame");
        GameRegistry.registerBlock(new BlockDecorEnderInfusedMetal("decor_dbs"), "decor_dbs");
        GameRegistry.registerBlock(new BlockStabilizerEmpty("dbs_empty"), "dbs_empty");
    }
}

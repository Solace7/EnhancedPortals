package enhancedportals;

public class Reference
{
    public static class EPMod
    {

        public static final String name = "Enhanced Portals";
        public static final String mod_id = "enhancedportals";
        public static final String version = "3.0.13";
        public static final String URL = "https://raw.githubusercontent.com/Solace7/enhancedportals/master/docs/VERSION";
        public static final String proxyCommon = "enhancedportals.network.CommonProxy";
        public static final String proxyClient = "enhancedportals.network.ClientProxy";
        public static final String GUI_FACTORY_CLASS = "enhancedportals.client.gui.GuiFactory";


    }

    public static class Dependencies
    {
        public static final String MODID_OPENCOMPUTERS = "OpenComputers";
        public static final String MODID_COMPUTERCRAFT = "ComputerCraft";
        public static final String MODID_THERMALEXPANSION = "ThermalExpansion";
    }

    public static class EPConfiguration
    {
        public static boolean disableParticles = false;
        public static boolean disableSounds = false;

        public static boolean forceFrameOverlay = false;

        public static boolean portalDestroysBlocks = false;

        public static boolean recipeVanilla = true;
        public static boolean recipeTE = true;

        public static boolean requirePower = true;
        public static int initializationCost = 10000;
        public static int entityBaseCost = 1000;
        public static int keepAliveCost = 10;
        public static int potionIDFeatherfall = 40;
    }
}

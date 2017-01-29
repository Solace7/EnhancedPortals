package enhancedportals;

public class Reference
        {
            public static class EPMod
            {

                public static final String name = "Enhanced Portals";
                public static final String mod_id = "enhancedportals";
                public static final String version = "3.0.14";
                public static final String URL = "https://raw.githubusercontent.com/Solace7/enhancedportals/master/docs/VERSION";
                public static final String proxyCommon = "enhancedportals.network.CommonProxy";
                public static final String proxyServer = "enhancedportals.network.ServerProxy";
                public static final String proxyClient = "enhancedportals.network.ClientProxy";
                public static final String GUI_FACTORY_CLASS = "enhancedportals.client.gui.GuiFactory";


            }

    public static class Dependencies
    {
        public static final String MODID_OPENCOMPUTERS = "OpenComputers";
//        public static final String MODID_COMPUTERCRAFT = "ComputerCraft";
//        public static final String MODID_THERMALEXPANSION = "ThermalExpansion";
    }

    public static class EPConfiguration
    {
        public static boolean disableParticles = false;
        public static boolean disableSounds = false;

        public static boolean forceFrameOverlay = false;

        public static boolean portalDestroysBlocks = false;

        public static int activePortalsPerRow = 2;

        public static boolean recipeVanilla = true;
//        public static boolean recipeTE = true;

        public static boolean requirePower = true;
        public static int initializationCost = 10000;
        public static int entityBaseCost = 1000;
        public static int keepAliveCost = 10;
        public static int potionIDFeatherfall = 40;
    }

    public static class GuiEnums {

        public enum GUI_DIAL {
            DIAL_A, DIAL_B, DIAL_C, DIAL_D, DIAL_E, ADDRESS_DIAL
        }

        public enum GUI_ADDRESS_BOOK {
            AB_A, AB_B, AB_C, AB_D, AB_E
        }

        public enum GUI_CONTROLLER {
            CONTROLLER_A, CONTROLLER_B
        }

        public enum GUI_TEXTURE {
            TEXTURE_A, TEXTURE_B, TEXTURE_C, TEXTURE_DIAL_EDIT_A, TEXTURE_DIAL_EDIT_B, TEXTURE_DIAL_EDIT_C, TEXTURE_DIAL_SAVE_A, TEXTURE_DIAL_SAVE_B, TEXTURE_DIAL_SAVE_C
        }

        public enum GUI_TRANFER {
            TRANSFER_FLUID, TRANSFER_ENERGY, TRANSFER_ITEM
        }

        public enum GUI_MISC {
            DIM_BRIDGE_STABILIZER, REDSTONE_INTERFACE, MODULE_MANIPULATOR, NETWORK_INTERFACE_A, NETWORK_INTERFACE_B
        }
    }
}

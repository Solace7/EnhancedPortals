package enhancedportals.utility;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import enhancedportals.Reference;
import enhancedportals.Reference.EPConfiguration;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static int CONFIG_REDSTONE_FLUX_COST = 100000, CONFIG_REDSTONE_FLUX_TIMER = 20;
    public static int CONFIG_PORTAL_CONNECTIONS_PER_ROW = 2;
    public static boolean CONFIG_FORCE_FRAME_OVERLAY;
    public static boolean CONFIG_DISABLE_SOUNDS;
    public static boolean CONFIG_DISABLE_PARTICLES;
    public static boolean CONFIG_PORTAL_DESTROYS_BLOCKS;
    public static boolean CONFIG_FASTER_PORTAL_COOLDOWN;
    public static boolean CONFIG_REQUIRE_POWER;
    public static boolean CONFIG_UPDATE_NOTIFIER;
    public static boolean CONFIG_RECIPES_VANILLA;
    public static boolean CONFIG_RECIPES_TE;
    public static int CONFIG_POTION_FEATHERFALL_ID = 40;
    public static double CONFIG_POWER_MULTIPLIER, CONFIG_POWER_STORAGE_MULTIPLIER;

    public static Configuration config;

    public static final String CATEGORY_POTIONS = "Potions";
    public static final String CATEGORY_MISC = "Misc";
    public static final String CATEGORY_OVERRIDES = "Overrides";
    public static final String CATEGORY_POWER = "Power";
    public static final String CATEGORY_PORTAL = "Portal";
    public static final String CATEGORY_CRAFTING = "Crafting";

    public static void setupConfiguration(File configFile)
    {
        if(config == null ) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    public static void loadConfig() {

        CONFIG_FORCE_FRAME_OVERLAY = config.getBoolean("ForceShowFrameOverlays", CATEGORY_MISC,false, "If true, shows frame overlays without goggles on");
        CONFIG_UPDATE_NOTIFIER = config.getBoolean("NotifyOfUpdates", CATEGORY_MISC,  true, "If true, a chat message will notify the player of updates");

        CONFIG_DISABLE_SOUNDS = config.getBoolean("DisableSounds", CATEGORY_OVERRIDES, false, "Disables portal sounds");
        CONFIG_DISABLE_PARTICLES = config.getBoolean("DisableParticles", CATEGORY_OVERRIDES,false, "Disables portal particles");

        CONFIG_PORTAL_DESTROYS_BLOCKS = config.getBoolean("PortalsDestroyBlocks", CATEGORY_PORTAL,  true, "If true, portals will destroy surrounding blocks");
        CONFIG_FASTER_PORTAL_COOLDOWN = config.getBoolean("FasterPortalCooldown", CATEGORY_PORTAL,  false, "If true, portals will have a faster cooldown");
        CONFIG_PORTAL_CONNECTIONS_PER_ROW = config.get( CATEGORY_PORTAL, "ActivePortalsPerRow",2, "").getInt(2);

        CONFIG_REQUIRE_POWER = config.getBoolean("RequirePower",CATEGORY_POWER, true, "");
        CONFIG_REDSTONE_FLUX_COST = config.get(CATEGORY_POWER,"PowerCost", 100000, "Portal power cost (RF)").getInt(100000);
        CONFIG_POWER_MULTIPLIER = config.get(CATEGORY_POWER, "PowerMultiplier", 1.0).getDouble(1.0);
        CONFIG_POWER_STORAGE_MULTIPLIER = config.get(CATEGORY_POWER, "DBSPowerStorageMultiplier", 1.0).getDouble(1.0);

        CONFIG_POTION_FEATHERFALL_ID = config.get(CATEGORY_POTIONS, "PotionID", 40, "Featherfall potion ID").getInt(40);

        CONFIG_RECIPES_TE = config.getBoolean("ThermalExpansion", CATEGORY_CRAFTING, true, "");
        CONFIG_RECIPES_VANILLA = config.getBoolean("Vanilla", CATEGORY_CRAFTING, true, "");

        if(config.hasChanged()) {
            config.save();
        }

        if (CONFIG_POWER_MULTIPLIER < 0 || CONFIG_REDSTONE_FLUX_COST < 0)
        {
            CONFIG_REQUIRE_POWER = false;
        }

        if (CONFIG_POWER_STORAGE_MULTIPLIER < 0.01)
        {
            CONFIG_POWER_STORAGE_MULTIPLIER = 0.01;
        }

        if (!EPConfiguration.requirePower) {
            EPConfiguration.initializationCost = 0;
            EPConfiguration.entityBaseCost = 0;
            EPConfiguration.keepAliveCost = 0;
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.EPMod.mod_id))
        {
            loadConfig();
        }
    }
}

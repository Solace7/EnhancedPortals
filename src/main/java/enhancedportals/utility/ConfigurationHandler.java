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

        CONFIG_FORCE_FRAME_OVERLAY = config.get("Misc", "ForceShowFrameOverlays", false).getBoolean(false);
        CONFIG_DISABLE_SOUNDS = config.get("Overrides", "DisableSounds", false).getBoolean(false);
        CONFIG_DISABLE_PARTICLES = config.get("Overrides", "DisableParticles", false).getBoolean(false);
        CONFIG_PORTAL_DESTROYS_BLOCKS = config.get("Portal", "PortalsDestroyBlocks", true).getBoolean(true);
        CONFIG_FASTER_PORTAL_COOLDOWN = config.get("Portal", "FasterPortalCooldown", false).getBoolean(false);
        CONFIG_REQUIRE_POWER = config.get("Power", "RequirePower", true).getBoolean(true);
        CONFIG_REDSTONE_FLUX_COST = config.get("Power", "PowerCost", 100000).getInt(100000);
        CONFIG_POWER_MULTIPLIER = config.get("Power", "PowerMultiplier", 1.0).getDouble(1.0);
        CONFIG_POWER_STORAGE_MULTIPLIER = config.get("Power", "DBSPowerStorageMultiplier", 1.0).getDouble(1.0);
        CONFIG_PORTAL_CONNECTIONS_PER_ROW = config.get("Portal", "ActivePortalsPerRow", 2).getInt(2);
        CONFIG_POTION_FEATHERFALL_ID = config.get("Potions", "PotionID", 40).getInt(40);
        CONFIG_UPDATE_NOTIFIER = config.get("Misc", "NotifyOfUpdates", true).getBoolean(true);
        CONFIG_RECIPES_VANILLA = config.get("Crafting", "Vanilla", true).getBoolean(true);
        CONFIG_RECIPES_TE = config.get("Crafting", "ThermalExpansion", true).getBoolean(true);

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

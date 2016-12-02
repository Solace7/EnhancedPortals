package enhancedportals.network;

import com.mojang.realmsclient.gui.ChatFormatting;
import enhancedportals.EnhancedPortals;
import enhancedportals.portal.NetworkManager;
import enhancedportals.registration.registerRecipes;
import enhancedportals.utility.ConfigurationHandler;
import enhancedportals.utility.CreativeTabEP3;
import enhancedportals.utility.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static enhancedportals.EnhancedPortals.MOD_NAME;
import static enhancedportals.utility.ConfigurationHandler.CONFIG_RECIPES_VANILLA;
import static enhancedportals.utility.ConfigurationHandler.CONFIG_UPDATE_NOTIFIER;

public class CommonProxy
{
    public int gogglesRenderIndex = 0;
    public NetworkManager networkManager;

    public static String UPDATE_LATEST_VER;
    public static final CreativeTabs creativeTab = new CreativeTabEP3();

    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.setupConfiguration(new File(event.getSuggestedConfigurationFile().getParentFile(), MOD_NAME + File.separator + "config.cfg"));
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public void waitForController(ChunkPos controller, ChunkPos frame)
    {

    }

    public ArrayList<ChunkPos> getControllerList(ChunkPos controller)
    {
        return null;
    }

    public void clearControllerList(ChunkPos controller)
    {

    }

    public File getBaseDir()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getFile(".");
    }

    public File getResourcePacksDir()
    {
        return new File(getBaseDir(), "resourcepacks");
    }

    public File getWorldDir()
    {
        return new File(getBaseDir(), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectory().getAbsolutePath());
    }

    public void miscSetup()
    {
        //todo Add dungeon loot
        //ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ItemPortalModule.instance, 1, 4), 1, 1, 2));
    }

    public static boolean Notify(EntityPlayer player, String lateVers)
    {
        if (CONFIG_UPDATE_NOTIFIER)
        {
            //player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "Your framework for tampering with subatomic particles has been rendered obsolete"));

            player.addChatMessage(new TextComponentString(ChatFormatting.GOLD + "[EnhancedPortals] " + ChatFormatting.WHITE + "has been updated to v" + lateVers + " :: You are running v" + EnhancedPortals.MOD_VERSION));
            return true;
        }
        else
        {
            LogHelper.warn("You're using an outdated version (v" + EnhancedPortals.MOD_VERSION + ")");
            return false;
        }
    }

    public void setupCrafting()
    {
        if (CONFIG_RECIPES_VANILLA)
        {
            registerRecipes.initShaped();
            registerRecipes.initShapeless();
        }
        /*if (CONFIG_RECIPES_TE && Loader.isModLoaded(Reference.Dependencies.MODID_THERMALEXPANSION))
        {
            ThermalExpansion.registerRecipes();
        }*/


        try
        {
            URL versionIn = new URL(EnhancedPortals.UPDATE_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(versionIn.openStream()));
            UPDATE_LATEST_VER = in.readLine();

            if (FMLCommonHandler.instance().getSide() == Side.SERVER && !UPDATE_LATEST_VER.equals(EnhancedPortals.MOD_VERSION))
            {
                LogHelper.info("You're using an outdated version (v" + EnhancedPortals.MOD_VERSION + "). The newest " + "version is: " + UPDATE_LATEST_VER);
            }

        }
        catch (Exception e)
        {
            LogHelper.warn("Unable to get the latest version information");
            UPDATE_LATEST_VER = EnhancedPortals.MOD_VERSION;
        }
    }
}

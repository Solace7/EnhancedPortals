package enhancedportals.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.crafting.ThermalExpansion;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.portal.NetworkManager;
import enhancedportals.registration.*;
import enhancedportals.utility.ConfigurationHandler;
import enhancedportals.utility.CreativeTabEP3;
import enhancedportals.utility.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DimensionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static enhancedportals.EnhancedPortals.MOD_NAME;
import static enhancedportals.utility.ConfigurationHandler.*;

public class CommonProxy
{

    public int gogglesRenderIndex = 0;
    public NetworkManager networkManager;

    public static String UPDATE_LATEST_VER;
    public static final CreativeTabs creativeTab = new CreativeTabEP3();

    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.setupConfiguration(new File(event.getSuggestedConfigurationFile().getParentFile(), MOD_NAME + File.separator + "config.cfg"));
        RegisterBlocks.preInit();
        RegisterItems.preInit();
        RegisterPackets.preInit();
        RegisterPotions.preInit();
        RegisterTiles.preInit();
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public void waitForController(ChunkCoordinates controller, ChunkCoordinates frame)
    {

    }

    public ArrayList<ChunkCoordinates> getControllerList(ChunkCoordinates controller)
    {
        return null;
    }

    public void clearControllerList(ChunkCoordinates controller)
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
        return new File(getBaseDir(), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectoryName());
    }

    public void miscSetup()
    {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ItemPortalModule.instance, 1, 4), 1, 1, 2));
    }

    public static boolean Notify(EntityPlayer player, String lateVers)
    {
        if (CONFIG_UPDATE_NOTIFIER)
        {
            //player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Your framework for tampering with subatomic particles has been rendered obsolete"));

            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[EnhancedPortals] " + EnumChatFormatting.WHITE + "has been updated to v" + lateVers + " :: You are running v" + EnhancedPortals.MOD_VERSION));
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
            RegisterRecipes.initShaped();
            RegisterRecipes.initShapeless();
        }
        if (CONFIG_RECIPES_TE && Loader.isModLoaded(Reference.Dependencies.MODID_THERMALEXPANSION))
        {
            ThermalExpansion.registerRecipes();
        }


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

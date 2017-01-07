package enhancedportals;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import enhancedportals.block.BlockFrame;
import enhancedportals.guidebook.WormholeTunnelManual;
import enhancedportals.network.CommonProxy;
import enhancedportals.network.GuiHandler;
import enhancedportals.network.PacketPipeline;
import enhancedportals.portal.NetworkManager;
import enhancedportals.registration.*;
import enhancedportals.utility.LogHelper;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.lang.reflect.Method;

@Mod(name = EnhancedPortals.MOD_NAME, modid = EnhancedPortals.MOD_ID, version = EnhancedPortals.MOD_VERSION, dependencies = EnhancedPortals.MOD_DEPENDENCIES, guiFactory = Reference.EPMod.GUI_FACTORY_CLASS)

public class EnhancedPortals
{
    public static final String
            MOD_NAME = Reference.EPMod.name,
            MOD_ID = Reference.EPMod.mod_id,
            MOD_VERSION = Reference.EPMod.version,
            MOD_DEPENDENCIES = "after:ThermalExpansion; required-after:guideapi;",
            UPDATE_URL = Reference.EPMod.URL;

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    @Instance(MOD_ID)
    public static EnhancedPortals instance;

    @SidedProxy(clientSide = Reference.EPMod.proxyClient, serverSide = Reference.EPMod.proxyCommon)

    public static CommonProxy proxy;

    public EnhancedPortals()
    {
        LoggerConfig fml = new LoggerConfig(FMLCommonHandler.instance().getFMLLogger().getName(), Level.ALL, true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    //PRE INITIALIZATION

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        registerBlocks.init();
        registerItems.init();
        registerPackets.init();
        registerPotions.init();
        registerTiles.init();

        proxy.preInit(event);
        packetPipeline.initalise();
    }

    //INITIALIZATION

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.miscSetup();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        LogHelper.info("I am tampering with subatomic particles...what could go wrong?");

    }

    /**
     * Taken from the CC-API, allowing for use it if it's available, instead of shipping it/requiring it
     **/
    void initializeComputerCraft()
    {
        if (!Loader.isModLoaded(Reference.Dependencies.MODID_COMPUTERCRAFT))
        {
            return;
        }

        try
        {
            Class computerCraft = Class.forName("dan200.computercraft.ComputerCraft");
            Method computerCraft_registerPeripheralProvider = computerCraft.getMethod("registerPeripheralProvider", new Class[]{Class.forName("dan200.computercraft.api.peripheral.IPeripheralProvider")});
            computerCraft_registerPeripheralProvider.invoke(null, BlockFrame.instance);
        }
        catch (Exception e)
        {
            LogHelper.error("Could not load the CC-API");
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
        initializeComputerCraft();
        proxy.setupCrafting();
        WormholeTunnelManual.registerGuide();

        if (event.getSide() == Side.CLIENT)
        {
            FMLCommonHandler.instance().bus().register(new enhancedportals.network.LogOnHandler());
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.networkManager = new NetworkManager(event);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        PotionEffect effect = event.entityLiving.getActivePotionEffect(registerPotions.featherfallPotion);

        if (effect != null)
        {
            event.entityLiving.fallDistance = 0f;

            if (event.entityLiving.getActivePotionEffect(registerPotions.featherfallPotion).getDuration() <= 0)
            {
                event.entityLiving.removePotionEffect(registerPotions.featherfallPotion.id);
            }
        }
    }

    @SubscribeEvent
    public void worldSave(WorldEvent.Save event)
    {
        if (!event.world.isRemote)
        {
            proxy.networkManager.saveAllData();
        }
    }
}

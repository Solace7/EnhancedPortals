package enhancedportals;

import amerifrance.guideapi.api.GuideAPI;
import enhancedportals.block.BlockFrame;
import enhancedportals.client.render.blocks.BlockRenderRegister;
import enhancedportals.client.render.items.ItemRenderRegister;
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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.lang.reflect.Method;

@Mod(name = EnhancedPortals.MOD_NAME, modid = EnhancedPortals.MOD_ID, version = EnhancedPortals.MOD_VERSION, dependencies = EnhancedPortals.MOD_DEPENDENCIES, guiFactory = Reference.EPMod.GUI_FACTORY_CLASS)

public class EnhancedPortals
{
    public static final String MOD_NAME = Reference.EPMod.name,
            MOD_ID = Reference.EPMod.mod_id,
            MOD_VERSION = Reference.EPMod.version,
            MOD_DEPENDENCIES = "after:ThermalExpansion;required-after:guideapi",
            UPDATE_URL = Reference.EPMod.URL;

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    @Instance(MOD_ID)
    public static EnhancedPortals instance;

    @SidedProxy(clientSide = Reference.EPMod.proxyClient, serverSide = Reference.EPMod.proxyServer)
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
        FMLCommonHandler.instance().bus().register(new enhancedportals.network.LogOnHandler());
        MinecraftForge.EVENT_BUS.register(new enhancedportals.network.LogOnHandler());
        registerBlocks.init();
        registerItems.init();
        registerPackets.init();
        registerPotions.init();
        registerTiles.init();
        WormholeTunnelManual.registerManual();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            GuideAPI.setModel(WormholeTunnelManual.epManual);

        proxy.preInit(event);
        packetPipeline.initalise();
    }

    //INITIALIZATION

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("I am tampering with subatomic particles...what could go wrong?");
        proxy.miscSetup();
        proxy.setupCrafting();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        if(event.getSide() == Side.CLIENT) {
            ItemRenderRegister.init();
            BlockRenderRegister.init();
        }
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
        WormholeTunnelManual.registerManual();

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            WormholeTunnelManual.registerCategories();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.networkManager = new NetworkManager(event);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        PotionEffect effect = event.getEntityLiving().getActivePotionEffect(registerPotions.featherfallPotion);

        if (effect != null)
        {
            event.getEntityLiving().fallDistance = 0f;

            if (event.getEntityLiving().getActivePotionEffect(registerPotions.featherfallPotion).getDuration() <= 0)
            {
                event.getEntityLiving().removePotionEffect(registerPotions.featherfallPotion);
            }
        }
    }

    @SubscribeEvent
    public void worldSave(WorldEvent.Save event)
    {
        if (!event.getWorld().isRemote)
        {
            proxy.networkManager.saveAllData();
        }
    }
}

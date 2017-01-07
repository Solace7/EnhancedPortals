package enhancedportals.guidebook;


import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.BookBuilder;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.pages.PageIRecipe;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.block.BlockFrame;
import enhancedportals.item.ItemBlankPortalModule;
import enhancedportals.item.ItemBlankUpgrade;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.item.ItemWrench;
import enhancedportals.registration.registerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class WormholeTunnelManual
{
    public static Book epManual;
    public static List<CategoryAbstract> categories = new ArrayList();

    public static void registerGuide()
    {
        registerIntro();
        registerBlocks();
        registerUpgrades();
        registerPortalTravel();
        registerAdvanced();
        registerItems();
        BookBuilder epBookBuilder = new BookBuilder();
        epBookBuilder
                .setCategories(categories)
                .setUnlocBookTitle("guide.EnhancedPortals.book.title")
                .setUnlocWelcomeMessage("guide.EnhancedPortals.book.welcomeMessage")
                .setUnlocDisplayName("guide.EnhancedPortals.book.name")
                .setBookColor(new Color(142, 58, 166))
                .setAuthor("guide.EnhancedPortals.book.author")
                .setItemTexture("enhancedportals:manual");

        epManual = epBookBuilder.build();
        GuideRegistry.registerBook(epManual);
    }

    public static void registerIntro()
    {
        List<EntryAbstract> entries = new ArrayList();

        List<IPage> prelude = new ArrayList<IPage>();
        prelude.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.0.p.0")));

        List<IPage> basics = new ArrayList<IPage>();
        basics.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.1.p.0")));
        basics.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.1.p.1")));
        basics.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.1.p.2")));

        entries.add(new EntryUniText(prelude, "enhancedportals.manual.chapter.0.title"));
        entries.add(new EntryUniText(basics, "enhancedportals.manual.chapter.1.title"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.0", new ItemStack(ItemLocationCard.instance)));
    }


    public static void registerPortalTravel()
    {
        //includes stabalizing dimensions, building the harness and igniting a blackhole
        List<EntryAbstract> entries = new ArrayList();
        List<IPage> stabalizingDim = new ArrayList<IPage>();
        stabalizingDim.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.2.p.0")));
        stabalizingDim.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.2.p.1")));
        stabalizingDim.add(new PageIRecipe(registerRecipes.blockStabilizer));
        stabalizingDim.add(new PageIRecipe(registerRecipes.itemWrench));
        stabalizingDim.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.2.p.2")));

        List<IPage> buildingTheHarness = new ArrayList<IPage>();
        buildingTheHarness.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.3.p.0")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockFrame));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.3.p.1")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.itemLocationCard));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockPortalController));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.3.p.2")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockNetworkInterface));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.3.p.3")));


        List<IPage> ignitingBlackhole = new ArrayList<IPage>();
        ignitingBlackhole.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.4.p.0")));
        ignitingBlackhole.add(new PageIRecipe(registerRecipes.blockRedstoneInterface));
        ignitingBlackhole.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.4.p.1")));

        entries.add(new EntryUniText(stabalizingDim, "enhancedportals.manual.chapter.2.title"));
        entries.add(new EntryUniText(buildingTheHarness, "enhancedportals.manual.chapter.3.title"));
        entries.add(new EntryUniText(ignitingBlackhole, "enhancedportals.manual.chapter.4.title"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.1", new ItemStack(BlockFrame.instance)));
    }

    public static void registerAdvanced()
    {
        List<EntryAbstract> entries = new ArrayList();
        List<IPage> phoneHome = new ArrayList<IPage>();

        phoneHome.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.5.p.0")));
        phoneHome.add(new PageIRecipe(registerRecipes.blockDiallingDevice));

        List<IPage> accessorizing = new ArrayList<IPage>();
        //recipe for nanobrush, portal manipulator, and blank portal module
        accessorizing.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.6.p.0")));
        accessorizing.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.6.p.1")));
        accessorizing.add(new PageIRecipe(registerRecipes.itemNanobrush));
        accessorizing.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.6.p.2")));
        accessorizing.add(new PageIRecipe(registerRecipes.blockModuleManipulator));
        accessorizing.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.chapter.6.p.3")));

        entries.add(new EntryUniText(phoneHome, "enhancedportals.manual.chapter.5.title"));
        entries.add(new EntryUniText(accessorizing, "enhancedportals.manual.chapter.6.title"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.2", new ItemStack(ItemBlankUpgrade.instance)));
    }

    public static void registerBlocks()
    {
        //list of all the blocks and what they do
        List<EntryAbstract> entries = new ArrayList();

        List<IPage> dbs = new ArrayList<IPage>();
        dbs.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.dbs.info")));
        dbs.add(new PageIRecipe(registerRecipes.blockStabilizer));

        List<IPage> portalFrame = new ArrayList<IPage>();
        portalFrame.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame0.info")));
        portalFrame.add(new PageIRecipe(registerRecipes.blockFrame));

        List<IPage> portalController = new ArrayList<IPage>();
        portalController.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame1.info")));
        portalController.add(new PageIRecipe(registerRecipes.blockPortalController));

        List<IPage> redstoneInterface = new ArrayList<IPage>();
        redstoneInterface.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame2.info")));
        redstoneInterface.add(new PageIRecipe(registerRecipes.blockRedstoneInterface));

        List<IPage> networkInterface = new ArrayList<IPage>();
        networkInterface.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame3.info")));
        networkInterface.add(new PageIRecipe(registerRecipes.blockNetworkInterface));

        List<IPage> diallingDevice = new ArrayList<IPage>();
        diallingDevice.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame4.info")));
        diallingDevice.add(new PageIRecipe(registerRecipes.blockDiallingDevice));

        List<IPage> portalManipulator = new ArrayList<IPage>();
        portalManipulator.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame6.info")));
        portalManipulator.add(new PageIRecipe(registerRecipes.blockModuleManipulator));

        List<IPage> fluidTransportModule = new ArrayList<IPage>();
        fluidTransportModule.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame7.info")));
        fluidTransportModule.add(new PageIRecipe(registerRecipes.blockFluidTransfer));

        List<IPage> itemTransportModule = new ArrayList<IPage>();
        itemTransportModule.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame8.info")));
        itemTransportModule.add(new PageIRecipe(registerRecipes.blockItemTranfer));

        List<IPage> energyTransportModule = new ArrayList<IPage>();
        energyTransportModule.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.frame9.info")));
        energyTransportModule.add(new PageIRecipe(registerRecipes.blockEnergyTransfer));

        entries.add(new EntryUniText(dbs, "enhancedportals.manual.dbs.title"));
        entries.add(new EntryUniText(portalFrame, "enhancedportals.manual.frame0.title"));
        entries.add(new EntryUniText(portalController, "enhancedportals.manual.frame1.title"));
        entries.add(new EntryUniText(redstoneInterface, "enhancedportals.manual.frame2.title"));
        entries.add(new EntryUniText(networkInterface, "enhancedportals.manual.frame3.title"));
        entries.add(new EntryUniText(diallingDevice, "enhancedportals.manual.frame4.title"));
        entries.add(new EntryUniText(portalManipulator, "enhancedportals.manual.frame6.title"));
        entries.add(new EntryUniText(fluidTransportModule, "enhancedportals.manual.frame7.title"));
        entries.add(new EntryUniText(itemTransportModule, "enhancedportals.manual.frame8.title"));
        entries.add(new EntryUniText(energyTransportModule, "enhancedportals.manual.frame9.title"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.3", new ItemStack(BlockDecorEnderInfusedMetal.instance)));
    }

    public static void registerUpgrades()
    {
        //list all the modules and what they do
        List<EntryAbstract> entries = new ArrayList();
        List<IPage> blankModule = new ArrayList<IPage>();

        blankModule.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.blank_module.info")));
        blankModule.add(new PageIRecipe(registerRecipes.blockModuleManipulator));

        List<IPage> particleDestroy = new ArrayList<IPage>();
        particleDestroy.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.0.desc")));
        particleDestroy.add(new PageIRecipe(registerRecipes.particleRemoveModule));

        List<IPage> particleRainbow = new ArrayList<IPage>();
        particleRainbow.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.1.desc")));
        particleRainbow.add(new PageIRecipe(registerRecipes.rainbowParticlesModule));

        List<IPage> silencer = new ArrayList<IPage>();
        silencer.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.2.desc")));
        silencer.add(new PageIRecipe(registerRecipes.silencerUpgrade));

        List<IPage> momentum = new ArrayList<IPage>();
        momentum.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.3.dec")));
        momentum.add(new PageIRecipe(registerRecipes.keepMomentumUpgrade));

        List<IPage> cloaking = new ArrayList<IPage>();
        cloaking.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal.module.4.desc")));

        List<IPage> particleShader = new ArrayList<IPage>();
        particleShader.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.5.desc")));
        particleShader.add(new PageIRecipe(registerRecipes.tintUpgrade));

        List<IPage> facing = new ArrayList<IPage>();
        facing.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.6.desc")));
        facing.add(new PageIRecipe(registerRecipes.facingUpgrade));

        List<IPage> featherfall = new ArrayList<IPage>();
        featherfall.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal_module.7.desc")));
        featherfall.add(new PageIRecipe(registerRecipes.featherfallUpgrade));

        List<IPage> lookingGlass = new ArrayList<IPage>();
        lookingGlass.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("item.portal.module.8.desc")));
        //todo add looking glass module crafting recipe


        entries.add(new EntryUniText(blankModule, "enhancedportals.manual.blank_module.title"));
        entries.add(new EntryUniText(particleDestroy, "item.portal_module.0.name"));
        entries.add(new EntryUniText(particleRainbow, "item.portal_module.1.name"));
        entries.add(new EntryUniText(silencer, "item.portal_module.2.name"));
        entries.add(new EntryUniText(momentum, "item.portal_module.3.name"));
        entries.add(new EntryUniText(cloaking, "item.portal_module.4.name"));
        entries.add(new EntryUniText(particleShader, "item.portal_module.5.name"));
        entries.add(new EntryUniText(facing, "item.portal_module.6.name"));
        entries.add(new EntryUniText(featherfall, "item.portal_module.7.name"));
        entries.add(new EntryUniText(lookingGlass, "item.portal_module.8.name"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.4", new ItemStack(ItemBlankPortalModule.instance)));
    }

    public static void registerItems()
    {
        List<EntryAbstract> entries = new ArrayList();
        List<IPage> wrench = new ArrayList<IPage>();

        wrench.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.wrench.info")));
        wrench.add(new PageIRecipe(registerRecipes.itemWrench));

        List<IPage> nanobrush = new ArrayList<IPage>();
        nanobrush.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.nanobrush.info")));
        nanobrush.add(new PageIRecipe(registerRecipes.itemNanobrush));

        List<IPage> glasses = new ArrayList<IPage>();
        glasses.addAll((PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.glasses.info"))));
        glasses.add(new PageIRecipe(registerRecipes.itemGlasses));

        List<IPage> locationCard = new ArrayList<IPage>();
        locationCard.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.location_card.info")));
        locationCard.add(new PageIRecipe(registerRecipes.itemLocationCard));

        List<IPage> addressBook = new ArrayList<IPage>();
        addressBook.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("enhancedportals.manual.address_book.info")));
        //addressBook.add(new PageIRecipe(registerRecipes.itemAddressBook));

        entries.add(new EntryUniText(wrench, "enhancedportals.manual.wrench.title"));
        entries.add(new EntryUniText(nanobrush, "enhancedportals.manual.nanobrush.title"));
        entries.add(new EntryUniText(glasses, "enhancedportals.manual.glasses.title"));
        entries.add(new EntryUniText(locationCard, "enhancedportals.manual.location_card.title"));
        entries.add(new EntryUniText(addressBook, "enhancedportals.manual.address_book.title"));

        categories.add(new CategoryItemStack(entries, "guide.EnhancedPortals.category.5", new ItemStack(ItemWrench.instance)));
    }


}

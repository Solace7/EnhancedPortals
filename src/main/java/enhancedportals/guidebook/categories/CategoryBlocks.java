package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryResourceLocation;
import amerifrance.guideapi.page.PageIRecipe;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.registration.registerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CategoryBlocks
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        //list of all the blocks and what they do

        List<IPage> dbs = new ArrayList<IPage>();
        dbs.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.dbs.info")));
        dbs.add(new PageIRecipe(registerRecipes.blockStabilizer));

        List<IPage> portalFrame = new ArrayList<IPage>();
        portalFrame.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame0.info")));
        portalFrame.add(new PageIRecipe(registerRecipes.blockFrame));

        List<IPage> portalController = new ArrayList<IPage>();
        portalController.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame1.info")));
        portalController.add(new PageIRecipe(registerRecipes.blockPortalController));

        List<IPage> redstoneInterface = new ArrayList<IPage>();
        redstoneInterface.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame2.info")));
        redstoneInterface.add(new PageIRecipe(registerRecipes.blockRedstoneInterface));

        List<IPage> networkInterface = new ArrayList<IPage>();
        networkInterface.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame3.info")));
        networkInterface.add(new PageIRecipe(registerRecipes.blockNetworkInterface));

        List<IPage> diallingDevice = new ArrayList<IPage>();
        diallingDevice.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame4.info")));
        diallingDevice.add(new PageIRecipe(registerRecipes.blockDiallingDevice));

        List<IPage> portalManipulator = new ArrayList<IPage>();
        portalManipulator.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame6.info")));
        portalManipulator.add(new PageIRecipe(registerRecipes.blockModuleManipulator));

        List<IPage> fluidTransportModule = new ArrayList<IPage>();
        fluidTransportModule.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame7.info")));
        fluidTransportModule.add(new PageIRecipe(registerRecipes.blockFluidTransfer));

        List<IPage> itemTransportModule = new ArrayList<IPage>();
        itemTransportModule.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame8.info")));
        itemTransportModule.add(new PageIRecipe(registerRecipes.blockItemTranfer));

        List<IPage> energyTransportModule = new ArrayList<IPage>();
        energyTransportModule.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.frame9.info")));
        energyTransportModule.add(new PageIRecipe(registerRecipes.blockEnergyTransfer));

        entries.put(new ResourceLocation("enhancedportals.manual.dbs.title"));
        entries.put(new EntryResourceLocation(portalFrame, "enhancedportals.manual.frame0.title"));
        entries.put(new EntryUniText(portalController, "enhancedportals.manual.frame1.title"));
        entries.put(new EntryUniText(redstoneInterface, "enhancedportals.manual.frame2.title"));
        entries.put(new EntryUniText(networkInterface, "enhancedportals.manual.frame3.title"));
        entries.put(new EntryUniText(diallingDevice, "enhancedportals.manual.frame4.title"));
        entries.put(new EntryUniText(portalManipulator, "enhancedportals.manual.frame6.title"));
        entries.put(new EntryUniText(fluidTransportModule, "enhancedportals.manual.frame7.title"));
        entries.put(new EntryUniText(itemTransportModule, "enhancedportals.manual.frame8.title"));
        entries.put(new EntryUniText(energyTransportModule, "enhancedportals.manual.frame9.title"));

        return entries;
    }

}

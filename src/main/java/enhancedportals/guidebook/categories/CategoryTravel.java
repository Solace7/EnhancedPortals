package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import enhancedportals.block.BlockFrame;
import enhancedportals.registration.registerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static enhancedportals.guidebook.WormholeTunnelManual.categories;

public class CategoryTravel
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        //includes stabalizing dimensions, building the harness and igniting a blackhole
        List<IPage> stabalizingDim = new ArrayList<IPage>();
        stabalizingDim.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.2.p.0")));
        stabalizingDim.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.2.p.1")));
        stabalizingDim.add(new PageIRecipe(registerRecipes.blockStabilizer));
        stabalizingDim.add(new PageIRecipe(registerRecipes.itemWrench));
        stabalizingDim.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.2.p.2")));

        List<IPage> buildingTheHarness = new ArrayList<IPage>();
        buildingTheHarness.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.3.p.0")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockFrame));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.3.p.1")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.itemLocationCard));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockPortalController));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.3.p.2")));
        buildingTheHarness.add(new PageIRecipe(registerRecipes.blockNetworkInterface));
        buildingTheHarness.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.3.p.3")));


        List<IPage> ignitingBlackhole = new ArrayList<IPage>();
        ignitingBlackhole.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.4.p.0")));
        ignitingBlackhole.add(new PageIRecipe(registerRecipes.blockRedstoneInterface));
        ignitingBlackhole.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.4.p.1")));

        entries.put(new ResourceLocation("enhancedportals", "enhancedportals.manual.chapter.2.title"));
        entries.add(new EntryUniText(buildingTheHarness, "enhancedportals.manual.chapter.3.title"));
        entries.add(new EntryUniText(ignitingBlackhole, "enhancedportals.manual.chapter.4.title"));


        return entries;
    }
}

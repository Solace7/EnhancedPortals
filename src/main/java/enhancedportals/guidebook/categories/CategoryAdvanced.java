package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.page.PageIRecipe;
import enhancedportals.registration.RegisterRecipes;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryAdvanced
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
        List<IPage> phoneHome = new ArrayList<IPage>();

        phoneHome.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.5.p.0")));
        phoneHome.add(new PageIRecipe(RegisterRecipes.blockDiallingDevice));

        List<IPage> accessorizing = new ArrayList<IPage>();
        //recipe for nanobrush, portal manipulator, and blank portal module
        accessorizing.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.6.p.0")));
        accessorizing.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.6.p.1")));
        accessorizing.add(new PageIRecipe(RegisterRecipes.itemNanobrush));
        accessorizing.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.6.p.2")));
        accessorizing.add(new PageIRecipe(RegisterRecipes.blockModuleManipulator));
        accessorizing.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.6.p.3")));

        /*entries.add(new EntryUniText(phoneHome, "enhancedportals.manual.chapter.5.title"));
        entries.add(new EntryUniText(accessorizing, "enhancedportals.manual.chapter.6.title"));*/

        return entries;
    }
}

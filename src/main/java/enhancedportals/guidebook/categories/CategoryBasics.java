package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryBasics
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        List<IPage> prelude = new ArrayList<IPage>();
        prelude.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.0.p.0")));

        List<IPage> basics = new ArrayList<IPage>();
        basics.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.1.p.0")));
        basics.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.1.p.1")));
        basics.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.chapter.1.p.2")));


        return entries;
    }

}

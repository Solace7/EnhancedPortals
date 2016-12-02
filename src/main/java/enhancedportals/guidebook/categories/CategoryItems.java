package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import enhancedportals.item.ItemWrench;
import enhancedportals.registration.registerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static enhancedportals.guidebook.WormholeTunnelManual.categories;

public class CategoryItems
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        List<IPage> wrench = new ArrayList<IPage>();

        wrench.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.wrench.info")));
        wrench.add(new PageIRecipe(registerRecipes.itemWrench));

        List<IPage> nanobrush = new ArrayList<IPage>();
        nanobrush.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.nanobrush.info")));
        nanobrush.add(new PageIRecipe(registerRecipes.itemNanobrush));

        List<IPage> glasses = new ArrayList<IPage>();
        glasses.addAll((PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.glasses.info"))));
        glasses.add(new PageIRecipe(registerRecipes.itemGlasses));

        List<IPage> locationCard = new ArrayList<IPage>();
        locationCard.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.location_card.info")));
        locationCard.add(new PageIRecipe(registerRecipes.itemLocationCard));

        List<IPage> addressBook = new ArrayList<IPage>();
        addressBook.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.address_book.info")));
        //addressBook.add(new PageIRecipe(registerRecipes.itemAddressBook));

        entries.add(new EntryUniText(wrench, "enhancedportals.manual.wrench.title"));
        entries.add(new EntryUniText(nanobrush, "enhancedportals.manual.nanobrush.title"));
        entries.add(new EntryUniText(glasses, "enhancedportals.manual.glasses.title"));
        entries.add(new EntryUniText(locationCard, "enhancedportals.manual.location_card.title"));
        entries.add(new EntryUniText(addressBook, "enhancedportals.manual.address_book.title"));

        return entries;
    }
}

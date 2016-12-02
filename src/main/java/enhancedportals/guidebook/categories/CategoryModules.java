package enhancedportals.guidebook.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import enhancedportals.item.ItemBlankPortalModule;
import enhancedportals.registration.registerRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static enhancedportals.guidebook.WormholeTunnelManual.categories;

public class CategoryModules
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        //list all the modules and what they do
        List<IPage> blankModule = new ArrayList<IPage>();

        blankModule.addAll(PageHelper.pagesForLongText(TextHelper.localize("enhancedportals.manual.blank_module.info")));
        blankModule.add(new PageIRecipe(registerRecipes.blockModuleManipulator));

        List<IPage> particleDestroy = new ArrayList<IPage>();
        particleDestroy.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.0.desc")));
        particleDestroy.add(new PageIRecipe(registerRecipes.particleRemoveModule));

        List<IPage> particleRainbow = new ArrayList<IPage>();
        particleRainbow.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.1.desc")));
        particleRainbow.add(new PageIRecipe(registerRecipes.rainbowParticlesModule));

        List<IPage> silencer = new ArrayList<IPage>();
        silencer.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.2.desc")));
        silencer.add(new PageIRecipe(registerRecipes.silencerUpgrade));

        List<IPage> momentum = new ArrayList<IPage>();
        momentum.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.3.dec")));
        momentum.add(new PageIRecipe(registerRecipes.keepMomentumUpgrade));

        List<IPage> cloaking = new ArrayList<IPage>();
        cloaking.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal.module.4.desc")));

        List<IPage> particleShader = new ArrayList<IPage>();
        particleShader.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.5.desc")));
        particleShader.add(new PageIRecipe(registerRecipes.tintUpgrade));

        List<IPage> facing = new ArrayList<IPage>();
        facing.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.6.desc")));
        facing.add(new PageIRecipe(registerRecipes.facingUpgrade));

        List<IPage> featherfall = new ArrayList<IPage>();
        featherfall.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal_module.7.desc")));
        featherfall.add(new PageIRecipe(registerRecipes.featherfallUpgrade));

        List<IPage> lookingGlass = new ArrayList<IPage>();
        lookingGlass.addAll(PageHelper.pagesForLongText(TextHelper.localize("item.portal.module.8.desc")));
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


        return entries;
    }
}

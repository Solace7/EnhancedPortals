package enhancedportals.guidebook;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.item.ItemGuideBook;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.block.BlockFrame;
import enhancedportals.guidebook.categories.*;
import enhancedportals.item.ItemBlankPortalModule;
import enhancedportals.item.ItemBlankUpgrade;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.item.ItemWrench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.awt.*;
import java.util.ArrayList;


public class WormholeTunnelManual extends ItemGuideBook
{
    public static Book epManual;
    public static java.util.List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

    public static  void buildManual()
    {
        epManual = new Book();

        epManual.setTitle("guide.enhancedportals.book.title");
        epManual.setWelcomeMessage("guide.enhancedportals.book.welcomeMessage");
        epManual.setColor(new Color(177, 68, 203));
        epManual.setAuthor("guide.enhancedportals.book.author");
        epManual.setRegistryName("guide_book");
        epManual.setDisplayName("guide.enhancedportals.book.name");
        epManual.setCategoryList(categories);

        categories.add(new CategoryItemStack(CategoryBasics.buildCategory(), "guide.EnhancedPortals.category.basics", new ItemStack(ItemLocationCard.instance)));
        categories.add(new CategoryItemStack(CategoryBlocks.buildCategory(), "guide.EnhancedPortals.category.blocks", new ItemStack(BlockDecorEnderInfusedMetal.instance)));
        categories.add(new CategoryItemStack(CategoryTravel.buildCategory(), "guide.EnhancedPortals.category.travel", new ItemStack(BlockFrame.instance)));
        categories.add(new CategoryItemStack(CategoryAdvanced.buildCategory(), "guide.EnhancedPortals.category.advanced", new ItemStack(ItemBlankUpgrade.instance)));
        categories.add(new CategoryItemStack(CategoryModules.buildCategory(), "guide.EnhancedPortals.category.modules", new ItemStack(ItemBlankPortalModule.instance)));
        categories.add(new CategoryItemStack(CategoryItems.buildCategory(), "guide.EnhancedPortals.category.item", new ItemStack(ItemWrench.instance)));


        GameRegistry.register(epManual);

    }

    public static void registerCategories() {

    }

}

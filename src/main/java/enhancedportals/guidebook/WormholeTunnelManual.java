package enhancedportals.guidebook;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.category.CategoryItemStack;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.block.BlockFrame;
import enhancedportals.guidebook.categories.*;
import enhancedportals.item.ItemBlankPortalModule;
import enhancedportals.item.ItemBlankUpgrade;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.item.ItemWrench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;


public class WormholeTunnelManual
{
    public static Book epManual;

    public static void registerManual()
    {
        epManual = new Book();

        epManual.setTitle("guide.EnhancedPortals.book.title");
        epManual.setWelcomeMessage("guide.EnhancedPortals.book.welcomeMessage");
        epManual.setColor(new Color(142, 58, 166));
        epManual.setAuthor("guide.EnhancedPortals.book.author");
        epManual.setRegistryName("EnhancedPortals");


        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        { GuideAPI.setModel(epManual); }
    }

    public static void registerCategories() {

        epManual.addCategory(new CategoryItemStack(CategoryBasics.buildCategory(), "guide.EnhancedPortals.category.basics", new ItemStack(ItemLocationCard.instance)));
        epManual.addCategory(new CategoryItemStack(CategoryBlocks.buildCategory(), "guide.EnhancedPortals.category.blocks", new ItemStack(BlockDecorEnderInfusedMetal.instance)));
        epManual.addCategory(new CategoryItemStack(CategoryTravel.buildCategory(), "guide.EnhancedPortals.category.travel", new ItemStack(BlockFrame.instance)));
        epManual.addCategory(new CategoryItemStack(CategoryAdvanced.buildCategory(), "guide.EnhancedPortals.category.advanced", new ItemStack(ItemBlankUpgrade.instance)));
        epManual.addCategory(new CategoryItemStack(CategoryModules.buildCategory(), "guide.EnhancedPortals.category.modules", new ItemStack(ItemBlankPortalModule.instance)));
        epManual.addCategory(new CategoryItemStack(CategoryItems.buildCategory(), "guide.EnhancedPortals.category.items", new ItemStack(ItemWrench.instance)));
    }

}

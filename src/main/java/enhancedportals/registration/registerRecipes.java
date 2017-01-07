package enhancedportals.registration;

import amerifrance.guideapi.api.GuideRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import enhancedportals.block.BlockDecorBorderedQuartz;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.item.*;
import enhancedportals.utility.RecipeRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static cpw.mods.fml.common.registry.GameRegistry.addShapedRecipe;
import static enhancedportals.guidebook.WormholeTunnelManual.epManual;

public class registerRecipes
{
    public static IRecipe blockStabilizer, itemAddressBook, itemWrench, blockFrame, blockRedstoneInterface, redstoneInterfaceUpgrade, itemBlankUpgrade, lookingGlassModule, itemBlankPortalModule, particleRemoveModule, rainbowParticlesModule, keepMomentumUpgrade, tintUpgrade, facingUpgrade, featherfallUpgrade, boarderedQuartzDecor, itemNanobrush, itemLocationCard, itemGlasses, itemGuideBook, enderInfusedMetalDecor, blockPortalController, blockDiallingDevice, blockModuleManipulator, blockEnergyTransfer, blockFluidTransfer, blockItemTranfer, blockNetworkInterface, silencerUpgrade, networkInterfaceUpgrade, diallingDeviceUpgrade, portalManipulatorUpgrade, transferFluidUpgrade, transferItemsUpgrade, transferEnergyUpgrade;

    public static void initShaped()
    {
        //////////////////
        //Shaped Recipes//
        //////////////////

        blockFrame = addShapedRecipe(new ItemStack(BlockFrame.instance, 4, 0),
                "SIS",
                "IQI",
                "SIS",
                'S', Blocks.stone, 'Q', Blocks.quartz_block, 'I', Items.iron_ingot);
        blockRedstoneInterface = GameRegistry.addShapedRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.REDSTONE_INTERFACE),
                " R ",
                "RFR",
                " R ",
                'F', new ItemStack(BlockFrame.instance, 1, 0), 'R', Items.redstone);
        GameRegistry.addShapedRecipe(new ItemStack(ItemUpgrade.instance, 1, 0),
                " R ",
                "RFR",
                " R ",
                'F', new ItemStack(ItemBlankUpgrade.instance), 'R', Items.redstone);
        redstoneInterfaceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        blockStabilizer = GameRegistry.addShapedRecipe(new ItemStack(BlockStabilizer.instance, 6),
                "QPQ",
                "PDP",
                "QPQ",
                'D', Items.diamond, 'Q', Blocks.iron_block, 'P', Items.ender_pearl);
        //  lookingGlassModule = GameRegistry.addShapedRecipe(new ItemStack(ItemPortalModule.instance, 1, 8),
        // "EGE",
        // "GBG",
        // "EGE",
        // 'E', Items.ender_pearl, 'G', Blocks.glass_pane, 'B', new ItemStack(ItemBlankPortalModule.instance));
        boarderedQuartzDecor = GameRegistry.addShapedRecipe(new ItemStack(BlockDecorBorderedQuartz.instance, 9),
                "SQS",
                "QQQ",
                "SQS",
                'S', Blocks.stone, 'Q', Blocks.quartz_block);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankPortalModule.instance),
                "NNN",
                "NIN",
                "NNN",
                'I', Items.iron_ingot, 'N', Items.gold_nugget));
        itemBlankPortalModule = RecipeRegistry.getLatestCraftingRecipe();
        featherfallUpgrade = GameRegistry.addShapedRecipe(new ItemStack(ItemPortalModule.instance, 1, 7),
                "FFF",
                "FXF",
                "FFF",
                'X', new ItemStack(ItemBlankPortalModule.instance), 'F', new ItemStack(Items.feather));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance), true,
                "WT ",
                "TS ",
                "  S",
                'W', Blocks.wool, 'T', Items.string, 'S', "stickWood"));
        itemNanobrush = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance),
                " TW",
                " ST",
                "S  ",
                'W', Blocks.wool, 'T', Items.string, 'S', "stickWood"));
        itemNanobrush = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLocationCard.instance, 16),
                "IPI",
                "PPP",
                "IDI",
                'I', Items.iron_ingot, 'P', Items.paper, 'D', "dyeBlue"));
        itemLocationCard = RecipeRegistry.getLatestCraftingRecipe();
        itemWrench = GameRegistry.addShapedRecipe(new ItemStack(ItemWrench.instance),
                "I I",
                " Q ",
                " I ",
                'I', Items.iron_ingot, 'Q', Items.quartz);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemGlasses.instance), true,
                "R B",
                "GLG",
                "L L",
                'R', "dyeRed", 'B', "dyeCyan", 'G', Blocks.glass_pane, 'L', Items.leather));
        itemGlasses = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankUpgrade.instance, 8, 0),
                "D",
                "P",
                "R",
                'P', Items.paper, 'D', Items.diamond, 'R', "dyeRed"));
        itemBlankUpgrade = RecipeRegistry.getLatestCraftingRecipe();
    }

    public static void initShapeless()
    {

        /////////////////////
        //Shapeless Recipes//
        /////////////////////

        GameRegistry.addShapelessRecipe(GuideRegistry.getItemStackForBook(epManual), new ItemStack(Items.book), new ItemStack(ItemLocationCard.instance));
        itemGuideBook = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockDecorEnderInfusedMetal.instance, 9), Blocks.iron_block, Items.iron_ingot, Items.iron_ingot, Items.ender_pearl, Items.ender_pearl);
        enderInfusedMetalDecor = RecipeRegistry.getLatestCraftingRecipe();
        //GameRegistry.addShapelessRecipe(new ItemStack(ItemAddressBook.instance), new ItemStack(Items.book), new ItemStack(BlockFrame.instance, 1, BlockFrame.DIALLING_DEVICE));
        //itemAddressBook = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.PORTAL_CONTROLLER), new ItemStack(BlockFrame.instance, 1, 0), Items.diamond);
        blockPortalController = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.DIALLING_DEVICE), new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), Items.diamond);
        blockDiallingDevice = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.MODULE_MANIPULATOR), new ItemStack(BlockFrame.instance, 1, 0), Items.diamond, Items.emerald, new ItemStack(ItemBlankPortalModule.instance));
        blockModuleManipulator = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_ENERGY), new ItemStack(BlockFrame.instance, 1, 0), Items.ender_pearl, Items.diamond, Blocks.redstone_block);
        blockEnergyTransfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_FLUID), new ItemStack(BlockFrame.instance, 1, 0), Items.ender_pearl, Items.diamond, Items.bucket);
        blockFluidTransfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_ITEM), new ItemStack(BlockFrame.instance, 1, 0), Items.ender_pearl, Items.diamond, Blocks.chest);
        blockItemTranfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), new ItemStack(BlockFrame.instance, 1, 0), Items.ender_pearl);
        blockNetworkInterface = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 0), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.redstone), new ItemStack(Items.gunpowder));
        particleRemoveModule = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 1), new ItemStack(ItemBlankPortalModule.instance), "dyeRed", "dyeBlue", "dyeGreen"));
        rainbowParticlesModule = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 2), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.redstone), new ItemStack(Blocks.noteblock));
        silencerUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 3), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Blocks.anvil), new ItemStack(Items.feather));
        keepMomentumUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 5), new ItemStack(ItemBlankPortalModule.instance), "dyeWhite", "dyeBlack"));
        tintUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 6), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.compass));
        facingUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 1), new ItemStack(ItemBlankUpgrade.instance), Items.ender_pearl);
        networkInterfaceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 2), new ItemStack(ItemUpgrade.instance, 1, 1), Items.diamond);
        diallingDeviceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 4), new ItemStack(ItemBlankUpgrade.instance), Items.diamond, Items.emerald, new ItemStack(ItemBlankPortalModule.instance));
        portalManipulatorUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 5), new ItemStack(ItemBlankUpgrade.instance), Items.ender_pearl, Items.diamond, Items.bucket);
        transferFluidUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 6), new ItemStack(ItemBlankUpgrade.instance), Items.ender_pearl, Items.diamond, Blocks.chest);
        transferItemsUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 7), new ItemStack(ItemBlankUpgrade.instance), Items.ender_pearl, Items.diamond, Blocks.redstone_block);
        transferEnergyUpgrade = RecipeRegistry.getLatestCraftingRecipe();
    }
}
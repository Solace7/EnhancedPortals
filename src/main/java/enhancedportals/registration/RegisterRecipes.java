package enhancedportals.registration;

import amerifrance.guideapi.api.GuideAPI;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static enhancedportals.guidebook.WormholeTunnelManual.epManual;
import static net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe;

public class RegisterRecipes
{
    public static IRecipe blockStabilizer, itemAddressBook, itemWrench, blockFrame, blockRedstoneInterface, redstoneInterfaceUpgrade, itemBlankUpgrade, itemBlankPortalModule, particleRemoveModule, rainbowParticlesModule, keepMomentumUpgrade, tintUpgrade, facingUpgrade, featherfallUpgrade, boarderedQuartzDecor, itemNanobrush, itemLocationCard, itemGlasses, ItemEPManual, enderInfusedMetalDecor, blockPortalController, blockDiallingDevice, blockModuleManipulator, blockEnergyTransfer, blockFluidTransfer, blockItemTranfer, blockNetworkInterface, silencerUpgrade, networkInterfaceUpgrade, diallingDeviceUpgrade, portalManipulatorUpgrade, transferFluidUpgrade, transferItemsUpgrade, transferEnergyUpgrade;

    public static void initShaped()
    {
        //////////////////
        //Shaped Recipes//
        //////////////////

        blockFrame = addShapedRecipe(new ItemStack(BlockFrame.instance, 4, 0),
                "SIS",
                "IQI",
                "SIS",
                'S', Blocks.STONE, 'Q', Blocks.QUARTZ_BLOCK, 'I', Items.IRON_INGOT);
        blockRedstoneInterface = GameRegistry.addShapedRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.REDSTONE_INTERFACE.ordinal()),
                " R ",
                "RFR",
                " R ",
                'F', new ItemStack(BlockFrame.instance, 1, 0), 'R', Items.REDSTONE);
        GameRegistry.addShapedRecipe(new ItemStack(ItemUpgrade.instance, 1, 0),
                " R ",
                "RFR",
                " R ",
                'F', new ItemStack(ItemBlankUpgrade.instance), 'R', Items.REDSTONE);
        redstoneInterfaceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        blockStabilizer = GameRegistry.addShapedRecipe(new ItemStack(BlockStabilizer.instance, 6),
                "QPQ",
                "PDP",
                "QPQ",
                'D', Items.DIAMOND, 'Q', Blocks.IRON_BLOCK, 'P', Items.ENDER_PEARL);
        //  lookingGlassModule = GameRegistry.addShapedRecipe(new ItemStack(ItemPortalModule.instance, 1, 8),
        // "EGE",
        // "GBG",
        // "EGE",
        // 'E', Items.ender_pearl, 'G', Blocks.glass_pane, 'B', new ItemStack(ItemBlankPortalModule.instance));
        boarderedQuartzDecor = GameRegistry.addShapedRecipe(new ItemStack(BlockDecorBorderedQuartz.instance, 9),
                "SQS",
                "QQQ",
                "SQS",
                'S', Blocks.STONE, 'Q', Blocks.QUARTZ_BLOCK);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankPortalModule.instance),
                "NNN",
                "NIN",
                "NNN",
                'I', Items.IRON_INGOT, 'N', Items.GOLD_NUGGET));
        itemBlankPortalModule = RecipeRegistry.getLatestCraftingRecipe();
        featherfallUpgrade = GameRegistry.addShapedRecipe(new ItemStack(ItemPortalModule.instance, 1, 7),
                "FFF",
                "FXF",
                "FFF",
                'X', new ItemStack(ItemBlankPortalModule.instance), 'F', new ItemStack(Items.FEATHER));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance), true,
                "WT ",
                "TS ",
                "  S",
                'W', Blocks.WOOL, 'T', Items.STRING, 'S', "stickWood"));
        itemNanobrush = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance),
                " TW",
                " ST",
                "S  ",
                'W', Blocks.WOOL, 'T', Items.STRING, 'S', "stickWood"));
        itemNanobrush = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLocationCard.instance, 16),
                "IPI",
                "PPP",
                "IDI",
                'I', Items.IRON_INGOT, 'P', Items.PAPER, 'D', "dyeBlue"));
        itemLocationCard = RecipeRegistry.getLatestCraftingRecipe();
        itemWrench = GameRegistry.addShapedRecipe(new ItemStack(ItemWrench.instance),
                "I I",
                " Q ",
                " I ",
                'I', Items.IRON_INGOT, 'Q', Items.QUARTZ);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemGlasses.instance), true,
                "R B",
                "GLG",
                "L L",
                'R', "dyeRed", 'B', "dyeCyan", 'G', Blocks.GLASS_PANE, 'L', Items.LEATHER));
        itemGlasses = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankUpgrade.instance, 8, 0),
                "D",
                "P",
                "R",
                'P', Items.PAPER, 'D', Items.DIAMOND, 'R', "dyeRed"));
        itemBlankUpgrade = RecipeRegistry.getLatestCraftingRecipe();
    }

    public static void initShapeless()
    {

        /////////////////////
        //Shapeless Recipes//
        /////////////////////

        GameRegistry.addShapelessRecipe(GuideAPI.getStackFromBook(epManual), new ItemStack(Items.BOOK), new ItemStack(ItemLocationCard.instance));
        ItemEPManual = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockDecorEnderInfusedMetal.instance, 9), Blocks.IRON_BLOCK, Items.IRON_INGOT, Items.IRON_INGOT, Items.ENDER_PEARL, Items.ENDER_PEARL);
        enderInfusedMetalDecor = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemAddressBook.instance), new ItemStack(Items.BOOK), new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.DIALLING_DEVICE.ordinal()));
        itemAddressBook = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.PORTAL_CONTROLLER.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.DIAMOND);
        blockPortalController = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.DIALLING_DEVICE.ordinal()), new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.NETWORK_INTERFACE.ordinal()), Items.DIAMOND);
        blockDiallingDevice = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.MODULE_MANIPULATOR.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.DIAMOND, Items.EMERALD, new ItemStack(ItemBlankPortalModule.instance));
        blockModuleManipulator = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.TRANSFER_ENERGY.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Blocks.REDSTONE_BLOCK);
        blockEnergyTransfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.TRANSFER_FLUID.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Items.BUCKET);
        blockFluidTransfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.TRANSFER_ITEM.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Blocks.CHEST);
        blockItemTranfer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.FrameType.NETWORK_INTERFACE.ordinal()), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL);
        blockNetworkInterface = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 0), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.REDSTONE), new ItemStack(Items.GUNPOWDER));
        particleRemoveModule = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 1), new ItemStack(ItemBlankPortalModule.instance), "dyeRed", "dyeBlue", "dyeGreen"));
        rainbowParticlesModule = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 2), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.NOTEBLOCK));
        silencerUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 3), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Blocks.ANVIL), new ItemStack(Items.FEATHER));
        keepMomentumUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 5), new ItemStack(ItemBlankPortalModule.instance), "dyeWhite", "dyeBlack"));
        tintUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemPortalModule.instance, 1, 6), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.COMPASS));
        facingUpgrade = RecipeRegistry.getLatestCraftingRecipe();

        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 1), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL);
        networkInterfaceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 2), new ItemStack(ItemUpgrade.instance, 1, 1), Items.DIAMOND);
        diallingDeviceUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 3), new ItemStack(ItemBlankUpgrade.instance), Items.DIAMOND, Items.EMERALD, new ItemStack(ItemBlankPortalModule.instance));
        portalManipulatorUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 4), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Items.BUCKET);
        transferFluidUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 5), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Blocks.CHEST);
        transferItemsUpgrade = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(ItemUpgrade.instance, 1, 6), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Blocks.REDSTONE_BLOCK);
        transferEnergyUpgrade = RecipeRegistry.getLatestCraftingRecipe();
    }
}
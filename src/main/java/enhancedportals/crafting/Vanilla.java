package enhancedportals.crafting;

import enhancedportals.block.BlockDecorBorderedQuartz;
import enhancedportals.block.BlockDecorEnderInfusedMetal;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockStabilizer;
import enhancedportals.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Vanilla {
    public static void registerRecipes() {

        //////////////////
        //Shaped Recipes//
        //////////////////

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 4, 0),
                "SIS",
                "IQI",
                "SIS",
            'S', Blocks.STONE, 'Q', Blocks.QUARTZ_BLOCK, 'I', Items.IRON_INGOT));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.REDSTONE_INTERFACE),
                " R ",
                "RFR",
                " R ",
            'F', new ItemStack(BlockFrame.instance, 1, 0), 'R', Items.REDSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 0),
                " R ",
                "RFR",
                " R ",
            'F', new ItemStack(ItemBlankUpgrade.instance), 'R', Items.REDSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockStabilizer.instance, 6),
                "QPQ",
                "PDP",
                "QPQ",
            'D', Items.DIAMOND, 'Q', Blocks.IRON_BLOCK, 'P', Items.ENDER_PEARL));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankPortalModule.instance), true,
                "NNN",
                "NIN",
                "NNN",
            'I', Items.IRON_INGOT, 'N', Items.GOLD_NUGGET ));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemBlankUpgrade.instance, 8, 0),
                "D",
                "P",
                "R",
            'P', Items.PAPER, 'D', Items.DIAMOND, 'R', "dyeRed"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 7),
                "FFF",
                "FXF",
                "FFF",
            'X', new ItemStack(ItemBlankPortalModule.instance), 'F', new ItemStack(Items.FEATHER)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance),
                "WT ",
                "TS ",
                "  S",
            'W', Blocks.WOOL, 'T', Items.STRING, 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemNanobrush.instance),
                " TW",
                " ST",
                "S  ",
            'W', Blocks.WOOL, 'T', Items.STRING, 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLocationCard.instance, 16),
                "IPI",
                "PPP",
                "IDI",
            'I', Items.IRON_INGOT, 'P', Items.PAPER, 'D', "dyeBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemWrench.instance),
                "I I",
                " Q ",
                " I ",
            'I', Items.IRON_INGOT, 'Q', Items.QUARTZ));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemGlasses.instance), true,
                "R B",
                "GLG",
                "L L",
            'R', "dyeRed", 'B', "dyeCyan", 'G', Blocks.GLASS_PANE, 'L', Items.LEATHER ));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDecorBorderedQuartz.instance, 9),
                "SQS",
                "QQQ",
                "SQS",
            'S', Blocks.STONE, 'Q', Blocks.QUARTZ_BLOCK));

        /////////////////////
        //Shapeless Recipes//
        /////////////////////


        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.PORTAL_CONTROLLER), new ItemStack(BlockFrame.instance, 1, 0), Items.DIAMOND));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.DIALLING_DEVICE), new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), Items.DIAMOND));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.MODULE_MANIPULATOR), new ItemStack(BlockFrame.instance, 1, 0), Items.DIAMOND, Items.EMERALD, new ItemStack(ItemBlankPortalModule.instance)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_ENERGY), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Blocks.REDSTONE_BLOCK));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_FLUID), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Items.BUCKET));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.TRANSFER_ITEM), new ItemStack(BlockFrame.instance, 1, 0), Items.ENDER_PEARL, Items.DIAMOND, Blocks.CHEST));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 2), new ItemStack(ItemUpgrade.instance, 1, 1), Items.DIAMOND));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 4), new ItemStack(ItemBlankUpgrade.instance), Items.DIAMOND, Items.EMERALD, new ItemStack(ItemBlankPortalModule.instance)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 7), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Blocks.REDSTONE_BLOCK));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 5), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Items.BUCKET));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 6), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL, Items.DIAMOND, Blocks.CHEST));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockDecorEnderInfusedMetal.instance, 9), Blocks.IRON_BLOCK, Items.IRON_INGOT, Items.IRON_INGOT, Items.ENDER_PEARL, Items.ENDER_PEARL));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 1), new ItemStack(ItemBlankUpgrade.instance), Items.ENDER_PEARL));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 0), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.REDSTONE), new ItemStack(Items.GUNPOWDER)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 1), new ItemStack(ItemBlankPortalModule.instance), "dyeRed", "dyeBlue", "dyeGreen"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 2), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.NOTEBLOCK)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 3), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Blocks.ANVIL), new ItemStack(Items.FEATHER)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 5), new ItemStack(ItemBlankPortalModule.instance), "dyeWhite", "dyeBlack"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 6), new ItemStack(ItemBlankPortalModule.instance), new ItemStack(Items.COMPASS)));
    }
}

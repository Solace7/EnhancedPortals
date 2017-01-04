package enhancedportals.utility;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class RecipeRegistry
{
    public static ArrayList<IRecipe> craftingRecipes = new ArrayList<IRecipe>();

    /**
     * Used to registerRender crafting recipes to the guide
     * Adapted from Tombenpotter code in Sanguimancy.src.main.java.tombenpotter.sanguimancy.registry.RecipeRegistry
     */
    public static IRecipe getLatestCraftingRecipe()
    {
        IRecipe rec = (IRecipe) CraftingManager.getInstance().getRecipeList().get(CraftingManager.getInstance().getRecipeList().size() - 1);
        craftingRecipes.add(rec);
        return craftingRecipes.get(craftingRecipes.size() - 1);
    }

}
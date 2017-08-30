package net.corecrafted.semcore.recipes;

import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class RegularRecipe extends BaseRecipe{
    boolean shaped=true;

    public RegularRecipe(LinkedList<ItemStack> recipe, ItemStack result,boolean shaped) {
        this.shaped = shaped;
        super.setRecipe(recipe);
        super.setResult(result);
    }
}

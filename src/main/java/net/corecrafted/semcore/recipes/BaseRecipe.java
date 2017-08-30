package net.corecrafted.semcore.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.LinkedList;

public abstract class BaseRecipe implements Recipe{
    private ItemStack result=new ItemStack(Material.AIR);
    private LinkedList<ItemStack> recipe=new LinkedList<>();

    @Override
    public ItemStack getResult() {
        return result;
    }

    public LinkedList<ItemStack> getRecipe() {
        return recipe;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public void setRecipe(LinkedList<ItemStack> recipe) {
        this.recipe = recipe;
    }
}

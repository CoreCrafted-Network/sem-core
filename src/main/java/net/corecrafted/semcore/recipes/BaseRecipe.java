package net.corecrafted.semcore.recipes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;

public abstract class BaseRecipe implements Recipe,Displayable{
    enum Type{
        REGULAR,FURNACE,ADVANCED
    }
    Type type;
    ItemStack[] recipe;
    ItemStack result;
    String title;

    public ItemStack[] getRecipe() {
        return recipe;
    }

    public void setRecipe(ItemStack[] recipe) {
        this.recipe = recipe;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean saveRecipe(){
        //TODO Save the recipe to file!
        return true;
    }
}

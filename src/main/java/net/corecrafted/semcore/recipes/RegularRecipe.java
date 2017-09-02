package net.corecrafted.semcore.recipes;

import net.corecrafted.semcore.utils.ColorParser;
import net.corecrafted.semcore.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RegularRecipe extends BaseRecipe {
    boolean shaped;

    public RegularRecipe(ItemStack[] recipe, ItemStack result, boolean shaped) {
        setRecipe(recipe);
        setResult(result);
        this.shaped = shaped;
    }


    @Override
    public void displayRecipe() {
        Inventory inv = Bukkit.createInventory(null,27, ColorParser.parse(title));
        ItemStack[] recipe = getRecipe();
        ItemStack bg = new ItemBuilder(Material.STAINED_GLASS_PANE,7,"").build();
        // Paint the background first
        for (int i=0; i<inv.getSize();i++){
            inv.setItem(i,bg);
        }

        // Place items down the grid
        inv.setItem(3,recipe[0]);
        inv.setItem(4,recipe[1]);
        inv.setItem(5,recipe[2]);
        inv.setItem(12,recipe[3]);
        inv.setItem(13,recipe[4]);
        inv.setItem(14,recipe[5]);
        inv.setItem(21,recipe[6]);
        inv.setItem(22,recipe[7]);
        inv.setItem(23,recipe[8]);

        // Place down the result
        inv.setItem(16,result);
    }
}

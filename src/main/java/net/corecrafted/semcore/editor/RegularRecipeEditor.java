package net.corecrafted.semcore.editor;

import net.corecrafted.semcore.recipes.RegularRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class RegularRecipeEditor implements RecipeEditor{

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Player player = ((Player) e.getPlayer());
        if (EditingPlayer.isContain(player)){
            // Wrapper for targeting only those player that are really editing recipe
            RegularRecipe recipe = new RegularRecipe(e.getInventory().getContents(),player.getInventory().getItemInMainHand(),EditingPlayer.getSet().get(player));
/           //TODO Recipe save format crisis
        }
    }

    @Override
    public void displayEditor(Player player) {

    }
}

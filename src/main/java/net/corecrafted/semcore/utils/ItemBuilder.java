package net.corecrafted.semcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemBuilder {
    private Material material;
    private int amount=1;
    private int damage=0;
    private String displayName;
    ArrayList<String> lore;


    public ItemBuilder(Material material, int amount , int damage, String displayName, ArrayList<String> lore) {
        this.material = material;
        this.amount = amount;
        this.damage = damage;
        this.displayName=displayName;
        this.lore = lore;
    }

    public ItemBuilder(Material material, int damage, String displayName) {
        this.material = material;
        this.damage = damage;
        this.displayName = displayName;

    }

    public ItemBuilder(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    public ItemBuilder(Material material, int damage, String displayName, ArrayList<String> lore) {
        this.material = material;
        this.damage = damage;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack build(){
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        item.setDurability((short) damage);
        if (displayName!=null || lore != null){
            ItemMeta itemMeta = item.getItemMeta();
            if (displayName!=null){
                itemMeta.setDisplayName(displayName);
            }
            if (lore!=null){
                itemMeta.setLore(lore);
            }
            item.setItemMeta(itemMeta);
            return item;
        }
        return item;
    }

}

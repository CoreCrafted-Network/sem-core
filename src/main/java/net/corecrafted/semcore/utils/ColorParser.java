package net.corecrafted.semcore.utils;

import org.bukkit.ChatColor;

/**
 * Created by ThisTNTSquid on 8/2/2017.
 */
public class ColorParser {
    public static String parse(String raw){
        return ChatColor.translateAlternateColorCodes('&',raw);
    }
}

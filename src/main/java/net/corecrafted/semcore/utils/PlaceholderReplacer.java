package net.corecrafted.semcore.utils;

import net.corecrafted.semcore.AppLaunch;

public class PlaceholderReplacer {
    public static String parse(String message ,AppLaunch plugin){
        message.replaceAll("%current_life%", "5")
                .replaceAll("%max_life%", "6")
                .replaceAll("%header%", plugin.getHeader());
        return message;
    }
}

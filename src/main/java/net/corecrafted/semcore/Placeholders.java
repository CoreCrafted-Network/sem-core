package net.corecrafted.semcore;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class Placeholders extends EZPlaceholderHook {
    private AppLaunch plugin;

    public Placeholders(AppLaunch plugin) {
        super(plugin, "semcore");
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if (p == null) {
            return "";
        }
        switch (s) {
            case "current_life":
                return  String.valueOf(new SEMUser(p, plugin).getLife());
            case "max_life":
                return String.valueOf(new SEMUser(p, plugin).getMax_life());
            case "header":
                return plugin.getHeader();
            default:
                return null;
        }

    }
}

package net.corecrafted.semcore;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

import java.util.Date;

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
                return String.valueOf(new SEMUser(p.getUniqueId(), plugin).getLife());
            case "max_life":
                return String.valueOf(new SEMUser(p.getUniqueId(), plugin).getMax_life());
            case "header":
                return plugin.getHeader();
            case "next_life_time":
                // Get unix time of next life of the user
                // Get Date object from the unix time
                // Convert the Date object to string
                // return the string
                // I'M A ONE LINER MUAHAHAA
                return new Date(new SEMUser(p.getUniqueId(), plugin).getNextLifeTime()).toString();
            default:
                return s;
        }

    }
}

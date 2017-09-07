package net.corecrafted.semcore.editor;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class EditingPlayer {
    private static HashMap<Player, Boolean> set = new HashMap<>();

    public void addPlayer(Player player, boolean shaped) {
        if (!set.containsKey(player)) {
            set.put(player, shaped);
        }
    }

    public static HashMap<Player, Boolean> getSet() {
        return set;
    }

    public static boolean isContain(Player player){
        return set.containsKey(player);
    }
}

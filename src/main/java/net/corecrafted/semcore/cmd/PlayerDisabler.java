package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import org.bukkit.entity.Player;

import java.util.Set;

public class PlayerDisabler {
    Set<Player> noJumpSet;
    Set<Player> noInventorySet;
    Set<Player> noSpeakSet;
    Set<Player> walkLagSet;

    AppLaunch plugin;

    public PlayerDisabler(AppLaunch plugin) {
        this.plugin = plugin;
    }

    public Set<Player> getNoJumpSet() {
        return noJumpSet;
    }

    public Set<Player> getNoInventorySet() {
        return noInventorySet;
    }

    public Set<Player> getNoSpeakSet() {
        return noSpeakSet;
    }

    public Set<Player> getWalkLagSet() {
        return walkLagSet;
    }
}

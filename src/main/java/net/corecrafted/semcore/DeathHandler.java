package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeathHandler implements Listener {
    AppLaunch plugin;

    public DeathHandler(AppLaunch plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws SQLException {
        Player p = e.getEntity();
        if (p.getLastDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && p.getLastDamageCause().getEntity() instanceof Player) {
            p.spigot().respawn();
            p.sendMessage(ColorParser.parse(plugin.getHeader()+" &6You just killed by a player and this death will NOT deduct your life"));
            p.sendMessage(ColorParser.parse(plugin.getHeader()+" &6Better luck next time :)"));
            return;
        } else {
//            PreparedStatement stmt = plugin.getDbConnection().prepareStatement("SELECT  ");
//            stmt.exec
            p.sendMessage("You fucking hell died");

        }
    }
}

package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DeathHandler implements Listener {
    private AppLaunch plugin;

    public DeathHandler(AppLaunch plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) throws SQLException {
        Player p = e.getEntity();
        if (!(p.getKiller() instanceof Player)) {
            // Handle Non-player kills (zombie, creepers, fall etc)
            List<String> deathMsg = (List<String>) plugin.getMessages().getList("normal-death");
            deathMsg.forEach(msg -> p.sendMessage(ColorParser.parse(msg.replaceAll("%current_life%", "5").replaceAll("%max_life%", "6").replaceAll("%header%", plugin.getHeader()))));
            PreparedStatement stmt = plugin.getDbConnection().prepareStatement("INSERT INTO sem_core.player_death_rec(uuid, cause, loc_world, loc_x, loc_y, loc_z, datetime) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
            stmt.setString(2, e.getDeathMessage());
            stmt.setString(3, p.getWorld().getName());
            stmt.setDouble(4, p.getLocation().getX());
            stmt.setDouble(5, p.getLocation().getY());
            stmt.setDouble(6, p.getLocation().getZ());
            stmt.setLong(7, System.currentTimeMillis() / 1000);
            stmt.execute();

        } else {
            // Handle Player kills , which does not count
            p.spigot().respawn();
            p.sendMessage(ColorParser.parse(plugin.getHeader() + " &6You just killed by a player and this death will NOT deduct your life"));
            p.sendMessage(ColorParser.parse(plugin.getHeader() + " &6Better luck next time :)"));
            return;
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {


    }
}

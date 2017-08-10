package net.corecrafted.semcore;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.awt.geom.RectangularShape;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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

            // Add a death record
            PreparedStatement stmt = plugin.getDbConnection().prepareStatement("INSERT INTO sem_core.player_death_rec(uuid, cause, loc_world, loc_x, loc_y, loc_z, datetime) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
            stmt.setString(2, e.getDeathMessage());
            stmt.setString(3, p.getWorld().getName());
            stmt.setDouble(4, p.getLocation().getX());
            stmt.setDouble(5, p.getLocation().getY());
            stmt.setDouble(6, p.getLocation().getZ());
            stmt.setLong(7, System.currentTimeMillis() / 1000);
            stmt.execute();

            // Take one life away from that player
            stmt = plugin.getDbConnection().prepareStatement("UPDATE player_lifes SET current_life=current_life-1 WHERE uuid=?");
            stmt.setString(1, p.getUniqueId().toString().replaceAll("-",""));
            stmt.execute();

            // Display message to the player
            List<String> deathMsg = (List<String>) plugin.getMessages().getList("normal-death");
            deathMsg.forEach(msg -> p.sendMessage(ColorParser.parse(PlaceholderAPI.setPlaceholders(p, msg))));

            //check if the player is out of life
            SEMUser user = new SEMUser(p, plugin);
            if (user.isOutOfLife()) {
                plugin.sendPlayerToServer(p, "hub");
            }

        } else {
            // Handle Player kills , which does not count
            List<String> deathMsg = (List<String>) plugin.getMessages().getList("player-kill-death");
            p.spigot().respawn();
            p.spigot().respawn();
            deathMsg.forEach(msg -> p.sendMessage(ColorParser.parse(PlaceholderAPI.setPlaceholders(p, msg))));
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = plugin.getLuckPermsApi().getUser(p.getUniqueId());
        try {
            //check if it is new player
            PreparedStatement statement = plugin.getDbConnection().prepareStatement("SELECT * FROM player_lifes WHERE uuid=?");
            statement.setString(1, e.getPlayer().getUniqueId().toString().replaceAll("-",""));
            ResultSet res = statement.executeQuery();
            // if is new player (no record)
            if (!res.next()) {

                statement = plugin.getDbConnection().prepareStatement("INSERT INTO player_lifes (uuid, current_life, max_life) VALUES (?,?,?)");
                statement.setString(1, p.getUniqueId().toString().replaceAll("-",""));
                statement.setInt(2, plugin.getConfig().getInt("initial_life"));
                Set<String> ranks = plugin.getConfig().getConfigurationSection("max_life").getKeys(false);
                if (ranks.contains(user.getPrimaryGroup())) {
                    statement.setInt(3, plugin.getConfig().getInt("max_life."+user.getPrimaryGroup()));
                } else {
                    statement.setInt(3, plugin.getConfig().getInt("max_life.staff"));
                }
                statement.execute();

                // it is not a new player
            } else {
                if (res.getInt(2)<=0){
                    plugin.sendPlayerToServer(p,"hub");
                }
                statement = plugin.getDbConnection().prepareStatement("UPDATE player_lifes SET max_life=? WHERE uuid=?");
                Set<String> ranks = plugin.getConfig().getConfigurationSection("max_life").getKeys(false);
                if (ranks.contains(user.getPrimaryGroup())) {
                    statement.setInt(1, plugin.getConfig().getInt("max_life."+user.getPrimaryGroup()));
                } else {
                    statement.setInt(1, plugin.getConfig().getInt("max_life.staff"));
                }
                statement.setString(2, p.getUniqueId().toString().replaceAll("-",""));
                statement.execute();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        SEMUser u = new SEMUser(e.getPlayer(), plugin);
        if (u.isOutOfLife()) {
            plugin.sendPlayerToServer(e.getPlayer(), "hub");
        }

    }
}

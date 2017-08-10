package net.corecrafted.semcore;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SEMUser {
    private Player player;
    private AppLaunch plugin;

    public SEMUser(Player p, AppLaunch plugin) {
        this.player = p;
        this.plugin = plugin;
    }

    public String getLife() {
        try {
            PreparedStatement statement = plugin.getDbConnection().prepareStatement("SELECT current_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet res = statement.executeQuery();
            return res.getString("current_life");
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

    }

    public String getMax_life() {
        try {
            PreparedStatement statement = plugin.getDbConnection().prepareStatement("SELECT max_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet res = statement.executeQuery();
            return res.getString("max_life");
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

    }

    public boolean isOutOfLife(){
        int life = Integer.parseInt(getLife());
        return (life<=0);
    }

}

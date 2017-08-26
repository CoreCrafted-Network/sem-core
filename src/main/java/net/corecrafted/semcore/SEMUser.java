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

    public int getLife() {
        try {
            PreparedStatement statement = plugin.getDbConnection().prepareStatement("SELECT current_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString().replaceAll("-",""));
            ResultSet res = statement.executeQuery();
            plugin.getDbConnection().close();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return -1;
        }

    }

    public int getMax_life() {
        try {
            PreparedStatement statement = plugin.getDbConnection().prepareStatement("SELECT max_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString().replaceAll("-",""));
            ResultSet res = statement.executeQuery();
            plugin.getDbConnection().close();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return -1;
        }

    }

    public boolean isOutOfLife(){
        int life = getLife();
        return (life<=0);
    }

}

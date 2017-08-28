package net.corecrafted.semcore;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Map;

public class SEMUser {
    private Player player;
    private AppLaunch plugin;

    public SEMUser(Player p, AppLaunch plugin) {
        this.player = p;
        this.plugin = plugin;
    }

    public int getLife() {
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {

            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));

            PreparedStatement statement = connection.prepareStatement("SELECT current_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString().replaceAll("-", ""));
            ResultSet res = statement.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMax_life() {
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {
            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));
            PreparedStatement statement = connection.prepareStatement("SELECT max_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, player.getUniqueId().toString().replaceAll("-", ""));
            ResultSet res = statement.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLife(int life){
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {
            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));
            PreparedStatement statement = connection.prepareStatement("UPDATE player_lifes SET current_life=? WHERE uuid=?");
            statement.setInt(1,life);
            statement.setString(2, player.getUniqueId().toString().replaceAll("-", ""));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isOutOfLife() {
        int life = getLife();
        return (life <= 0);
    }

}

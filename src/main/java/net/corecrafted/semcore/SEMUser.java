package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.UUIDFetcher;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class SEMUser {
    private AppLaunch plugin;
    private UUID uuid;

    public SEMUser(UUID uuid, AppLaunch plugin){
        this.uuid= uuid;
        this.plugin=plugin;
    }

    public SEMUser(String playername, AppLaunch plugin){
        this.plugin=plugin;
        String noDash = new UUIDFetcher(plugin).fetchUUID(playername);
        // ecee956b3ffa4796b51abeefa7c3854b
        // ecee956b-3ffa-4796-b51a-beefa7c3854b
        this.uuid= UUID.fromString(noDash.substring(0,8)+"-"+noDash.substring(8,12)+"-"+noDash.substring(12,16)+"-"+noDash.substring(16,20)+"-"+noDash.substring(20));
    }

    public int getLife() {
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {

            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));
            PreparedStatement statement = connection.prepareStatement("SELECT current_life FROM player_lifes WHERE uuid=?");
            statement.setString(1, uuid.toString().replaceAll("-", ""));
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
            statement.setString(1, uuid.toString().replaceAll("-", ""));
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
            statement.setString(2, uuid.toString().replaceAll("-", ""));
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
    public void addLife(int number){
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {
            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));
            PreparedStatement statement = connection.prepareStatement("UPDATE player_lifes SET current_life=? WHERE uuid=?");
            statement.setInt(1,getLife()+number);
            statement.setString(2, uuid.toString().replaceAll("-", ""));
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

    // Return when will the next life come back
    public long getNextLifeTime(){
        return plugin.getLifeGenerator().getGenerateSet().get(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }
}

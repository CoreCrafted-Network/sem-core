package net.corecrafted.semcore.utils;

import net.corecrafted.semcore.AppLaunch;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class UUIDFetcher {
    private AppLaunch plugin;

    public UUIDFetcher(AppLaunch plugin) {
        this.plugin = plugin;
    }


    public String fetchUUID(String playername){
        Map map = plugin.getDbConnInfo();
        Connection connection=null;
        try {

            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("players_central"), (String) map.get("username"), (String) map.get("password"));
            PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM uuid_map WHERE playername=?");
            statement.setString(1, playername);
            ResultSet res = statement.executeQuery();
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return "null";
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public String fetchName(String uuid){
//        UUID id=UUID.fromString(uuid);
//    }
}

package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


public class AppLaunch extends JavaPlugin {
    private PluginDescriptionFile pdf = getDescription();
    private ConsoleCommandSender console = getServer().getConsoleSender();
    private File configf, messagesf;
    private FileConfiguration config, messages;
    private Connection connection;

    private String header = "&7[&2SEM Core&7]";

    public void onEnable() {
        console.sendMessage(ColorParser.parse("&8-=-=-=-=-=-=-=[ &aSEM Core &8]=-=-=-=-=-=-="));
        console.sendMessage(ColorParser.parse("> Plugin: &2SEM Core"));
        console.sendMessage(ColorParser.parse("> Author: ThisTNTSquid"));
        console.sendMessage(ColorParser.parse("> Contributors: JC2048, ItzJacky, vincentc"));
        console.sendMessage(ColorParser.parse("> Version: &6" + pdf.getVersion()));
        console.sendMessage(ColorParser.parse("&8-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
        console.sendMessage(ColorParser.parse(header + " &7>> Loading Files....."));
        loadFiles();
        console.sendMessage(ColorParser.parse(header + " &7>> Testing connection with database server...."));
        testDbConn();
        console.sendMessage(ColorParser.parse(header + " &7>> Loading Database....."));
        checkTablesExist();
        console.sendMessage(ColorParser.parse(header + " &7>> Registering commands and events...."));
        getCommand("sem").setExecutor(new CommandHandler(this));
        getServer().getPluginManager().registerEvents(new DeathHandler(this), this);
        console.sendMessage(ColorParser.parse(header + " &7>> Initialization completed"));

        console.sendMessage(ColorParser.parse(header + " &aWelcome back to the reality"));
    }


    void loadFiles() {
        configf = new File(getDataFolder(), "config.yml");
        messagesf = new File(getDataFolder(), "messages.yml");

        if (!configf.exists()) {
            console.sendMessage(ColorParser.parse(header + " &8- Creating missing config.yml....."));
            configf.getParentFile().mkdir();
            saveResource("config.yml", false);
        }
        if (!messagesf.exists()) {
            console.sendMessage(ColorParser.parse(header + " &8- Creating missing messages.yml...."));
            messagesf.getParentFile().mkdir();
            saveResource("messages.yml", false);
        }
        config = new YamlConfiguration();
        messages = new YamlConfiguration();

        try {
            config.load(configf);
            console.sendMessage(ColorParser.parse(header + " &8- Config loaded"));
        } catch (IOException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> Unable to access config.yml , printing stacktrace..."));
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> config.yml is not valid, printing stacktrace..."));
            e.printStackTrace();
        }
        try {
            messages.load(messagesf);
            console.sendMessage(ColorParser.parse(header + " &8- Messages file loaded"));
            header = messages.getString("header");
        } catch (IOException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> Unable to access messages.yml , printing stacktrace..."));
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> messages.yml is invalid, printing stacktrace..."));
            e.printStackTrace();
        }

        console.sendMessage(ColorParser.parse(header + " &7>> Files loading finished :)"));
        header = messages.getString("header");
    }

    private void testDbConn() {
        Map map = getDbConnInfo();
        try {
            connection = DriverManager.getConnection("jdbc:" + (String) map.get("host") + "/" + (String) map.get("schema"), (String) map.get("username"), (String) map.get("password"));
            console.sendMessage(ColorParser.parse(header + " &7>> Database server is working properly :)"));
        } catch (SQLException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> Unable to connect to database, printing stacktrace..."));
            e.printStackTrace();
        }
    }

    private void checkTablesExist() {
        try {
            String sqlscript = "create table IF NOT EXISTS player_death_rec( id int auto_increment primary key, uuid varchar(32) null, cause varchar(100) null, loc_world varchar(45) null, loc_x double null, loc_y double null, loc_z double null, datetime bigint(10) null) ;";
            String sqlscript2 = " create table IF NOT EXISTS player_lifes( uuid varchar(32) not null primary key, current_life int null, max_life int null ) ;";
//            System.out.println(sqlscript);
//            System.out.println(sqlscript2);
            PreparedStatement stmt = connection.prepareStatement(sqlscript);
            stmt.execute();
            stmt = connection.prepareStatement(sqlscript2);
            stmt.execute();
        } catch (SQLException e) {
            console.sendMessage(ColorParser.parse(header + " &c>> Check failed since it is unable connect to database"));
            e.printStackTrace();
        }

    }

    private Map getDbConnInfo() {
        Map map = config.getConfigurationSection("database").getValues(true);
        return map;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public String getHeader() {
        return header;
    }

    public Connection getDbConnection() {
        return connection;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }
}

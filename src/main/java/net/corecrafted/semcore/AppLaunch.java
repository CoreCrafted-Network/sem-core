package net.corecrafted.semcore;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class AppLaunch extends JavaPlugin implements PluginMessageListener {
    private PluginDescriptionFile pdf = getDescription();
    private ConsoleCommandSender console = getServer().getConsoleSender();
    private FileConfiguration config, messages;
    private Connection connection;
    private LuckPermsApi luckPermsApi;
    private LifeGenerator lifeGenerator;

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
        getServer().getPluginManager().registerEvents(new EventsHandler(this), this);
        console.sendMessage(ColorParser.parse(header + " &7>> Hooking into PlaceholderAPI....."));
        hookPlaceholderAPI();
        console.sendMessage(ColorParser.parse(header + " &7>> Hooking into LuckPerms"));
        hookLuckPermsAPI();
        console.sendMessage(ColorParser.parse(header + " &7>> Setting up plugin messaging channel..."));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        lifeGenerator = new LifeGenerator(this);
        console.sendMessage(ColorParser.parse(header + " &7>> Loaded player generation set"));
        console.sendMessage(ColorParser.parse(header + " &2>> Initialization completed"));

        console.sendMessage(ColorParser.parse(header + " &aWelcome back to the reality"));
        console.sendMessage(ColorParser.parse(header + " --6 -= // /-"));
        console.sendMessage(ColorParser.parse(header + " - =    -  4"));
        console.sendMessage(ColorParser.parse(header + " ]= :  .1 98 |:,? 9"));
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void loadFiles() {

        if (!(getDataFolder().exists())) {
            getDataFolder().mkdir();
        }
        File configf = new File(getDataFolder(), "config.yml");
        File messagesf = new File(getDataFolder(), "messages.yml");
        if (!configf.exists()) {
            saveResource("config.yml", false);
            console.sendMessage(ColorParser.parse(header + " &8- Creating missing config.yml ....."));
        }
        if (!messagesf.exists()) {
            saveResource("messages.yml", false);
            console.sendMessage(ColorParser.parse(header + " &8- Creating missing messages.yml ....."));
        }
        config = new YamlConfiguration();
        messages = new YamlConfiguration();

        try {
            config.load(configf);
            messages.load(messagesf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
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
            console.sendMessage(ColorParser.parse(header + " &c>> Disabling plugin to prevent further damage"));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void checkTablesExist() {
        try {
            String sqlscript = "CREATE TABLE IF NOT EXISTS player_death_rec( id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(32) NULL, cause VARCHAR(100) NULL, loc_world VARCHAR(45) NULL, loc_x DOUBLE NULL, loc_y DOUBLE NULL, loc_z DOUBLE NULL, datetime BIGINT(10) NULL) ;";
            String sqlscript2 = " CREATE TABLE IF NOT EXISTS player_lifes( uuid VARCHAR(32) NOT NULL PRIMARY KEY, current_life INT NULL, max_life INT NULL ) ;";
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

    private void hookPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).hook();
            console.sendMessage(ColorParser.parse(header + " &8- Successfully hooked into LuckPerms"));
        } else {
            console.sendMessage(ColorParser.parse(header + " &c- PlceholderAPI not found, please make sure you have installed it"));
        }
    }

    public Map getDbConnInfo() {
        Map map = config.getConfigurationSection("database").getValues(true);
        return map;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public String getHeader() {
        return header;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }

    public void sendPlayerToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    private void hookLuckPermsAPI() {
        Optional<LuckPermsApi> provider = LuckPerms.getApiSafe();
        if (provider.isPresent()) {
            luckPermsApi = provider.get();
            console.sendMessage(ColorParser.parse(header + " &8- Successfully hooked into PlaceholderAPI"));
        } else {
            console.sendMessage(ColorParser.parse(header + " &c- LuckPerms not found, disabling plugin..."));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public LuckPermsApi getLuckPermsApi() {
        return luckPermsApi;
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {

    }

    public LifeGenerator getLifeGenerator() {
        return lifeGenerator;
    }

}

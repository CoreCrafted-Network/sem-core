package net.corecrafted.semcore;

import me.lucko.luckperms.api.User;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.UUID;

public class LifeGenerator {
    private HashMap<UUID, Long> generateSet = new HashMap<>();
    private AppLaunch plugin;

    public LifeGenerator(AppLaunch plugin) {
        this.plugin = plugin;
//        generateSet.put(UUID.fromString("ecee956b-3ffa-4796-b51a-beefa7c3854b"), 15113L);

        // Load generator data if it exists
        readFile();
//        System.out.println(plugin.getDataFolder().toString()+"\\regen.data");
        regenClock();
        saveFileClock();
    }

    private void readFile() {

        FileInputStream filein = null;
        try {
            filein = new FileInputStream(plugin.getDataFolder().toString() + "\\regen.data");
            ObjectInputStream in = new ObjectInputStream(filein);
            generateSet = (HashMap<UUID, Long>) in.readObject();
            in.close();
            filein.close();
        } catch (FileNotFoundException e) {
            plugin.getConsole().sendMessage(ColorParser.parse(plugin.getHeader() + " &7>> Regen Datafile not found, never mind"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void outputFile() {
        try {
            FileOutputStream fileout = new FileOutputStream(plugin.getDataFolder().toString() + "\\regen.data");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(generateSet);
            out.close();
            fileout.close();
            plugin.getConsole().sendMessage(ColorParser.parse(plugin.getHeader() + " &7>> Regen data saved"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start Life Regeneration clock
    private void regenClock() {
        // Give player a life when time is up
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
//            System.out.println("Life");
            Bukkit.getOnlinePlayers().forEach((player) -> {
                SEMUser user = new SEMUser(player.getUniqueId(), plugin);
                User luckpermUser = plugin.getLuckPermsApi().getUser(player.getUniqueId());
                // Check if the player is already inside

                if (user.getLife() < plugin.getConfig().getInt("regen_life." + luckpermUser.getPrimaryGroup())) {

                    // Schedule next regen time for player life less than regen limit
                    if (!generateSet.containsKey(player.getUniqueId())) {
                        generateSet.put(player.getUniqueId(), System.currentTimeMillis() / 1000 + plugin.getConfig().getInt("life-regen-interval") * 60);
                        if (plugin.getConfig().getBoolean("debug")) {
                            System.out.print("Added " + player.getName() + " to the regen set");
                        }

                    }
                }
//                System.out.println(player.getName());
            });
            try {
                generateSet.forEach((uuid, time) -> {
                    // Check for time up players
                    if (System.currentTimeMillis() / 1000 > generateSet.get(uuid)) {
                        SEMUser user = new SEMUser(uuid, plugin);
                        // if someone time up, add one life and move them away from the list (reschedule)
                        if (plugin.getConfig().getBoolean("debug")) {
                            plugin.getConsole().sendMessage(ColorParser.parse(plugin.getHeader() + " &8- 1 life regenerated for " + Bukkit.getOfflinePlayer(user.getUuid()).getName()));
                        }
                        user.addLife(1);
                        Bukkit.getPlayer(uuid).sendMessage(ColorParser.parse(plugin.getHeader() + " &a1 Life has been regenerated"));
                        generateSet.remove(uuid);
                    }
                });
            } catch (ConcurrentModificationException e){ }

        },2,40);
    }

    private void saveFileClock() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            outputFile();
        }, 1, plugin.getConfig().getInt("data-save-interval") * 60 * 20);
    }

    public HashMap<UUID, Long> getGenerateSet() {
        return generateSet;
    }
}

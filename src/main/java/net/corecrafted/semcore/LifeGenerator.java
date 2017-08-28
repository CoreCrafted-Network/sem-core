package net.corecrafted.semcore;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;

import java.io.*;
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
        outputFile();
//        System.out.println(plugin.getDataFolder().toString()+"\\regen.data");
        regenClock();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start Life Regeneration clock
    private void regenClock() {
        // Give player a life when time is up
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getOnlinePlayers().forEach((player) -> {
            SEMUser user = new SEMUser(player, plugin);
            User luckpermUser = plugin.getLuckPermsApi().getUser(player.getUniqueId());
            // Check if the player is already inside

            if (user.getLife() < plugin.getConfig().getInt("regen_life" + luckpermUser.getPrimaryGroup())) {
                // Schedule next regen time for player life less than regen limit
                generateSet.put(player.getUniqueId(), System.currentTimeMillis() / 1000 + plugin.getConfig().getInt("life-regen-interval") * 60);
                if (generateSet.containsKey(player.getUniqueId())) {
                    // Check for time up players
                    if (System.currentTimeMillis() / 1000 > generateSet.get(player.getUniqueId())) {
                        // if someone time up, add one life and move them away from the list (reschedule)
                        user.setLife(user.getLife() + 1);
                        generateSet.remove(player.getUniqueId());
                    }
                }
            }



        }), 1, 40);
    }

    private void saveFileClock() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::outputFile, 1, 2400);
    }


}

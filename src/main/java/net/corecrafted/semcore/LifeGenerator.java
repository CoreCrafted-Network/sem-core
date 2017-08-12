package net.corecrafted.semcore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class LifeGenerator {
    private HashMap<UUID, Long> generateSet = new HashMap<>();
    private AppLaunch plugin;

    public LifeGenerator(AppLaunch plugin) {
        this.plugin = plugin;
        generateSet.put(UUID.fromString("ecee956b-3ffa-4796-b51a-beefa7c3854b"), 15113L);
        outputFile();
//        System.out.println(plugin.getDataFolder().toString()+"\\regen.data");
    }

    private void outputFile() {
        try {
            FileOutputStream fileout = new FileOutputStream(plugin.getDataFolder().toString() + "\\regen.data");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(generateSet);
            out.close();
            fileout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

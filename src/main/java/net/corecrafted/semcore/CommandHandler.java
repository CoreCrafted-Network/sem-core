package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class CommandHandler implements CommandExecutor {
    private AppLaunch plugin;

    public CommandHandler(AppLaunch plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        // Help and invalid command handling
        if (args.length==0 || args[0].equals("help") || args[0].equals("?")) {
            sendMainHelp(commandSender);
            return true;
        }
        if (args[0].equals("reload")){
            plugin.loadFiles();
            commandSender.sendMessage(ColorParser.parse(plugin.getHeader()+" &aConfig and messages files reloaded"));
            return true;
        }
        if (args[0].equals("debug")){
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(plugin.getDataFolder()+"\\regen.data");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                HashMap map = (HashMap) in.readObject();
                in.close();
                fileIn.close();
                map.forEach((uuid,number)-> System.out.print(uuid.toString()+" | "+number));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if (args[0].equals("player")){
//            if (args.length==1){
//                displayPlayerHelp(sen);
//            }
//            Player p = Bukkit.getPlayer(args[1]);
//            if (p==null){
//                commandSender.sendMessage(ColorParser.parse(plugin.getHeader()+" &cThe player you targeting is not a player"));
//            }
//
//        }



        return true;
    }

//    private void displayPlayerHelp() {
//        if (sender.hasPermission("sem.control")) {
//            sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
//            sender.sendMessage(ColorParser.parse("&a/sem player disable &e- Display this help message"));
//            sender.sendMessage(ColorParser.parse("&a/sem player &e- Show a list of operations on players"));
//            sender.sendMessage(ColorParser.parse("&a/sem items &e- This option is not working"));
//            sender.sendMessage(ColorParser.parse("&a/sem reload &e- Reloads config and messages"));
//        } else {
//            sender.sendMessage(ColorParser.parse(plugin.getMessages().getString("no-permission").replaceAll("%header%",plugin.getHeader())));
//        }
//    }

    private void sendMainHelp(CommandSender sender) {
        if (sender.hasPermission("sem.control")) {
            sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
            sender.sendMessage(ColorParser.parse("&a/sem help,? &e- Display this help message"));
            sender.sendMessage(ColorParser.parse("&a/sem player &e- Show a list of operations on players"));
            sender.sendMessage(ColorParser.parse("&a/sem items &e- This option is not working"));
            sender.sendMessage(ColorParser.parse("&a/sem reload &e- Reloads config and messages"));
        } else {
            sender.sendMessage(ColorParser.parse(plugin.getMessages().getString("no-permission").replaceAll("%header%",plugin.getHeader())));
        }
    }


}

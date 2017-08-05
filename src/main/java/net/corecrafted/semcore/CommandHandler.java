package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        }



        return true;
    }

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

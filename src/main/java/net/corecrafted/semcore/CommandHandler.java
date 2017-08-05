package net.corecrafted.semcore;

import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandHandler implements CommandExecutor {
    private AppLaunch plugin;
    private String header;

    public CommandHandler(AppLaunch plugin) {
        this.plugin = plugin;
        header = plugin.getHeader();
    }

    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (args.length==0 || args[0].equals("help") || args[0].equals("?")) {
        //
            sendMainHelp(commandSender);

        } else {
            commandSender.sendMessage(ColorParser.parse(header + " "));
        }

        return true;
    }

    private void sendMainHelp(CommandSender sender) {
        if (sender.hasPermission("sem.control")) {
            sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
            sender.sendMessage(ColorParser.parse("&a/sem help,? &e- Display this help message"));
            sender.sendMessage(ColorParser.parse("&a/sem player &e- Show a list of operations on players"));
            sender.sendMessage(ColorParser.parse("&a/sem items &e- This option is not working"));
        } else {
            sender.sendMessage(ColorParser.parse(plugin.getMessages().getString("no-permission").replaceAll("%header%",plugin.getHeader())));
        }

    }
}

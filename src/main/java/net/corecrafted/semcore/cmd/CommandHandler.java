package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedList;

public class CommandHandler implements CommandExecutor {
    private AppLaunch plugin;

    public CommandHandler(AppLaunch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmdObj, String s, String[] args) {
        if (commandSender.hasPermission("sem.control")) {
            // Help and invalid command handling
            if (args.length == 0 || args[0].equals("help") || args[0].equals("?")) {
                sendMainHelp(commandSender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.loadFiles();
                commandSender.sendMessage(ColorParser.parse(plugin.getHeader() + " &aConfig and messages files reloaded"));
                return true;
            }
            if (args[0].equalsIgnoreCase("debug")) {
                plugin.getLifeGenerator().getGenerateSet().forEach((uuid, time) -> {
                    System.out.println(uuid.toString() + " | " + time);
                });
            }


            if (args[0].equalsIgnoreCase("player")) {
                if (args.length == 1) {
                    sendPlayerHelp(commandSender);
                } else {
                    LinkedList<String> list = new LinkedList<>(Arrays.asList(args));
                    list.pollFirst();
                    list.pollFirst();
                    return new PlayerCommand().execute(plugin, commandSender, cmdObj, s, args[1], list);
                }


            } else if (args[0].equalsIgnoreCase("recipe")) {
                if (args.length == 1) {
                    sendRecipesHelp(commandSender);
                } else {
                    LinkedList<String> list = new LinkedList<>(Arrays.asList(args));
                    list.pollFirst();
                    list.pollFirst();
                    return new RecipeCommand().execute(plugin,commandSender,cmdObj,s,args[1],list);
                }
            }
            return true;
        } else
            commandSender.sendMessage(ColorParser.parse(plugin.getMessages().getString("no-permission").replaceAll("%header%", plugin.getHeader())));
        return true;
    }

    private void sendRecipesHelp(CommandSender sender) {
        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
        sender.sendMessage(ColorParser.parse("&a/sem recipe set [regular|r] <id> <shaped?>"));
        sender.sendMessage(ColorParser.parse("&a/sem recipe set [furnace|f] <id>"));
        sender.sendMessage(ColorParser.parse("&a/sem recipe set [advanced|a] <id> <shaped?>"));
        sender.sendMessage(ColorParser.parse("&a/sem recipe remove <id>"));
    }


    private void sendPlayerHelp(CommandSender sender) {
        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
        sender.sendMessage(ColorParser.parse("&a/sem player look <player> &e- Return information of the player"));
        sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
        sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add life to a player"));
    }


//    private void sendItemsHelp(CommandSender sender) {
//        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
//        sender.sendMessage(ColorParser.parse("&a/sem item define <id> &e- Define an item to config"));
//    }


    private void sendMainHelp(CommandSender sender) {
        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
        sender.sendMessage(ColorParser.parse("&a/sem help,? &e- Display this help message"));
        sender.sendMessage(ColorParser.parse("&a/sem player &e- Show a list of operations on players"));
        sender.sendMessage(ColorParser.parse("&a/sem items &e- This option is not working"));
        sender.sendMessage(ColorParser.parse("&a/sem recipe &e- Show a list of recipes commands"));
        sender.sendMessage(ColorParser.parse("&a/sem reload &e- Reloads config and messages"));
    }
}


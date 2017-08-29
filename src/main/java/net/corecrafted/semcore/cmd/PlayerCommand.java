package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import net.corecrafted.semcore.SEMUser;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;

public class PlayerCommand implements BaseCommand {
    AppLaunch plugin;

    @Override
    public boolean execute(AppLaunch plugin, CommandSender sender, Command cmdObj, String label, String cmd, LinkedList<String> args) {
        //                       [cmd] | [args]-->>
        // example: /sem  player  life  set  ThisTNTSquid  50
        this.plugin = plugin;
        if (cmd.equalsIgnoreCase("life")) {
            //sem player life [?]
            if (args.size() > 0) {
                //TODO add life
                //TODO look life

                if (args.get(0).equalsIgnoreCase("set")){
                    //sem player life set [?]
                    if (args.size()>1 /* args.size()==2 */){
                        SEMUser user = new SEMUser(args.get(1),plugin);
                        //sem player life set ThisTNTSquid [?]
                        if (args.size()>2){
                            // Have value input
                            user.setLife(Integer.parseInt(args.get(2)));
                        } else {
                            // Did not put any value to set
                            sender.sendMessage(ColorParser.parse("&cPlease specify a value"));
                            sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));

                        }
                    } else{
                        // Did not input player
                        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                        sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
                    }
                }
            } else {
                // Did not input operation [set/add]
                sender.sendMessage(ColorParser.parse("&cYou entered too few arguments!"));
                sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
                sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add life to a player"));
            }
        }
        return true;
    }
}

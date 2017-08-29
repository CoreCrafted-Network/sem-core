package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import net.corecrafted.semcore.SEMUser;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                if (args.get(0).equalsIgnoreCase("set")){
                    //sem player life set [?]
                    if (args.size()>1 /* args.size()==2 */){
                        SEMUser user = new SEMUser(args.get(1),plugin);
                        //sem player life set ThisTNTSquid [?]
                        if (args.size()>2){
                            // Have value input
                            user.setLife(Integer.parseInt(args.get(2)));
                            sender.sendMessage(ColorParser.parse(plugin.getHeader() + " &a"+args.get(1)+"'s life has been set to "+args.get(2)));
//                            System.out.println(Integer.parseInt(args.get(2))+" "+user.getUuid());
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

                } else if (args.get(0).equalsIgnoreCase("add")){
                    //sem player life add [?]
                    if (args.size()>1 /* args.size()==2 */){
                        SEMUser user = new SEMUser(args.get(1),plugin);
                        //sem player life add ThisTNTSquid [?]
                        if (args.size()>2){
                            // Have value input
                            user.addLife(Integer.parseInt(args.get(2)));
                            sender.sendMessage(ColorParser.parse(plugin.getHeader() + " &aAdded "+args.get(2)+" lives to "+args.get(1)));
                        } else {
                            // Did not put any value to set
                            sender.sendMessage(ColorParser.parse("&cPlease specify a value"));
                            sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add the amount of life to the player"));

                        }
                    } else{
                        // Did not input player
                        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                        sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add the amount of life to the player"));
                    }
                } else{
                    // sem player life efwlefk(dunno whats that)
                    sender.sendMessage(ColorParser.parse("&cUnknown argument"));
                    sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                    sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
                    sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add life to a player"));
                }
            } else {
                // Did not input operation [set/add]
                sender.sendMessage(ColorParser.parse("&cYou entered too few arguments!"));
                sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
                sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add life to a player"));
            }
        } else if (cmd.equalsIgnoreCase("look")){
            //sem player look [?]
            if (args.size()>0){
                //TODO Player state query
                // Full state
                Player player = Bukkit.getPlayer(args.get(0));
                if (player!=null){
                    SEMUser user = new SEMUser(player.getUniqueId(),plugin);
                    //TODO Display stats
                } else {
                    // Targeting player that is not online / not exist
                    sender.sendMessage(ColorParser.parse(plugin.getHeader()+" &cThe player you targeting does not exist/ not online"));
                }
            } else {
                // Does not specify target
                sender.sendMessage(ColorParser.parse("&cYou didn't specify who you wanted to look at"));
                sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                sender.sendMessage(ColorParser.parse("&a/sem player look <player> &e- Return information of the player"));
            }
        }
        return true;
    }
}

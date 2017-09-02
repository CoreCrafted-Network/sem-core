package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import net.corecrafted.semcore.SEMUser;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

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
                if (args.get(0).equalsIgnoreCase("set")) {
                    //sem player life set [?]
                    if (args.size() > 1 /* args.size()==2 */) {
                        SEMUser user = new SEMUser(args.get(1), plugin);
                        //sem player life set ThisTNTSquid [?]
                        if (args.size() > 2) {
                            // Have value input
                            user.setLife(Integer.parseInt(args.get(2)));
                            sender.sendMessage(ColorParser.parse(plugin.getHeader() + " &a" + args.get(1) + "'s life has been set to " + args.get(2)));
//                            System.out.println(Integer.parseInt(args.get(2))+" "+user.getUuid());
                        } else {
                            // Did not put any value to set
                            sender.sendMessage(ColorParser.parse("&cPlease specify a value"));
                            sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));

                        }
                    } else {
                        // Did not input player
                        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                        sender.sendMessage(ColorParser.parse("&a/sem player life set <player> <amount> &e- Set the amount of life of the player"));
                    }

                } else if (args.get(0).equalsIgnoreCase("add")) {
                    //sem player life add [?]
                    if (args.size() > 1 /* args.size()==2 */) {
                        SEMUser user = new SEMUser(args.get(1), plugin);
                        //sem player life add ThisTNTSquid [?]
                        if (args.size() > 2) {
                            // Have value input
                            user.addLife(Integer.parseInt(args.get(2)));
                            sender.sendMessage(ColorParser.parse(plugin.getHeader() + " &aAdded " + args.get(2) + " lives to " + args.get(1)));
                        } else {
                            // Did not put any value to set
                            sender.sendMessage(ColorParser.parse("&cPlease specify a value"));
                            sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add the amount of life to the player"));

                        }
                    } else {
                        // Did not input player
                        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                        sender.sendMessage(ColorParser.parse("&a/sem player life add <player> <amount> &e- Add the amount of life to the player"));
                    }
                } else {
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
        } else if (cmd.equalsIgnoreCase("look")) {
            //sem player look [?]
            if (args.size() > 0) {
                // Full state
                Player player = Bukkit.getPlayer(args.get(0));
                if (player != null) {
                    sendPlayerInfo(player, sender);

                } else {
                    // Targeting player that is not online / not exist
                    sender.sendMessage(ColorParser.parse(plugin.getHeader() + " &cThe player you targeting does not exist/ not online"));
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

    private void sendPlayerInfo(Player p, CommandSender sender) {
        SEMUser user = new SEMUser(p.getUniqueId(), plugin);
        Location loc = p.getLocation();
        sender.sendMessage(ColorParser.parse("&6-=-=-=-=-=&6[&e " + p.getName() + " &6]=-=-=-=-=-"));
        sender.sendMessage(ColorParser.parse("&e> UUID: &6" + p.getUniqueId()));
        sender.sendMessage(ColorParser.parse("&e> Loc: (&6" + p.getWorld().getName() + "&e) &6" + loc.getBlockX() + "&e/&6" + loc.getBlockY() + "&e/&6" + loc.getBlockZ()));
        sender.sendMessage(ColorParser.parse("&e> Exp: &6"+p.getTotalExperience()+" &e(&6Lv"+p.getLevel()+"&e)"));
        sender.sendMessage(ColorParser.parse("&e> Health: &6"+p.getHealth()+"&e/&6"+p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
        sender.sendMessage(ColorParser.parse("&c> Life: "+user.getLife()+"/"+user.getMax_life()));
        long nextLifeTime = user.getNextLifeTime();
        if (nextLifeTime==0){
            sender.sendMessage(ColorParser.parse("&c> Next Life: Not needed"));
        } else {
            Date date = new Date(nextLifeTime*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            sender.sendMessage(ColorParser.parse("&c> Next Life: "+sdf.format(date)));
        }



    }
}

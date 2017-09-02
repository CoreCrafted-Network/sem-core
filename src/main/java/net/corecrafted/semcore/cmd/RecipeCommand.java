package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import net.corecrafted.semcore.utils.ColorParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
//TODO Change bad ass error messages
public class RecipeCommand implements BaseCommand {
    AppLaunch plugin;

    @Override
    public boolean execute(AppLaunch plugin, CommandSender sender, Command cmdObj, String label, String cmd, LinkedList<String> args) {
        //                [cmd]|[args] 0-->  1       2
        // /sem   recipe   set   regular   dmgear   true
        this.plugin = plugin;
        if (cmd.equalsIgnoreCase("set")) {
            //sem recipe set [?]
            if (args.size() > 0) {
                if (args.get(0).equalsIgnoreCase("regular") || args.get(0).equalsIgnoreCase("r")) {
                    //sem recipe set regular [?]
                    //TODO Regular recipe define
                    if (args.size()>1){
                        String id = args.get(1);
                        //sem recipe set regular dmgear [?]
                    } else {
                        // no id present
                        sender.sendMessage(ColorParser.parse("&cGive your recipe a name!"));
                        sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                        sender.sendMessage(ColorParser.parse("&a/sem recipe set [regular|r] <id> <shaped?>"));
                    }

                } else if (args.get(0).equalsIgnoreCase("furnace") || args.get(0).equalsIgnoreCase("f")) {
                    //sem recipe set furnace [?]
                    //TODO furnace recipe define


                } else if (args.get(0).equalsIgnoreCase("advanced") || args.get(0).equalsIgnoreCase("a")) {
                    //sem recipe set advanced [?]
                    //TODO advanced 4x4 recipe define

                } else {
                    // Unknown recipe type
                    sender.sendMessage(ColorParser.parse("&cWhat the fuck is this crappy recipe type?!"));
                    sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                    sender.sendMessage(ColorParser.parse("&a/sem recipe set [regular|r] <id> <shaped?>"));
                    sender.sendMessage(ColorParser.parse("&a/sem recipe set [furnace|f] <id>"));
                    sender.sendMessage(ColorParser.parse("&a/sem recipe set [advanced|a] <id> <shaped?>"));
                }

            } else {
                // Does not define what to set
                // -> "/sem recipe set"
                sender.sendMessage(ColorParser.parse("&cPlease specify the type of recipe [regular/furnace/advanced]"));
                sender.sendMessage(ColorParser.parse("&8-=-=-=-=-=[ &aSEM Core Help &8]=-=-=-=-=-"));
                sender.sendMessage(ColorParser.parse("&a/sem recipe set [regular|r] <id> <shaped?>"));
                sender.sendMessage(ColorParser.parse("&a/sem recipe set [furnace|f] <id>"));
                sender.sendMessage(ColorParser.parse("&a/sem recipe set [advanced|a] <id> <shaped?>"));
            }
        }

        return true;
    }
}

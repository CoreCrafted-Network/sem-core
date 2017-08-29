package net.corecrafted.semcore.cmd;

import net.corecrafted.semcore.AppLaunch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public interface BaseCommand {
    /**
     * @param plugin Instance of main class
     * @param sender The command sender (e.g. console, player, cmd block
     * @param cmdObj Command object of the command
     * @param label The base command of the command (e.g. /sem in this plugin
     * @param cmd Argument after the sub-command (e.g. "/sem player life", "life" is the cmd)
     * @param args Arguments follow after "cmd"
     * @return boolean value. recommended to keep "true"
     */
    boolean execute(AppLaunch plugin, CommandSender sender, Command cmdObj, String label, String cmd, LinkedList<String> args);
}

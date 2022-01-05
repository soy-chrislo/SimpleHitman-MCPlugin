package com.soychristian.simplehitman.commands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.subcommands.HuntCommand;
import com.soychristian.simplehitman.commands.subcommands.ReloadCommand;
import com.soychristian.simplehitman.commands.subcommands.RemoveUserListCommand;
import com.soychristian.simplehitman.commands.subcommands.ShowListCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    public static ArrayList<Player> listHitman = new ArrayList<>();
    public static ArrayList<String> listReward = new ArrayList<>();

    Simplehitman plugin;

    String pluginName = Simplehitman.pluginName;
    String availableCommands = plugin.getConfig().getString("available-commands");

    public CommandManager(Simplehitman plugin) {
        subCommands.add(new ReloadCommand(plugin));
        subCommands.add(new ShowListCommand(plugin));
        subCommands.add(new HuntCommand(plugin));
        subCommands.add(new RemoveUserListCommand(plugin));
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                    }
                }
            } else {
                player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', availableCommands));
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "• " + ChatColor.GRAY +"/sh hunt <player> <reward>");
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "• " + ChatColor.GRAY +"/sh list");
                if (player.hasPermission("simplehitman.reload")) {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "• " + ChatColor.GRAY +"/sh reload");
                }
                if (player.hasPermission("simplehitman.removelist")) {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "• " + ChatColor.GRAY +"/sh removelist <player>");
                }
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}

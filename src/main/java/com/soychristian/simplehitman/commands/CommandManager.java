package com.soychristian.simplehitman.commands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.subcommands.HuntCommand;
import com.soychristian.simplehitman.commands.subcommands.ReloadCommand;
import com.soychristian.simplehitman.commands.subcommands.RemoveFromListCommand;
import com.soychristian.simplehitman.commands.subcommands.ShowListContractsCommand;
import net.milkbowl.vault.economy.Economy;
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

    private final Economy eco = Simplehitman.getEconomy();

    String pluginName = Simplehitman.pluginName;
    String availableCommands = Simplehitman.getPlugin().getConfig().getString("available-commands");

    public CommandManager() {
        subCommands.add(new ReloadCommand());
        subCommands.add(new ShowListContractsCommand());
        subCommands.add(new HuntCommand());
        subCommands.add(new RemoveFromListCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // /simplehitman
        // Arreglar jerarquia de comandos, no es posible ejecutar comando remove
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

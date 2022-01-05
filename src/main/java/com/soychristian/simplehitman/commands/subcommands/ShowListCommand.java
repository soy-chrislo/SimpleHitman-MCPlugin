package com.soychristian.simplehitman.commands.subcommands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.CommandManager;
import com.soychristian.simplehitman.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShowListCommand extends SubCommand {
    Simplehitman plugin;
    String emptyList, messageList;

    public ShowListCommand(Simplehitman plugin) {
        this.plugin = plugin;
        this.emptyList = this.plugin.getConfig().getString("empty-list");
        this.messageList = this.plugin.getConfig().getString("message-list");
    }
    String pluginName = Simplehitman.pluginName;

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Show the list of active contracts.";
    }

    @Override
    public String getSyntax() {
        return "/simplehitman list";
    }

    @Override
    public void perform(Player player, String[] args) {
        int lenghtList = CommandManager.listHitman.size();
        if (CommandManager.listHitman.isEmpty()) {
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', emptyList));
        } else if (!CommandManager.listHitman.isEmpty()) {
            player.sendMessage(pluginName + messageList);
            for (int i = 0; i < lenghtList; i++) {
                int topCounter =+ 1;
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + topCounter + ". " +ChatColor.WHITE + CommandManager.listHitman.get(i).getName() + " - " + ChatColor.GREEN + "$" + CommandManager.listReward.get(i));
            }
        }

    }
}

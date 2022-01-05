package com.soychristian.simplehitman.commands.subcommands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.CommandManager;
import com.soychristian.simplehitman.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveUserListCommand extends SubCommand {
    Simplehitman plugin;
    String removeList, insufficientPermission;

    public RemoveUserListCommand(Simplehitman plugin) {
        this.plugin = plugin;
        this.removeList = this.plugin.getConfig().getString("remove-list");
        this.insufficientPermission = this.plugin.getConfig().getString("insufficient-permission");
    }

    String pluginName = Simplehitman.pluginName;

    @Override
    public String getName() {
        return "removelist";
    }

    @Override
    public String getDescription() {
        return "Remove a player from hitman list.";
    }

    @Override
    public String getSyntax() {
        return "/simplehitman removelist";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("simplehitman.removelist")) {
            Player target = Bukkit.getPlayerExact(args[1]);

            int indexBoth = CommandManager.listHitman.indexOf(target);

            CommandManager.listHitman.remove(indexBoth);
            CommandManager.listReward.remove(indexBoth);
            player.sendMessage(pluginName + ChatColor.GRAY + target.getName() + " " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', removeList));
        } else {
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', insufficientPermission));
        }


    }
}

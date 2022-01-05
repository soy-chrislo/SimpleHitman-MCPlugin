package com.soychristian.simplehitman.commands.subcommands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    Simplehitman plugin;

    public ReloadCommand(Simplehitman plugin) {
        this.plugin = plugin;
    }

    String reloadConfig = plugin.getConfig().getString("reload-config");
    String pluginName = Simplehitman.pluginName;
    String insufficientPermission = plugin.getConfig().getString("insufficient-permission");

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the configuration files plugin.";
    }

    @Override
    public String getSyntax() {
        return "/simplehitman reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("simplehitman.reload")) {
            plugin.reloadConfig();
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', reloadConfig));
        } else {
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', insufficientPermission));
        }
    }
}

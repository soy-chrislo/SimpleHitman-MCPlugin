package com.soychristian.simplehitman.commands.subcommands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    Simplehitman plugin;
    String reloadConfig, insufficientPermission;

    public ReloadCommand(Simplehitman plugin) {
        this.plugin = plugin;
        this.reloadConfig = this.plugin.getConfig().getString("reload-config");
        this.insufficientPermission = this.plugin.getConfig().getString("insufficient-permission");
    }

    String pluginName = Simplehitman.pluginName;

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

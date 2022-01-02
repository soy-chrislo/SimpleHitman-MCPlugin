package com.soychristian.simplehitman.listeners;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.CommandManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onKillPlayer implements Listener {
    private final Economy eco = Simplehitman.getEconomy();

    String pluginName = Simplehitman.pluginName;

    String killObjetive = Simplehitman.getPlugin().getConfig().getString("kill-objetive");
    String killHimselfReward = Simplehitman.getPlugin().getConfig().getString("kill-himself-reward");
    String broadcastKillObjetive = Simplehitman.getPlugin().getConfig().getString("broadcast-kill-objetive");

    @EventHandler
    public void onKillPlayerByPlayer(PlayerDeathEvent event) {
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (killer instanceof Player) {
            if (CommandManager.listHitman.contains(victim)) {
                if (victim.getName().equals(killer.getName())) {
                    victim.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', killHimselfReward));
                } else if (!victim.getName().equals(killer.getName())){
                    String reward = CommandManager.listReward.get(CommandManager.listHitman.indexOf(victim));
                    CommandManager.listReward.remove(CommandManager.listHitman.indexOf(victim));
                    CommandManager.listHitman.remove(victim);
                    eco.depositPlayer(killer, Double.parseDouble(reward));
                    killer.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', killObjetive) + " " + ChatColor.GREEN + "$" + reward + ".");
                    Bukkit.broadcastMessage(pluginName + ChatColor.GRAY + killer.getName() + ChatColor.WHITE +" " + ChatColor.translateAlternateColorCodes('&', broadcastKillObjetive) + " " + ChatColor.GRAY + victim.getName() + ChatColor.WHITE +" (" + ChatColor.GREEN + "$" + reward + ChatColor.WHITE + ").");
                }
            }

        }
    }
}


package com.soychristian.simplehitman.commands.subcommands;

import com.soychristian.simplehitman.Simplehitman;
import com.soychristian.simplehitman.commands.CommandManager;
import com.soychristian.simplehitman.commands.SubCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HuntCommand extends SubCommand {
    private final Economy eco = Simplehitman.getEconomy();

    String pluginName = Simplehitman.pluginName;

    String enterUsername = Simplehitman.getPlugin().getConfig().getString("enter-username");
    String offerReward = Simplehitman.getPlugin().getConfig().getString("offer-reward");
    String successfulContract = Simplehitman.getPlugin().getConfig().getString("succesful-contract");
    String broadcastSuccessfulContract = Simplehitman.getPlugin().getConfig().getString("broadcast-succesfull-contract");
    String userNotFound = Simplehitman.getPlugin().getConfig().getString("user-not-found");
    String insufficientMoney = Simplehitman.getPlugin().getConfig().getString("insufficient-money");
    String enterNumericValue = Simplehitman.getPlugin().getConfig().getString("enter-numeric-value");
    String increaseReward = Simplehitman.getPlugin().getConfig().getString("increase-reward");
    @Override
    public String getName() {
        return "hunt";
    }

    @Override
    public String getDescription() {
        return "Offer a reward for a player's head";
    }

    @Override
    public String getSyntax() {
        return "/simplehitman hunt <player> <reward>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', enterUsername));
        } else if (args.length == 2) {
            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', offerReward));
        } else if (args.length > 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target instanceof Player) {
                if (args[2].matches("[+-]?\\d*(\\.\\d+)?")) {
                    double reward = Double.parseDouble(args[2]);
                    if (eco.getBalance(player) >= reward) {
                        if (CommandManager.listHitman.contains(target)) {
                            int indexReward = CommandManager.listHitman.indexOf(target);
                            double rewardExtend = Double.parseDouble(CommandManager.listReward.get(indexReward)) + Double.parseDouble(args[2]);

                            eco.withdrawPlayer(player, reward);
                            CommandManager.listReward.remove(indexReward);
                            CommandManager.listReward.add(indexReward, Double.toString(rewardExtend));

                            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', successfulContract));
                            Bukkit.broadcastMessage(pluginName + ChatColor.GRAY + player.getName() + ChatColor.WHITE + " " + ChatColor.translateAlternateColorCodes('&', increaseReward) + " " + ChatColor.GRAY + target.getName() + ChatColor.WHITE + " (" + ChatColor.GREEN + "$" + rewardExtend + ChatColor.WHITE + ")");
                        } else if (!CommandManager.listHitman.contains(target)) {
                            player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', successfulContract));
                            CommandManager.listHitman.add(target);
                            CommandManager.listReward.add(String.valueOf(reward));
                            eco.withdrawPlayer(player, reward);
                            Bukkit.broadcastMessage(pluginName + ChatColor.GRAY + player.getName() + ChatColor.WHITE + " " + ChatColor.translateAlternateColorCodes('&', broadcastSuccessfulContract) + " " + ChatColor.GRAY + target.getName() + ChatColor.WHITE + " (" + ChatColor.GREEN + "$" + reward + ChatColor.WHITE + ").");
                        }
                    } else {
                        player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', insufficientMoney));
                    }
                } else {
                    player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', enterNumericValue));
                }
            } else {
                player.sendMessage(pluginName + ChatColor.translateAlternateColorCodes('&', userNotFound));
            }
        }
    }
}


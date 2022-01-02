package com.soychristian.simplehitman;

import com.soychristian.simplehitman.commands.CommandManager;
import com.soychristian.simplehitman.listeners.onKillPlayer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Simplehitman extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Simplehitman plugin;

    public static String pluginName = ChatColor.GOLD + "" +ChatColor.BOLD + "[" + ChatColor.GRAY + "SimpleHitman" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.WHITE;

    @Override
    public void onEnable() {
        plugin = this;
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("simplehitman").setExecutor(new CommandManager());
        getServer().getPluginManager().registerEvents(new onKillPlayer(), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }


    public static Simplehitman getPlugin(){
        return plugin;
    }
}

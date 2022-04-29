package me.pm.commandspy;

import me.pm.commandspy.events.CommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private final CommandEvent commandEvent = new CommandEvent(this);

    @Override
    public void onEnable() {
        load();
        Bukkit.getPluginManager().registerEvents(commandEvent, this);
        getCommand("commandspyreload").setExecutor(commandEvent);
        getCommand("broadcastdiscord").setExecutor(commandEvent);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void load() {
        saveDefaultConfig();
    }
}

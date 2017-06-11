package me.itskev.thirst.util;

import me.itskev.thirst.Thirst;
import me.itskev.thirst.manager.ThirstPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WriteConfig {
    private static WriteConfig instance;

    public static WriteConfig getInstance() {
        return instance == null ? instance = new WriteConfig() : instance;
    }

    private JavaPlugin plugin;

    private WriteConfig() {
        this.plugin = Thirst.getInstance();
        this.plugin.getConfig().options().copyDefaults(true);
        this.plugin.saveDefaultConfig();
        this.plugin.saveConfig();
    }

    public void addToPlayerToConfig(String playerName, int thirst) {
        FileConfiguration config = this.plugin.getConfig();

        config.set("Players." + playerName, thirst);
        this.plugin.saveConfig();
    }

    public ThirstPlayer getAndRemovePlayerFromConfig(Player player){
        FileConfiguration config = this.plugin.getConfig();

        int thirst = 100;

        if(config.contains("Players." + player.getName())){
            thirst = config.getInt("Players." + player.getName());
            config.set("Players." + player.getName(), null);
            this.plugin.saveConfig();
        }

        return new ThirstPlayer(player, thirst);
    }
}

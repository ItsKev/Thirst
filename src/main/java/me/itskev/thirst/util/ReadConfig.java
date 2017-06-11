package me.itskev.thirst.util;

import me.itskev.thirst.Thirst;
import org.bukkit.configuration.file.FileConfiguration;

public class ReadConfig {

    private static ReadConfig instance;

    public static ReadConfig getInstance() {
        return instance == null ? instance = new ReadConfig() : instance;
    }

    private int timeBetweenTicks, percentageRemovePerTick, percentageGainPerBottle;

    private ReadConfig() {
        FileConfiguration configuration = Thirst.getInstance().getConfig();

        configuration.addDefault("TimeBetweenTicks", 1);
        configuration.addDefault("PercentageRemovePerTick", 5);
        configuration.addDefault("PercentageGainPerBottle", 50);

        this.timeBetweenTicks = configuration.getInt("TimeBetweenTicks");
        this.percentageRemovePerTick = configuration.getInt("PercentageRemovePerTick");
        this.percentageGainPerBottle = configuration.getInt("PercentageGainPerBottle");
    }

    public int getTimeBetweenTicks() {
        return timeBetweenTicks;
    }

    public int getPercentageRemovePerTick() {
        return percentageRemovePerTick;
    }

    public int getPercentageGainPerBottle() {
        return percentageGainPerBottle;
    }
}

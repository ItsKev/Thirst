package me.itskev.thirst;

import me.itskev.thirst.events.PlayerEvent;
import me.itskev.thirst.manager.ThirstPlayer;
import me.itskev.thirst.util.ReadConfig;
import me.itskev.thirst.util.WriteConfig;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Main class of the Thirst plugin
 *
 * @author ItsKev
 */
public class Thirst extends JavaPlugin {

    private static Thirst instance;

    public static Thirst getInstance() {return instance;}

    private List<ThirstPlayer> thirst;


    @Override
    public void onEnable() {
        instance = this;
        this.thirst = new ArrayList<>();

        WriteConfig.getInstance();
        ReadConfig.getInstance();
        this.registerEvents();
        this.startThirstSimulation();
    }

    @Override
    public void onDisable() {
        for(ThirstPlayer entry : this.thirst){
            WriteConfig.getInstance().addToPlayerToConfig(entry.getPlayer().getName(), entry.getThirst());
        }
    }

    public List<ThirstPlayer> getThirst(){
        return this.thirst;
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerEvent(), this);
    }

    private void startThirstSimulation() {
        new BukkitRunnable(){
            @Override
            public void run() {

                for (ThirstPlayer player : thirst){
                    if(player.getThirst() <= 0){
                        player.getPlayer().damage(1);
                    } else {
                        player.setThirst(player.getThirst() - ReadConfig.getInstance().getPercentageRemovePerTick());
                    }
                    IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§bThirst: " + player.getThirst() + "%\"}");
                    PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, titleJSON);
                    ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }.runTaskTimer(Thirst.getInstance(), 0, 20 * ReadConfig.getInstance().getTimeBetweenTicks());
    }
}

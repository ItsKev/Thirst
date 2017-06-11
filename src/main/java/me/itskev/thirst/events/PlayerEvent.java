package me.itskev.thirst.events;

import me.itskev.thirst.Thirst;
import me.itskev.thirst.manager.ThirstPlayer;
import me.itskev.thirst.util.ReadConfig;
import me.itskev.thirst.util.WriteConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

/**
 * Multiple player events
 */
public class PlayerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("thirst.lose")) {
            Thirst.getInstance().getThirst().add(WriteConfig.getInstance().getAndRemovePlayerFromConfig(event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ThirstPlayer entry = null;
        for (ThirstPlayer player : Thirst.getInstance().getThirst()) {
            if (player.getPlayer().equals(event.getPlayer())) {
                entry = player;
            }
        }

        if (entry != null) {
            WriteConfig.getInstance().addToPlayerToConfig(entry.getPlayer().getName(), entry.getThirst());
            Thirst.getInstance().getThirst().remove(entry);
        }
    }

    @EventHandler
    public void onPotionConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.POTION)) {
            PotionType potionType = ((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType();
            if (potionType == PotionType.WATER) {
                for (ThirstPlayer entry : Thirst.getInstance().getThirst()) {
                    if (entry.getPlayer().equals(event.getPlayer())) {
                        int thirst = entry.getThirst() + ReadConfig.getInstance().getPercentageGainPerBottle();
                        if (thirst > 100) {
                            entry.setThirst(100);
                        } else {
                            entry.setThirst(thirst);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        for (ThirstPlayer entry : Thirst.getInstance().getThirst()) {
            if (entry.getPlayer().equals(event.getPlayer())) {
                entry.setThirst(100);
            }
        }
    }
}

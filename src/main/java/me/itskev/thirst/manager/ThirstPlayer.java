package me.itskev.thirst.manager;

import org.bukkit.entity.Player;

public class ThirstPlayer {

    private Player player;
    private int thirst;

    public ThirstPlayer(Player player, int thirst) {
        this.player = player;
        this.thirst = thirst;
    }

    public int getThirst() {
        return thirst;
    }

    public Player getPlayer() {
        return player;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }
}

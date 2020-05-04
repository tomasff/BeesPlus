package com.tomff.beesplus.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Icon {

    private ItemStack item;
    private Consumer<Player> callback;

    public Icon(ItemStack item, Consumer<Player> callback) {
        this.item = item;
        this.callback = callback;
    }

    public Consumer<Player>  getCallback() {
        return callback;
    }

    public ItemStack getItem() {
        return item;
    }

}

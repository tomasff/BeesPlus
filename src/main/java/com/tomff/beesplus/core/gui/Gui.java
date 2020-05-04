package com.tomff.beesplus.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class Gui {

    private final HashMap<Integer, Icon> icons;
    private final Inventory inventory;

    protected Gui() {
        this.icons = new HashMap<>(getSize());
        this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
    }

    public final void fill(Icon icon) {
        for (int i = 0; i < getSize(); i++) {
            if (!icons.containsKey(i)) {
                setIcon(icon, i);
            }
        }
    }

    public final void setIcon(Icon icon, int... slots) {
        for (int slot : slots) {
            icons.put(slot, icon);
            inventory.setItem(slot, icon.getItem());
        }
    }

    public Icon getIcon(int slot) {
        return icons.get(slot);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public abstract int getSize();

    public abstract String getTitle();

    public abstract void buildIcons();
}

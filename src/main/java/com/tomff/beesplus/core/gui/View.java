package com.tomff.beesplus.core.gui;

import org.bukkit.inventory.InventoryView;

public class View {

    private final InventoryView inventoryView;
    private final Gui gui;

    public View(InventoryView inventoryView, Gui gui) {
        this.inventoryView = inventoryView;
        this.gui = gui;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public Gui getGui() {
        return gui;
    }
}

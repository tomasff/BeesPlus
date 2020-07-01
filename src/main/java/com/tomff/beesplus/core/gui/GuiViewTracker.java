package com.tomff.beesplus.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.UUID;

public class GuiViewTracker {

    private HashMap<UUID, View> views;

    public GuiViewTracker() {
        views = new HashMap<>();
    }

    public boolean isViewingGui(UUID uuid) {
        return views.containsKey(uuid);
    }

    public View getView(UUID uuid) {
        return views.get(uuid);
    }

    public void removeView(UUID uuid) {
        views.remove(uuid);
    }

    public void clearViews() {
        views.forEach((uuid, view) -> view.getInventoryView().close());
    }

    public void openGui(Player player, Gui gui) {
        gui.buildIcons();
        InventoryView view = player.openInventory(gui.getInventory());

        views.put(player.getUniqueId(), new View(view, gui));
    }
}

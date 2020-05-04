package com.tomff.beesplus.core.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GuiManager {

    private HashMap<UUID, Gui> openedGuis;

    public GuiManager() {
        openedGuis = new HashMap<>();
    }

    public void openGui(Player player, Gui gui) {
        openedGuis.put(player.getUniqueId(), gui);

        gui.buildIcons();
        player.openInventory(gui.getInventory());
    }

    public HashMap<UUID, Gui> getOpenedGuis() {
        return openedGuis;
    }
}

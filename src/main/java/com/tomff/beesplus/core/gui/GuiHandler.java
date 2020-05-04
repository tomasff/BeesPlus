package com.tomff.beesplus.core.gui;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class GuiHandler implements Listener {

    private final BeesPlus beesPlus;
    private final GuiManager guiManager;

    public GuiHandler(BeesPlus beesPlus) {
        this.beesPlus = beesPlus;
        this.guiManager = beesPlus.getGuiManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Inventory clickedInventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if (guiManager.getOpenedGuis().containsKey(uuid) &&
            guiManager.getOpenedGuis().get(uuid).getInventory().equals(clickedInventory)) {

            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Gui gui = guiManager.getOpenedGuis().get(uuid);

            Icon icon = gui.getIcon(event.getRawSlot());
            if (icon == null) return;

            Consumer<Player> callback = icon.getCallback();
            if(callback == null) return;

            icon.getCallback().accept(player);
        }
    }

}

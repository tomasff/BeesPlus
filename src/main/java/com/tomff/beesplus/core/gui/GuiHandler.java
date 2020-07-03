package com.tomff.beesplus.core.gui;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class GuiHandler implements Listener {
    private final GuiViewTracker guiViewTracker;

    public GuiHandler(BeesPlus beesPlus) {
        this.guiViewTracker = beesPlus.getGuiViewTracker();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if (!guiViewTracker.isViewingGui(uuid)) {
            return;
        }

        if (guiViewTracker.getView(uuid).getInventoryView() != event.getView()) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) return;
        if (clickedItem.getType() == Material.AIR) return;

        int slot = event.getRawSlot();

        View view = guiViewTracker.getView(uuid);
        Gui gui = view.getGui();

        if (!gui.hasIcon(slot)) return;

        Icon icon = gui.getIcon(event.getRawSlot());
        Consumer<Player> callback = icon.getCallback();

        if(callback == null) return;

        icon.getCallback().accept(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!guiViewTracker.isViewingGui(uuid)) {
            return;
        }

        if (guiViewTracker.getView(uuid).getInventoryView() == event.getView()) {
            guiViewTracker.removeView(uuid);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        guiViewTracker.removeView(event.getPlayer().getUniqueId());
    }

}

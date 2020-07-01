package com.tomff.beesplus.handlers;

import com.tomff.beesplus.BeesPlus;
import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.core.items.CustomItemManager;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;
import java.util.stream.Stream;

public class DamageHandler implements Listener {

    private final CustomItemManager customItemManager;

    private final CustomItem helmet;
    private final CustomItem chestplate;
    private final CustomItem leggings;
    private final CustomItem boots;

    private final double reduction;

    public DamageHandler(BeesPlus beesPlus) {
        this.customItemManager = beesPlus.getCustomItemManager();

        this.helmet = customItemManager.getCustomItems().get("protection_helmet");
        this.chestplate = customItemManager.getCustomItems().get("protection_chestplate");
        this.leggings = customItemManager.getCustomItems().get("protection_leggings");
        this.boots = customItemManager.getCustomItems().get("protection_boots");

        this.reduction = beesPlus.getConfig().getDouble("beeprotectionsuit.reduction", 0.50);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Bee) {
            Player player = (Player) event.getEntity();
            PlayerInventory playerInventory = player.getInventory();

            if (Stream.of(playerInventory.getArmorContents()).allMatch(Objects::nonNull)) {
                if (playerInventory.getHelmet().isSimilar(helmet.getResult()) &&
                        playerInventory.getChestplate().isSimilar(chestplate.getResult()) &&
                        playerInventory.getLeggings().isSimilar(leggings.getResult()) &&
                        playerInventory.getBoots().isSimilar(boots.getResult())) {

                    event.setDamage(reduction * event.getDamage());
                }
            }
        }
    }

}

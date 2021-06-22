package com.tomff.beesplus.items;

import com.tomff.beesplus.BeesPlus;
import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.core.items.ItemBuilder;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class BeeHiveUpgrade implements CustomItem, Listener {
    private NamespacedKey upgradeKey;
    private int maxPopulation;

    public BeeHiveUpgrade(BeesPlus beesPlus) {
        upgradeKey = new NamespacedKey(beesPlus, "upgrade");
        maxPopulation = beesPlus.getConfig().getInt("beehiveupgrade.maximumpopulation", 9);
    }

    public String[] getRecipe() {
        return new String[] {
                "CCC",
                "CHC",
                "CCC"
        };
    }

    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('C', Material.HONEYCOMB);
        ingredients.put('H', Material.BEEHIVE);

        return ingredients;
    }

    public ItemStack getResult() {
        return new ItemBuilder(Material.HONEYCOMB)
                .setName(Localization.get(Localization.BEEHIVE_UPGRADE_ITEM_NAME))
                .setLore(Localization.get(Localization.BEEHIVE_UPGRADE_ITEM_LORE).split("\\|\\|"))
                .setPersistentData(upgradeKey, PersistentDataType.STRING, "beehive")
                .enchant(Enchantment.DURABILITY, 1)
                .hideEnchantments()
                .build();
    }

    @EventHandler
    public void onHiveClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (clickedBlock == null) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType().equals(Material.BEEHIVE)) {
            ItemStack handItem = event.getItem();
            ItemMeta handItemMeta = handItem.getItemMeta();

            if (handItemMeta == null) {
                return;
            }

            PersistentDataContainer container = handItemMeta.getPersistentDataContainer();

            if (!container.has(upgradeKey, PersistentDataType.STRING)) {
                return;
            }

            String upgradeType = container.get(upgradeKey, PersistentDataType.STRING);

            if (upgradeType.equals("beehive")) {
                event.setCancelled(true);

                Beehive beehive = (Beehive) clickedBlock.getState();

                if (!player.hasPermission("beesplus.beehive.upgrade")) {
                    return;
                }

                upgradeBeehive(player, beehive, handItem);
            }
        }
    }

    public void upgradeBeehive(Player player, Beehive beehive, ItemStack handItem) {
        if (beehive.getMaxEntities() >= maxPopulation) {
            Localization.sendMessage(player, Localization.BEEHIVE_UPGRADE_MAX);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);

            return;
        }

        beehive.setMaxEntities(beehive.getMaxEntities() + 3);
        beehive.update();

        Localization.sendMessage(player, Localization.BEEHIVE_UPGRADE_SUCCESS, beehive.getMaxEntities());
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2, 2);

        ItemStack upgradeAmountRemove = handItem.clone();
        upgradeAmountRemove.setAmount(1);

        player.getInventory().removeItem(upgradeAmountRemove);
    }
}

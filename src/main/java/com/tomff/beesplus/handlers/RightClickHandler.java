package com.tomff.beesplus.handlers;

import com.tomff.beesplus.BeesPlus;
import com.tomff.beesplus.gui.BeeHiveInfo;
import com.tomff.beesplus.gui.BeeInfo;
import com.tomff.beesplus.core.gui.GuiManager;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RightClickHandler implements Listener {

    private final BeesPlus beesPlus;
    private final GuiManager guiManager;

    private final boolean removeAnger;

    private final Material[] flowers = {
            Material.SUNFLOWER, Material.DANDELION,
            Material.POPPY, Material.BLUE_ORCHID,
            Material.ALLIUM, Material.ORANGE_TULIP,
            Material.PINK_TULIP, Material.RED_TULIP,
            Material.WHITE_TULIP, Material.AZURE_BLUET,
            Material.LILY_OF_THE_VALLEY, Material.OXEYE_DAISY,
            Material.CORNFLOWER
    };

    public RightClickHandler(BeesPlus beesPlus) {
        this.beesPlus = beesPlus;
        this.guiManager = beesPlus.getGuiManager();

        removeAnger = beesPlus.getConfig().getBoolean("healing.removeanger", true);
    }

    private boolean isFlower(ItemStack item) {
        return Arrays.stream(flowers).anyMatch(flower -> item.getType().equals(flower));
    }

    @EventHandler
    public void onInteraction(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (target instanceof Bee) {
            Bee bee = (Bee) target;

            if (player.isSneaking() && player.hasPermission("beesplus.bee.view")) {
                event.setCancelled(true);

                guiManager.openGui(player, new BeeInfo(bee));
                return;
            }

            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (isFlower(itemInHand) && bee.getHealth() < 10 && player.hasPermission("beesplus.bee.heal")) {
                event.setCancelled(true);

                if (removeAnger) {
                    bee.setAnger(0);
                }

                bee.setHealth(bee.getHealth() + 1);
                bee.playEffect(EntityEffect.LOVE_HEARTS);
                player.getInventory().removeItem(new ItemStack(itemInHand.getType(), 1));
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (action == Action.RIGHT_CLICK_BLOCK && player.isSneaking() && clickedBlock != null) {
            if ((clickedBlock.getType().equals(Material.BEEHIVE) || clickedBlock.getType().equals(Material.BEE_NEST)) && player.hasPermission("beesplus.beehive.view")) {
                event.setCancelled(true);

                Beehive beehive = (Beehive) clickedBlock.getState();
                guiManager.openGui(player, new BeeHiveInfo(beehive));
            }
        }
    }
}

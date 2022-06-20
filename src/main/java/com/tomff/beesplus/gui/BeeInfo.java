package com.tomff.beesplus.gui;

import com.tomff.beesplus.core.gui.Gui;
import com.tomff.beesplus.core.gui.Icon;
import com.tomff.beesplus.core.items.ItemBuilder;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.*;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BeeInfo extends Gui {

    private final Bee bee;

    public BeeInfo(Bee bee) {
        this.bee = bee;
    }

    @Override
    public int getSize() {
        return 5 * 9;
    }

    @Override
    public String getTitle() {
        return Localization.get(Localization.BEE_INFO_GUI_TITLE);
    }

    public void rideBee(Player player) {
        double distance = player.getLocation().distance(bee.getLocation());

        if (!player.hasPermission("beesplus.bee.ride")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
            Localization.sendMessage(player, Localization.BEE_INFO_GUI_RIDE_NO_PERMISSION);

            return;
        }

        if (bee.getAnger() > 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
            Localization.sendMessage(player, Localization.BEE_INFO_GUI_RIDE_ANGRY);

            return;
        }

        if (distance <= 6) {
            if (bee.getPassengers().size() >= 1) {
                Localization.sendMessage(player, Localization.BEE_INFO_GUI_RIDE_ALREADY);
                return;
            }

            player.closeInventory();

            bee.addPassenger(player);

            String title = Localization.get(Localization.RIDE_BEE_TITLE, player.getName());
            String subtitle = Localization.get(Localization.RIDE_BEE_SUBTITLE, player.getName());

            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 10, 1);
            player.sendTitle(title, subtitle, 10, 25, 10);
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
            Localization.sendMessage(player, Localization.BEE_INFO_GUI_RIDE_TOO_FAR);
        }
    }

    @Override
    public void buildIcons() {
        ItemStack age = new ItemBuilder(Material.OAK_SIGN)
                .setName(Localization.get(Localization.BEE_INFO_GUI_AGE))
                .setLore(Localization.get(bee.isAdult() ? Localization.BEE_INFO_GUI_AGE_ADULT : Localization.BEE_INFO_GUI_AGE_BABY)
                ).build();

        Icon ageIcon = new Icon(age, null);
        setIcon(ageIcon, 4);

        Material angerColor = (bee.getAnger() > 0) ? Material.RED_TERRACOTTA : Material.GREEN_TERRACOTTA;
        ItemStack anger = new ItemBuilder(angerColor)
                .setName(Localization.get(Localization.BEE_INFO_GUI_ANGER))
                .setLore(Localization.get(Localization.BEE_INFO_GUI_ANGER_LEVEL_DESC, bee.getAnger()))
                .build();

        Icon angerIcon = new Icon(anger, null);
        setIcon(angerIcon, 11);

        Location hiveLocation = bee.getHive();
        String[] hiveLocationLore = (hiveLocation != null) ? new String[] {
                ChatColor.GREEN + "X: " + ChatColor.GRAY + hiveLocation.getX(),
                ChatColor.GREEN + "Y: " + ChatColor.GRAY + hiveLocation.getY(),
                ChatColor.GREEN + "Z: " + ChatColor.GRAY + hiveLocation.getZ()
        } : Localization.get(Localization.BEE_INFO_GUI_NO_HIVE_DESC).split("\\|\\|");

        ItemStack hive = new ItemBuilder(Material.BEE_NEST)
                .setName(Localization.get(Localization.BEE_INFO_GUI_HIVE_LOCATION))
                .setLore(hiveLocationLore)
                .build();

        Icon hiveIcon = new Icon(hive, null);
        setIcon(hiveIcon, 29);

        ItemStack mount = new ItemBuilder(Material.SADDLE)
                .setName(Localization.get(Localization.BEE_INFO_GUI_RIDE))
                .build();

        Icon mountIcon = new Icon(mount, this::rideBee);
        setIcon(mountIcon, 15);

        ItemStack stung = new ItemBuilder(Material.IRON_SWORD)
                .setName(Localization.get(Localization.BEE_INFO_GUI_HAS_STUNG))
                .setLore(bee.hasStung() ? Localization.get(Localization.TEXT_YES) : Localization.get(Localization.TEXT_NO))
                .build();

        Icon stungIcon = new Icon(stung, null);
        setIcon(stungIcon, 22);

        ItemStack nectar = new ItemBuilder(Material.HONEYCOMB)
                .setName(Localization.get(Localization.BEE_INFO_GUI_HAS_NECTAR))
                .setLore(bee.hasNectar() ? Localization.get(Localization.TEXT_YES) : Localization.get(Localization.TEXT_NO))
                .build();

        Icon nectarIcon = new Icon(nectar, null);
        setIcon(nectarIcon, 33);

        ItemStack health = new ItemBuilder(Material.POTION)
                .setName(Localization.get(Localization.BEE_INFO_GUI_HEALTH))
                .setLore(Localization.get(Localization.BEE_INFO_GUI_HEALTH_DESC, bee.getHealth()))
                .build();

        Icon healthIcon = new Icon(health, null);
        setIcon(healthIcon, 40);

        ItemStack filler = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                .setName(" ")
                .build();

        Icon fillerIcon = new Icon(filler, null);
        fill(fillerIcon);
    }
}

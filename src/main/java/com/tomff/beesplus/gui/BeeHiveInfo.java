package com.tomff.beesplus.gui;

import com.tomff.beesplus.core.gui.Gui;
import com.tomff.beesplus.core.gui.Icon;
import com.tomff.beesplus.core.items.ItemBuilder;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.inventory.ItemStack;

public class BeeHiveInfo extends Gui {

    private final Beehive beehive;
    private final int[] honeyLevelSlots = {
            40, 41, 42,
            31, 32, 33,
            22, 23, 24,
            13, 14, 15
    };

    public BeeHiveInfo(Beehive beehive) {
        this.beehive = beehive;
    }

    @Override
    public int getSize() {
        return 6 * 9;
    }

    @Override
    public String getTitle() {
        return Localization.get(Localization.BEEHIVE_INFO_GUI_TITLE);
    }

    private void setHoneyLevelSlots(HoneyLevelIndicators indicator) {
        ItemStack empty = new ItemBuilder(Material.AIR).build();
        Icon emptyIcon = new Icon(empty, null);

        ItemStack level = new ItemBuilder(indicator.getMaterial())
                .setName(indicator.getColor() + indicator.getHumanName())
                .build();
        Icon levelIcon = new Icon(level, null);

        for (int slot = 0; slot < indicator.getSlots(); slot++) {
            setIcon(levelIcon, honeyLevelSlots[slot]);
        }

        for (int slot = indicator.getSlots(); slot < 12; slot++) {
            setIcon(emptyIcon, honeyLevelSlots[slot]);
        }
    }

    @Override
    public void buildIcons() {
        org.bukkit.block.data.type.Beehive beehiveData = (org.bukkit.block.data.type.Beehive) beehive.getBlockData();

        ItemStack honeyLevel = new ItemBuilder(Material.HONEYCOMB)
                .setName(Localization.get(Localization.BEEHIVE_INFO_GUI_HONEY_CAPACITY))
                .setLore(Localization.get(Localization.BEEHIVE_INFO_GUI_HONEY_CAPACITY_DESC,
                        beehiveData.getHoneyLevel(), beehiveData.getMaximumHoneyLevel()))
                .build();

        Icon honeyLevelIcon = new Icon(honeyLevel, null);
        setIcon(honeyLevelIcon, 10);

        String isSedated = beehive.isSedated() ? Localization.get(Localization.BEEHIVE_INFO_GUI_SEDATED) :
                                                                Localization.get(Localization.BEEHIVE_INFO_GUI_NOT_SEDATED);

        ItemStack beeCapacity = new ItemBuilder(Material.BEE_NEST)
                .setName(Localization.get(Localization.BEEHIVE_INFO_GUI_BEE_CAPACITY))
                .setLore(Localization.get(Localization.BEEHIVE_INFO_GUI_BEE_CAPACITY_DESC, beehive.getEntityCount(), beehive.getMaxEntities()),
                        isSedated)
                .build();

        Icon beeCapacityIcon = new Icon(beeCapacity, null);
        setIcon(beeCapacityIcon, 19);

        Location flowerLocation = beehive.getFlower();
        String[] flowerLocationLore = (flowerLocation != null) ? new String[] {
                ChatColor.GREEN + "X: " + ChatColor.GRAY + flowerLocation.getX(),
                ChatColor.GREEN + "Y: " + ChatColor.GRAY + flowerLocation.getY(),
                ChatColor.GREEN + "Z: " + ChatColor.GRAY + flowerLocation.getZ()
        } : Localization.get(Localization.BEEHIVE_INFO_GUI_NO_TARGET_FLOWER_DESC).split("\\|\\|");

        ItemStack flower = new ItemBuilder(Material.DANDELION)
                .setName(Localization.get(Localization.BEEHIVE_INFO_GUI_FLOWER))
                .setLore(flowerLocationLore)
                .build();

        Icon flowerIcon = new Icon(flower, null);
        setIcon(flowerIcon, 37);

        HoneyLevelIndicators honeyLevelIndicator = HoneyLevelIndicators.getFromLevel(beehiveData.getHoneyLevel(),
                beehiveData.getMaximumHoneyLevel());
        setHoneyLevelSlots(honeyLevelIndicator);

        ItemStack filler = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                .setName(" ")
                .build();

        Icon fillerIcon = new Icon(filler, null);
        fill(fillerIcon);
    }
}

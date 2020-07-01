package com.tomff.beesplus.gui;

import com.tomff.beesplus.localization.Localization;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum HoneyLevelIndicators {

    EMPTY(0, Material.AIR, null, null, 0),
    LOW(1, Material.GREEN_TERRACOTTA, ChatColor.GREEN, Localization.HONEY_LOW, 3),
    MEDIUM(2, Material.YELLOW_TERRACOTTA, ChatColor.GOLD, Localization.HONEY_MEDIUM, 6),
    HIGH(3, Material.ORANGE_TERRACOTTA, ChatColor.RED, Localization.HONEY_HIGH, 9),
    VERY_HIGH(4, Material.RED_TERRACOTTA, ChatColor.DARK_RED, Localization.HONEY_VERY_HIGH, 12);

    private final int level;
    private final Material material;
    private final ChatColor color;
    private final Localization localization;
    private final int slots;

    HoneyLevelIndicators(int level, Material material, ChatColor color, Localization localization, int slots) {
        this.level = level;
        this.material = material;
        this.color = color;
        this.localization = localization;
        this.slots = slots;
    }

    public static HoneyLevelIndicators getFromLevel(int currentHoneyLvl, int maxHoneyLvl) {
        float ratio = (float) currentHoneyLvl / (float) maxHoneyLvl;

        if (ratio == 0) return EMPTY;
        if (ratio <= 0.25) return LOW;
        if (ratio <= 0.50) return MEDIUM;
        if (ratio <= 0.75) return HIGH;
        if (ratio <= 1) return VERY_HIGH;

        return EMPTY;
    }

    public int getLevel() {
        return level;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getSlots() {
        return slots;
    }

    public Material getMaterial() {
        return material;
    }

    public String getHumanName() {
        return Localization.get(localization);
    }
}

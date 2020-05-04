package com.tomff.beesplus.core.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class CustomItem {

    public abstract String[] getRecipe();
    public abstract Map<Character, Material> getIngredients();
    public abstract String getName();
    public abstract Material getMaterial();

    public ItemStack getItem() {
        return new ItemBuilder(getMaterial())
                .setName(getName())
                .build();
    }

}

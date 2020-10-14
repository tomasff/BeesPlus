package com.tomff.beesplus.core.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface CustomItem {

    String[] getRecipe();
    Map<Character, Material> getIngredients();
    ItemStack getResult();

}

package com.tomff.beesplus.items;

import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.core.items.ItemBuilder;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BeeProtectionHelmet implements CustomItem {
    public String[] getRecipe() {
        return new String[] {
                "SSS",
                "SAS",
                "SSS"
        };
    }

    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('S', Material.STRING);
        ingredients.put('A', Material.CHAINMAIL_HELMET);

        return ingredients;
    }

    public ItemStack getResult() {
        return new ItemBuilder(Material.CHAINMAIL_HELMET)
                .setName(Localization.get(Localization.BEE_PROTECTION_HELMET))
                .build();
    }
}

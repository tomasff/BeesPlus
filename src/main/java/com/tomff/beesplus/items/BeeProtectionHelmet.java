package com.tomff.beesplus.items;

import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class BeeProtectionHelmet extends CustomItem {
    @Override
    public String[] getRecipe() {
        return new String[] {
                "SSS",
                "SAS",
                "SSS"
        };
    }

    @Override
    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('S', Material.STRING);
        ingredients.put('A', Material.CHAINMAIL_HELMET);

        return ingredients;
    }

    @Override
    public String getName() {
        return Localization.get(Localization.BEE_PROTECTION_HELMET);
    }

    @Override
    public Material getMaterial() {
        return Material.CHAINMAIL_HELMET;
    }
}

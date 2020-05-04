package com.tomff.beesplus.items;

import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class BeeProtectionLeggings extends CustomItem {
    @Override
    public String[] getRecipe() {
        return new String[] {
                "SSS",
                "SLS",
                "SSS"
        };
    }

    @Override
    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('S', Material.STRING);
        ingredients.put('L', Material.CHAINMAIL_LEGGINGS);

        return ingredients;
    }

    @Override
    public String getName() {
        return Localization.get(Localization.BEE_PROTECTION_LEGGINGS);
    }

    @Override
    public Material getMaterial() {
        return Material.CHAINMAIL_LEGGINGS;
    }
}

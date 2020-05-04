package com.tomff.beesplus.items;

import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class BeeProtectionChestplate extends CustomItem {
    @Override
    public String[] getRecipe() {
        return new String[] {
                "SSS",
                "SCS",
                "SSS"
        };
    }

    @Override
    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('S', Material.STRING);
        ingredients.put('C', Material.CHAINMAIL_CHESTPLATE);

        return ingredients;
    }

    @Override
    public String getName() {
        return Localization.get(Localization.BEE_PROTECTION_CHESTPLATE);
    }

    @Override
    public Material getMaterial() {
        return Material.CHAINMAIL_CHESTPLATE;
    }
}

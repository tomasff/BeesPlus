package com.tomff.beesplus.items;

import com.tomff.beesplus.core.items.CustomItem;
import com.tomff.beesplus.core.items.ItemBuilder;
import com.tomff.beesplus.localization.Localization;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BeeProtectionBoots extends CustomItem {
    @Override
    public String[] getRecipe() {
        return new String[] {
                "SSS",
                "SBS",
                "SSS"
        };
    }

    @Override
    public Map<Character, Material> getIngredients() {
        Map<Character, Material> ingredients = new HashMap<>();

        ingredients.put('S', Material.STRING);
        ingredients.put('B', Material.CHAINMAIL_BOOTS);

        return ingredients;
    }

    @Override
    public ItemStack getResult() {
        return new ItemBuilder(Material.CHAINMAIL_BOOTS)
                .setName(Localization.get(Localization.BEE_PROTECTION_BOOTS))
                .build();
    }
}

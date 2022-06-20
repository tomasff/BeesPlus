package com.tomff.beesplus.core.items;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {

    private BeesPlus beesPlus;
    private Map<String, CustomItem> customItems;

    public CustomItemManager(BeesPlus beesPlus) {
        this.beesPlus = beesPlus;
        this.customItems = new HashMap<>();
    }

    public Map<String, CustomItem> getCustomItems() {
        return customItems;
    }

    public void registerCustomItem(String id, CustomItem customItem) {
        customItems.put(id, customItem);

        NamespacedKey namespacedKey = new NamespacedKey(beesPlus, id);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, customItem.getResult());

        recipe.shape(customItem.getRecipe());
        customItem.getIngredients().forEach(recipe::setIngredient);

        Bukkit.addRecipe(recipe);
    }
}

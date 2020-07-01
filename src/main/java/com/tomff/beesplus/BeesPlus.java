package com.tomff.beesplus;

import com.tomff.beesplus.handlers.DamageHandler;
import com.tomff.beesplus.handlers.RightClickHandler;
import com.tomff.beesplus.items.*;
import com.tomff.beesplus.core.UpdateChecker;
import com.tomff.beesplus.core.gui.GuiHandler;
import com.tomff.beesplus.core.gui.GuiManager;
import com.tomff.beesplus.core.items.CustomItemManager;
import com.tomff.beesplus.localization.Localization;
import com.tomff.beesplus.localization.LocalizationWrapper;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Locale;

public class BeesPlus extends JavaPlugin {

    private GuiManager guiManager;
    private CustomItemManager customItemManager;

    private LocalizationWrapper localizationWrapper;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        guiManager = new GuiManager();
        customItemManager = new CustomItemManager(this);

        getServer().getPluginManager().registerEvents(new GuiHandler(this), this);
        getServer().getPluginManager().registerEvents(new RightClickHandler(this), this);

        if (!loadLocale()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerItems();

        Metrics metrics = new Metrics(this, 7065);

        new UpdateChecker(this, 77224).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("A new update is available: BeesPlus " + version);
            }
        });
    }

    private boolean loadLocale() {
        String locale = getConfig().getString("locale", Locale.ENGLISH.toLanguageTag());
        localizationWrapper = new LocalizationWrapper(this, "locale");

        try {
            YamlConfiguration localeYamlFile = localizationWrapper.getLocale(locale);
            Localization.load(localeYamlFile);
        } catch (IOException e) {
            getLogger().severe("Invalid locale! Please choose a valid locale.");

            return false;
        } catch (InvalidConfigurationException | IllegalArgumentException e) {
            getLogger().severe(e.getMessage());

            getLogger().severe("Locale file corrupted or malformed! Please check your locale file.");
            return false;
        }

        return true;
    }

    private void registerItems() {
        BeeHiveUpgrade beeHiveUpgrade = new BeeHiveUpgrade(this);

        customItemManager.registerCustomItem("honey_upgrade", beeHiveUpgrade);
        getServer().getPluginManager().registerEvents(beeHiveUpgrade, this);

        boolean isProtectionSuitEnabled = getConfig().getBoolean("beeprotectionsuit.enabled", true);

        if(isProtectionSuitEnabled) {
            customItemManager.registerCustomItem("protection_boots", new BeeProtectionBoots());
            customItemManager.registerCustomItem("protection_leggings", new BeeProtectionLeggings());
            customItemManager.registerCustomItem("protection_chestplate", new BeeProtectionChestplate());
            customItemManager.registerCustomItem("protection_helmet", new BeeProtectionHelmet());

            getServer().getPluginManager().registerEvents(new DamageHandler(this), this);
        }
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }
}

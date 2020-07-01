package com.tomff.beesplus;

import com.tomff.beesplus.handlers.DamageHandler;
import com.tomff.beesplus.handlers.RightClickHandler;
import com.tomff.beesplus.items.BeeProtectionBoots;
import com.tomff.beesplus.items.BeeProtectionChestplate;
import com.tomff.beesplus.items.BeeProtectionHelmet;
import com.tomff.beesplus.items.BeeProtectionLeggings;
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

        String locale = getConfig().getString("locale", Locale.ENGLISH.toLanguageTag());

        localizationWrapper = new LocalizationWrapper(this, "locale");

        try {
            YamlConfiguration localeYamlFile = localizationWrapper.getLocale(locale);
            Localization.load(localeYamlFile);
        } catch (IOException e) {
            getLogger().severe("Invalid locale! Please choose a valid locale.");
            disablePlugin();

            return;
        } catch (InvalidConfigurationException e) {
            getLogger().severe(e.getMessage());
            getLogger().severe("Locale file corrupted or malformed! Please check your locale file.");
            disablePlugin();

            return;
        } catch (IllegalArgumentException e) {
            getLogger().severe(e.getMessage());
            getLogger().severe("Error in the locale file! Please check your locale file.");
            getLogger().severe("Maybe caused by a typo");
            disablePlugin();

            return;
        }

        guiManager = new GuiManager();

        getServer().getPluginManager().registerEvents(new GuiHandler(this), this);
        getServer().getPluginManager().registerEvents(new RightClickHandler(this), this);

        boolean isProtectionSuitEnabled = getConfig().getBoolean("beeprotectionsuit.enabled", true);

        if (isProtectionSuitEnabled) {
            customItemManager = new CustomItemManager(this);

            customItemManager.registerCustomItem("protection_boots", new BeeProtectionBoots());
            customItemManager.registerCustomItem("protection_leggings", new BeeProtectionLeggings());
            customItemManager.registerCustomItem("protection_chestplate", new BeeProtectionChestplate());
            customItemManager.registerCustomItem("protection_helmet", new BeeProtectionHelmet());

            getServer().getPluginManager().registerEvents(new DamageHandler(this), this);
        }

        Metrics metrics = new Metrics(this, 7065);

        new UpdateChecker(this, 77224).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("A new update is available: BeesPlus " + version);
            }
        });
    }

    private void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }
}

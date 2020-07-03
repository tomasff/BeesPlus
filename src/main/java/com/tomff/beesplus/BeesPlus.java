package com.tomff.beesplus;

import com.tomff.beesplus.core.UpdateChecker;
import com.tomff.beesplus.core.gui.GuiHandler;
import com.tomff.beesplus.core.gui.GuiViewTracker;
import com.tomff.beesplus.core.items.CustomItemManager;
import com.tomff.beesplus.core.migrations.AddFields;
import com.tomff.beesplus.core.migrations.Field;
import com.tomff.beesplus.core.migrations.MigrationsExecutor;
import com.tomff.beesplus.handlers.BeehiveHandler;
import com.tomff.beesplus.handlers.DamageHandler;
import com.tomff.beesplus.handlers.RightClickHandler;
import com.tomff.beesplus.items.*;
import com.tomff.beesplus.localization.Localization;
import com.tomff.beesplus.localization.LocalizationWrapper;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Locale;

public class BeesPlus extends JavaPlugin {

    private GuiViewTracker guiViewTracker;
    private CustomItemManager customItemManager;

    @Override
    public void onEnable() {
        performMigrations();
        saveDefaultConfig();

        guiViewTracker = new GuiViewTracker();
        customItemManager = new CustomItemManager(this);

        getServer().getPluginManager().registerEvents(new GuiHandler(this), this);
        getServer().getPluginManager().registerEvents(new BeehiveHandler(), this);
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

    @Override
    public void onDisable() {
        guiViewTracker.clearViews();
    }

    private boolean loadLocale() {
        String locale = getConfig().getString("locale", Locale.ENGLISH.toLanguageTag());
        LocalizationWrapper localizationWrapper = new LocalizationWrapper(this, "locale");

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

    private void performMigrations() {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutor(this);

        Field[] beehiveUpgradeTranslation = new Field[] {
                new Field("beehive_upgrade_item_name", "&6Beehive Upgrade"),
                new Field("beehive_upgrade_item_lore", "&7Bee capacity: &a+3||&8(Right click to use)"),
                new Field("beehive_upgrade_success", "&aBeehive upgraded! New population: &7%beesno%&a bees"),
                new Field("beehive_upgrade_max", "&cError: This beehive has reached the maximum population allowed!")
        };

        migrationsExecutor.addMigration(1,
                new AddFields("config.yml", new Field[] {
                        new Field("beehiveupgrade.maximumpopulation", 9)
                }),
                new AddFields("locale/en.yml", beehiveUpgradeTranslation),
                new AddFields("locale/fr.yml", beehiveUpgradeTranslation),
                new AddFields("locale/hu.yml", beehiveUpgradeTranslation),
                new AddFields("locale/zh_cn.yml", beehiveUpgradeTranslation),
                new AddFields("locale/pt.yml", new Field[] {
                        new Field("beehive_upgrade_item_name", "&6Melhorar Colmeia"),
                        new Field("beehive_upgrade_item_lore", "&7População de abelhas: &a+3||&8(Clique direito para usar)"),
                        new Field("beehive_upgrade_success", "&aColmeia melhorada! Nova população: &7%beesno%&a abelhas"),
                        new Field("beehive_upgrade_max", "&cErro: Esta colmeia atingiu a população máxima permitida!")
                })
        );

        migrationsExecutor.migrate();
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

    public GuiViewTracker getGuiViewTracker() {
        return guiViewTracker;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }
}
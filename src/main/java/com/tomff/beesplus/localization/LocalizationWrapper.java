package com.tomff.beesplus.localization;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class LocalizationWrapper {

    private final BeesPlus beesPlus;
    private final String basePath;

    public LocalizationWrapper(BeesPlus beesPlus, String path) {
        this.beesPlus = beesPlus;
        this.basePath = path;
    }

    public YamlConfiguration getLocale(String locale) throws IOException, InvalidConfigurationException {
        String path = Paths.get(basePath, locale + ".yml").toString();
        File localeFile = new File(beesPlus.getDataFolder(), path);

        if(!localeFile.exists()) {
            beesPlus.saveResource(path, false);
        }

        YamlConfiguration localeYaml = new YamlConfiguration();
        localeYaml.load(localeFile);

        return localeYaml;
    }

}

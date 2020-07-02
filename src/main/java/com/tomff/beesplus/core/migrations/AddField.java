package com.tomff.beesplus.core.migrations;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AddField implements Operation {
    private File file;

    private String fieldPath;
    private Object fieldValue;

    public AddField(String filePath, String fieldPath, Object fieldValue) {
        file = new File(BASE_PATH, filePath);

        this.fieldPath = fieldPath;
        this.fieldValue = fieldValue;
    }

    @Override
    public void execute() throws IOException {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        config.set(fieldPath, fieldValue);
        config.save(file);
    }
}

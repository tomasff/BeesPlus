package com.tomff.beesplus.core.migrations;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AddFields implements Operation {
    private File file;
    private Field[] fields;

    public AddFields(String filePath, Field[] fields) {
        file = new File(BASE_PATH, filePath);
        this.fields = fields;
    }

    @Override
    public void execute() throws IOException {
        if (!file.exists()) {
            return;
        }

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for (Field field : fields) {
            config.set(field.getPath(), field.getValue());
        }

        config.save(file);
    }
}

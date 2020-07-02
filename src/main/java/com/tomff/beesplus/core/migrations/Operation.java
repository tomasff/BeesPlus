package com.tomff.beesplus.core.migrations;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public interface Operation {
    File BASE_PATH = JavaPlugin.getPlugin(BeesPlus.class).getDataFolder();

    void execute() throws IOException;
}

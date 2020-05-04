package com.tomff.beesplus.core;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private BeesPlus beesPlus;
    private int resourceId;

    public UpdateChecker(BeesPlus beesPlus, int resourceId) {
        this.beesPlus = beesPlus;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(beesPlus, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                beesPlus.getLogger().info("Unable to query updates: " + exception.getMessage());
            }
        });
    }
}

package com.tomff.beesplus.core.migrations;

import com.tomff.beesplus.BeesPlus;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MigrationsExecutor {
    private int latestVersion;

    private Map<Integer, Operation[]> migrations;
    private File migrationFileRegistry;
    private YamlConfiguration yaml;

    private BeesPlus beesPlus;

    public MigrationsExecutor(BeesPlus beesPlus) {
        this.beesPlus = beesPlus;
        this.migrations = new HashMap<>();
        this.yaml = new YamlConfiguration();
        this.migrationFileRegistry = new File(beesPlus.getDataFolder(), "migration.yml");
        this.latestVersion = 0;
    }

    public void addMigration(int version, Operation... operations) {
        migrations.put(version, operations);

        if (version > latestVersion) {
            this.latestVersion = version;
        }
    }

    public void migrate() {
        if (hasConfig() && !hasMigrationRegistry()) {
            loadRegistry();

            migrateFrom(0);
            return;
        }

        if (!loadRegistry()) return;

        int version = yaml.getInt("migration", 0);
        if (version == latestVersion) return;

        migrateFrom(version);
    }

    private void migrateFrom(int version) {
        beesPlus.getLogger().info("Old files detected. Migrating files to migration v" + latestVersion);

        for (int nextVersion = version + 1; nextVersion <= latestVersion; nextVersion++) {
            performMigration(migrations.get(nextVersion));
        }

        setCurrentMigration(latestVersion);
    }

    public void setCurrentMigration(int version) {
        yaml.set("migration", version);

        try {
            yaml.save(migrationFileRegistry);
        } catch (IOException e) {
            beesPlus.getLogger().severe("An error occurred while trying to update the migration version");
            e.printStackTrace();
        }
    }

    private void createIfNotExist() {
        if (!hasMigrationRegistry()) {
            yaml.set("migration", latestVersion);

            try {
                yaml.save(migrationFileRegistry);
            } catch (IOException e) {
                e.printStackTrace();
                beesPlus.getLogger().severe("An error occurred while trying to create the migration registry file!");
            }
        }
    }

    private boolean loadRegistry() {
        createIfNotExist();

        try {
            yaml.load(migrationFileRegistry);
        } catch (IOException | InvalidConfigurationException e) {
            beesPlus.getLogger().severe("An error occurred while opening the migration registry file.");
            beesPlus.getLogger().severe("Suggested action: please delete the BeesPlus plugin folder and restart.");
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private void performMigration(Operation[] operations) {
        for(Operation operation : operations) {
            try {
                operation.execute();
            } catch (IOException e) {
                e.printStackTrace();
                beesPlus.getLogger().severe("An error occurred while migrating a file");
            }
        }
    }

    private boolean hasMigrationRegistry() {
        return migrationFileRegistry.exists();
    }

    private boolean hasConfig() {
        return new File(beesPlus.getDataFolder(), "config.yml").exists();
    }

}

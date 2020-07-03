package com.tomff.beesplus.localization;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum Localization {

    TEXT_YES,
    TEXT_NO,
    BEEHIVE_INFO_GUI_TITLE,
    BEEHIVE_INFO_GUI_HONEY_CAPACITY,
    BEEHIVE_INFO_GUI_HONEY_CAPACITY_DESC("current", "maximum"),
    BEEHIVE_INFO_GUI_BEE_CAPACITY,
    BEEHIVE_INFO_GUI_BEE_CAPACITY_DESC("current", "maximum"),
    BEEHIVE_INFO_GUI_SEDATED,
    BEEHIVE_INFO_GUI_NOT_SEDATED,
    BEEHIVE_INFO_GUI_FLOWER,
    BEEHIVE_INFO_GUI_NO_TARGET_FLOWER_DESC,
    BEE_INFO_GUI_TITLE,
    BEE_INFO_GUI_AGE,
    BEE_INFO_GUI_AGE_ADULT,
    BEE_INFO_GUI_AGE_BABY,
    BEE_INFO_GUI_ANGER,
    BEE_INFO_GUI_ANGER_LEVEL_DESC("level"),
    BEE_INFO_GUI_HIVE_LOCATION,
    BEE_INFO_GUI_NO_HIVE_DESC,
    BEE_INFO_GUI_RIDE,
    BEE_INFO_GUI_RIDE_NO_PERMISSION,
    BEE_INFO_GUI_RIDE_ANGRY,
    BEE_INFO_GUI_RIDE_ALREADY,
    BEE_INFO_GUI_RIDE_TOO_FAR,
    BEE_INFO_GUI_HAS_STUNG,
    BEE_INFO_GUI_HAS_NECTAR,
    BEE_INFO_GUI_HEALTH,
    BEE_INFO_GUI_HEALTH_DESC("health"),
    RIDE_BEE_TITLE("name"),
    RIDE_BEE_SUBTITLE("name"),
    BEE_PROTECTION_HELMET,
    BEE_PROTECTION_CHESTPLATE,
    BEE_PROTECTION_LEGGINGS,
    BEE_PROTECTION_BOOTS,
    BEEHIVE_UPGRADE_ITEM_NAME,
    BEEHIVE_UPGRADE_ITEM_LORE,
    BEEHIVE_UPGRADE_SUCCESS("beesno"),
    BEEHIVE_UPGRADE_MAX,
    HONEY_LOW,
    HONEY_MEDIUM,
    HONEY_HIGH,
    HONEY_VERY_HIGH;

    private final String[] placeholders;

    Localization(String... placeholders) {
        this.placeholders = placeholders;
    }

    public String[] getPlaceholders() {
        return placeholders;
    }

    private static final Map<Localization, String> messages = new HashMap<>();

    public static void load(YamlConfiguration localeFile) throws IllegalArgumentException {
        messages.clear();

        localeFile.getValues(false).forEach((key, value) -> {
            Localization localeKey = Localization.valueOf(key.toUpperCase());
            String message = (String) value;

            if (message != null) {
                messages.put(localeKey, ChatColor.translateAlternateColorCodes('&', message));
            }
        });
    }

    public static String get(Localization localization, Object... objects) {
        String message = messages.get(localization);

        if (message == null) {
            return null;
        }

        if (objects != null && objects.length != 0 && localization.getPlaceholders() != null && localization.getPlaceholders().length > 0) {
            String[] args = localization.getPlaceholders();

            for (int i = 0; i < objects.length; i++) {
                message = message.replace("%" + args[i] + "%", objects[i].toString());
            }
        }

        return message;
    }

    public static void sendMessage(CommandSender sender, Localization localization, Object... objects) {
        String message = get(localization, objects);

        if (message != null) {
            sender.sendMessage(message);
        }
    }
}
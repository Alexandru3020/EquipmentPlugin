package me.Alexandru302.equipmentplugin.Utils;

import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {
    private static final FileConfiguration config = EquipmentPlugin.getInstance().getConfig();
    public static String getConfigString(String path) {
        String message = Objects.requireNonNull(config.getString(path));
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String EQUIPMENT_GUI_NAME = getConfigString("gui.equipment_title");
    public static String prefix = getConfigString("messages.prefix");
}

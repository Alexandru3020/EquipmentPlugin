package me.Alexandru302.equipmentplugin;

import me.Alexandru302.equipmentplugin.Commands.EquipmentCommand;
import me.Alexandru302.equipmentplugin.Commands.EquipmentCommandTabCompleter;
import me.Alexandru302.equipmentplugin.EquipmentGUI.EquipmentGUI;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EquipmentPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        File equipmentDataFolder = new File(getDataFolder(), "equipmentData");
        if (!equipmentDataFolder.exists()) {
            equipmentDataFolder.mkdirs();
        }
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }
    private void registerCommands() {
        getCommand("equipment").setExecutor(new EquipmentCommand());
        getCommand("equipment").setTabCompleter(new EquipmentCommandTabCompleter());
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new EquipmentGUI(), this);
    }
    public static EquipmentPlugin getInstance() {
        return getPlugin(EquipmentPlugin.class);
    }
}

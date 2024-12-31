package me.Alexandru302.equipmentplugin.Commands;

import me.Alexandru302.equipmentplugin.EquipmentGUI.EquipmentGUI;
import me.Alexandru302.equipmentplugin.EquipmentPlugin;
import me.Alexandru302.equipmentplugin.Item.DiamondCharm;
import me.Alexandru302.equipmentplugin.Utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static me.Alexandru302.equipmentplugin.Utils.MessageUtil.sendMessage;

public class EquipmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("equipment.equipment_command"))) {
            MessageUtil.noPermissionMessage(sender);
            return false;
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("equipment.equipment_command.reload")) {
                sendMessage(sender, EquipmentPlugin.getInstance().getConfig().getString("messages.reload.reloading_message"));
                EquipmentPlugin.getInstance().reloadConfig();
                sendMessage(sender, EquipmentPlugin.getInstance().getConfig().getString("messages.reload.reloaded_message"));
                return false;
            }
            if (args[0].equalsIgnoreCase("reset_config") && sender.hasPermission("equipment.equipment_command.reset_config")); {
                File configFile = new File(EquipmentPlugin.getInstance().getDataFolder(), "config.yml");
                if (!configFile.exists()) {
                    sendMessage(sender, "&cConfig.yml File does not exist!");
                    return false;
                }
                configFile.delete();
                EquipmentPlugin.getInstance().saveDefaultConfig();
                sendMessage(sender, "&aConfig file successfully reset.");
                return false;
            }
        }
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "&cThis command can only be executed as a &4Player&c!");
            return false;
        }
        player.getInventory().addItem(new DiamondCharm().getItemStack());

        EquipmentGUI.openEquipmentGUI(player);
        return false;
    }
}
